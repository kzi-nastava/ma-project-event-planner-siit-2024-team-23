package com.example.fusmobilni.fragments.items.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.serviceProvider.ServiceProviderProductAdapter;
import com.example.fusmobilni.adapters.users.serviceProvider.ServiceProviderServiceAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentServiceViewBinding;
import com.example.fusmobilni.fragments.items.product.filters.ProductFilterFragment;
import com.example.fusmobilni.fragments.users.filters.ServiceProviderFilterFragment;
import com.example.fusmobilni.fragments.users.filters.ServiceProviderProductFilterFragment;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.requests.products.GetProductResponse;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;
import com.example.fusmobilni.requests.services.cardView.GetServiceCardResponse;
import com.example.fusmobilni.requests.services.cardView.GetServicesCardResponse;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.products.ProductOverviewResponse;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;
import com.example.fusmobilni.responses.items.products.home.ProductsHomeResponse;
import com.example.fusmobilni.viewModels.items.product.ProductViewModel;
import com.example.fusmobilni.viewModels.items.service.ServiceViewModel;
import com.example.fusmobilni.viewModels.users.serviceProvider.ServiceProviderViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsFragment extends Fragment implements DeleteServiceListener {
    private FragmentServiceViewBinding binding;
    private static final long DEBOUNCE_DELAY = 300;
    private Handler handler = new Handler();
    private ServiceProviderProductAdapter serviceAdapter;
    private View deleteModal;
    private List<ProductHomeResponse> products = new ArrayList<>();
    private ProductViewModel viewModel;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.applyFilters(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        requireActivity().getViewModelStore().clear();
        viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        setUpAdapter();
        viewModel.setData(products);
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        Long userId = null;
        if (user == null) {
            return view;
        }
        userId = user.getId();
        Call<ProductsHomeResponse> response = ClientUtils.productsService.findAllByServiceProvider(userId,new ServiceFilterRequest());
        response.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ProductsHomeResponse> call, @NonNull Response<ProductsHomeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products = response.body().products;
                    serviceAdapter.notifyDataSetChanged();
                    viewModel.setData(products);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductsHomeResponse> call, @NonNull Throwable t) {
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_productView_toProductCreationStepOne);
        });
        setUpSearch();
        binding.filterBtn.setOnClickListener(v -> openFilterFragment());

        viewModel.getSearchConstraint().observe(getViewLifecycleOwner(),observer->{
            serviceAdapter.setData(viewModel.getProducts().getValue());
        });

        viewModel.getProducts().observe(getViewLifecycleOwner(),observer->{
            serviceAdapter.setData(viewModel.getProducts().getValue());
        });
        return view;
    }

    private void openFilterFragment() {
        Bundle bundle = new Bundle();
        bundle.putDouble("minValue", 0);
        bundle.putDouble("maxValue", 10000);
        ServiceProviderProductFilterFragment filterFragment = new ServiceProviderProductFilterFragment();
        filterFragment.setArguments(bundle);

        filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
    }

    @Override
    public void onDeleteService(Long id) {
        deleteModal = binding.getRoot().findViewById(R.id.nigger);
        TextView deleteLabel = deleteModal.findViewById(R.id.deleteLabel);
        deleteLabel.setText(R.string.product_delete_label);
        binding.modalBackground.setVisibility(View.VISIBLE);
        deleteModal.setVisibility(View.VISIBLE);
        Button cancelButton = deleteModal.findViewById(R.id.cancelButton);
        Button confirmButton = deleteModal.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> {
            binding.modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });

        confirmButton.setOnClickListener(v -> {
            int position = findProductByPosition(id);
            if (position == -1) return;
            Call<Void> response = ClientUtils.productsService.delete(id);
            response.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    products.remove(position);
                    viewModel.setData(products);
                    serviceAdapter.notifyItemRemoved(position);
                    serviceAdapter.notifyItemRangeChanged(position, products.size());
                    binding.modalBackground.setVisibility(View.INVISIBLE);
                    deleteModal.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public void onUpdateService(Long id) {
        int position = findProductByPosition(id);
        if (position == -1) return;
        Call<GetProductResponse> findById = ClientUtils.productsService.findById(id);
        findById.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<GetProductResponse> call, @NonNull Response<GetProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductViewModel viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
                    viewModel.populate(response.body(), getContext());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_productView_toProductCreationStepOne);
                }
            }
            @Override
            public void onFailure(Call<GetProductResponse> call, Throwable t) {

            }
        });
    }

    private void setUpAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceAdapter = new ServiceProviderProductAdapter(products, this);
        binding.recyclerView.setAdapter(serviceAdapter);
    }

    private void setUpSearch() {
        binding.searchServices.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    viewModel.setSearchConstraint(s.toString(), requireContext());
                }, DEBOUNCE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int findProductByPosition(Long id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id.equals(id)) return i;
        }
        return -1;
    }
}