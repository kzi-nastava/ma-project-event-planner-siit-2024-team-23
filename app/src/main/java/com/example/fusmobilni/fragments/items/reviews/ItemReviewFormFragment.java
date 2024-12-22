package com.example.fusmobilni.fragments.items.reviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.reviews.ItemReviewFormAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentItemReviewFormBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.requests.items.ItemReviewCreateRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemReviewFormFragment extends Fragment {

    private String _itemName;
    private Long _itemId;
    private FragmentItemReviewFormBinding _binding;
    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;
    private Long _eoId;
    private ItemReviewFormAdapter _adapter;

    public ItemReviewFormFragment() {
        // Required empty public constructor
    }

    public static ItemReviewFormFragment newInstance(String param1, String param2) {
        ItemReviewFormFragment fragment = new ItemReviewFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _eoId = getArguments().getLong("eoId");
            _itemId = getArguments().getLong("itemId");
            _itemName = getArguments().getString("itemName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _binding = FragmentItemReviewFormBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        _binding.textViewItemName.setText(_itemName);
        _adapter = new ItemReviewFormAdapter();
        _binding.starsRecycler.setAdapter(_adapter);
        initializeDialogs();

        _binding.submitReviewButton.setOnClickListener(v -> {
            sendReview();
        });
        return root;
    }

    void sendReview() {
        _loader.show(getFragmentManager(), "loading_spinner");
        String content = _binding.editTextTextMultiLine.getText().toString();
        ItemReviewCreateRequest request = new ItemReviewCreateRequest(content, _eoId, _itemId, _adapter.getAmount());
        Call<Void> call = ClientUtils.itemsService.submitReview(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    openSuccessWindow("Your review has been submitted");
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {

                        Navigation.findNavController(getView()).navigateUp();
                    }, 1500);
                } else {
                    openFailiureWindow("Failed to submit review");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                openFailiureWindow("Failed to submit review");
            }
        });
    }

    void openSuccessWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Success");
        args.putString("Message", message);
        _success.setArguments(args);
        _success.show(getParentFragmentManager(), "success_dialog");
    }

    void openFailiureWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", message);
        _failiure.setArguments(args);
        _failiure.show(getParentFragmentManager(), "failiure_dialog");
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _failiure = new FailiureDialogFragment();
    }

}