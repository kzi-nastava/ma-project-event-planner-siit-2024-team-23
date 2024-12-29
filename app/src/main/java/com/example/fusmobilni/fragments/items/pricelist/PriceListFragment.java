package com.example.fusmobilni.fragments.items.pricelist;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.pricelist.PriceListAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentHomeBinding;
import com.example.fusmobilni.databinding.FragmentPriceListBinding;
import com.example.fusmobilni.databinding.PriceListItemBinding;
import com.example.fusmobilni.interfaces.PriceListListener;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.pricelist.PriceListGetResponse;
import com.example.fusmobilni.responses.items.pricelist.PriceListsGetResponse;
import com.example.fusmobilni.viewModels.items.category.CategoryViewModel;
import com.example.fusmobilni.viewModels.items.pricelist.PriceListViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PriceListFragment extends Fragment implements PriceListListener {

    private FragmentPriceListBinding binding;
    private PriceListAdapter adapter;

    private PriceListViewModel viewModel;
    private RecyclerView recyclerView;
    private List<PriceListGetResponse> items = new ArrayList<>();



    public PriceListFragment() {
    }

    public static PriceListFragment newInstance() {
        return new PriceListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPriceListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PriceListViewModel.class);
        View view = binding.getRoot();

        initializeRecycler();
        Long spId = getSpId();
        if (spId == null) {
            return view;
        }
        Call<PriceListsGetResponse> callback = ClientUtils.priceListService.findAllItemsBySpId(spId);
        callback.enqueue(new Callback<PriceListsGetResponse>() {
            @Override
            public void onResponse(Call<PriceListsGetResponse> call, Response<PriceListsGetResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    items = response.body().items;
                    adapter.setData(items);
                    initializeRecycler();
                }
            }

            @Override
            public void onFailure(Call<PriceListsGetResponse> call, Throwable t) {

            }
        });

        binding.generatePdfBtn.setOnClickListener(v -> onGeneratePdfClick(spId));
        return view;
    }

    @Override
    public void onUpdate(int position) {
        viewModel.populate(this.items.get(position));
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_priceList_toPriceListEdit);
    }

    private void initializeRecycler() {
        recyclerView = binding.rvPriceList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PriceListAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    private Long getSpId() {
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    private void onGeneratePdfClick(Long spId) {
        Call<ResponseBody> request = ClientUtils.priceListService.downloadPriceListPdf(spId);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        try {
                            String fileName = "price_list_" + spId + "_" + System.currentTimeMillis() + ".pdf";
                            File file = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

                            try (InputStream inputStream = response.body().byteStream();
                                 FileOutputStream outputStream = new FileOutputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int read;
                                while ((read = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, read);
                                }
                                outputStream.flush();
                            }

                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(requireContext(), "PDF downloaded: " + fileName, Toast.LENGTH_LONG).show();
                                openPdf(requireContext(), file);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(requireContext(), "Download failed", Toast.LENGTH_SHORT).show()
                            );
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Download failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void openPdf(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF viewer installed", Toast.LENGTH_SHORT).show();
        }
    }
}