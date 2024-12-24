package com.example.fusmobilni.fragments.items.service;




import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.serviceProvider.ServiceProviderServiceAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentServiceViewBinding;
import com.example.fusmobilni.fragments.users.filters.ServiceProviderFilterFragment;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.GetServicesResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;
import com.example.fusmobilni.requests.services.cardView.GetServiceCardResponse;
import com.example.fusmobilni.requests.services.cardView.GetServicesCardResponse;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.viewModels.users.serviceProvider.ServiceProviderViewModel;
import com.example.fusmobilni.viewModels.items.service.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceView extends Fragment implements DeleteServiceListener {

    FragmentServiceViewBinding binding;
    private static final long DEBOUNCE_DELAY = 300;
    private Handler handler = new Handler();
    private ServiceProviderServiceAdapter serviceAdapter;
    private View deleteModal;
    private List<GetServiceCardResponse> services = new ArrayList<>();
    private ServiceProviderViewModel viewModel;

    public ServiceView() {
    }

    public static ServiceView newInstance() {
        return new ServiceView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        requireActivity().getViewModelStore().clear();
        viewModel = new ViewModelProvider(requireActivity()).get(ServiceProviderViewModel.class);
        setUpAdapter();
        viewModel.setData(services);
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        Long userId = null;
        if (user == null) {
            return view;
        }
        userId = user.getId();
        Call<GetServicesCardResponse> response = ClientUtils.serviceOfferingService.findAllByServiceProvider(userId,new ServiceFilterRequest());
        response.enqueue(new Callback<GetServicesCardResponse>() {
            @Override
            public void onResponse(Call<GetServicesCardResponse> call, Response<GetServicesCardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    services = response.body().services;
                    serviceAdapter.notifyDataSetChanged();
                    viewModel.setData(services);
                }
            }

            @Override
            public void onFailure(Call<GetServicesCardResponse> call, Throwable t) {
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceView_toServiceCreationStepOne);
        });
        setUpSearch();
        binding.filterBtn.setOnClickListener(v -> openFilterFragment());

        viewModel.getSearchConstraint().observe(getViewLifecycleOwner(),observer->{
            serviceAdapter.setData(viewModel.getServices().getValue());
        });

        viewModel.getServices().observe(getViewLifecycleOwner(),observer->{
            serviceAdapter.setData(viewModel.getServices().getValue());
        });

        return view;
    }

    private void openFilterFragment() {
        ServiceProviderFilterFragment filterFragment = new ServiceProviderFilterFragment();

        filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
    }

    @Override
    public void onDeleteService(int position) {
        deleteModal = binding.getRoot().findViewById(R.id.nigger);
        binding.modalBackground.setVisibility(View.VISIBLE);
        deleteModal.setVisibility(View.VISIBLE);
        Button cancelButton = deleteModal.findViewById(R.id.cancelButton);
        Button confirmButton = deleteModal.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> {
            binding.modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });

        confirmButton.setOnClickListener(v -> {
            Long id = this.services.get(position).id;
            Call<Void> response = ClientUtils.serviceOfferingService.delete(id);
            response.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    services.remove(position);
                    viewModel.setData(services);
                    serviceAdapter.notifyItemRemoved(position);
                    serviceAdapter.notifyItemRangeChanged(position, services.size());
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
    public void onUpdateService(int position) {
        GetServiceCardResponse service = services.get(position);
        Call<GetServiceResponse> findById = ClientUtils.serviceOfferingService.findById(service.id);
        findById.enqueue(new Callback<GetServiceResponse>() {
            @Override
            public void onResponse(Call<GetServiceResponse> call, Response<GetServiceResponse> response) {
                if (response.isSuccessful()) {
                    ServiceViewModel viewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
                    viewModel.populate(response.body(), getContext());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_serviceView_toServiceCreationStepOne);
                }
            }
            @Override
            public void onFailure(Call<GetServiceResponse> call, Throwable t) {

            }
        });
    }

    private void setUpAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceAdapter = new ServiceProviderServiceAdapter(services, this);
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
                    viewModel.setSearchConstraint(s.toString());
                }, DEBOUNCE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}