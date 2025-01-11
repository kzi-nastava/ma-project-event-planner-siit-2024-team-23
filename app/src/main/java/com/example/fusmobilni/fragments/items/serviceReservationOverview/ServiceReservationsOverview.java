package com.example.fusmobilni.fragments.items.serviceReservationOverview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.adapters.items.service.ServiceReservationOverviewAdapter;
import com.example.fusmobilni.adapters.loading.LoadingCardHorizontalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentServiceReservationsBinding;
import com.example.fusmobilni.interfaces.ReservationUpdateListener;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.services.ServiceOfferingReservationForEventResponse;
import com.example.fusmobilni.responses.items.services.ServiceOfferingReservationsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceReservationsOverview extends Fragment implements ReservationUpdateListener {

    private FragmentServiceReservationsBinding binding;
    private ServiceReservationOverviewAdapter adapter;

    public ServiceReservationsOverview() {
    }

    public static ServiceReservationsOverview newInstance() {
        return new ServiceReservationsOverview();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceReservationsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initializeLoadingCards();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ServiceReservationOverviewAdapter(new ArrayList<>(), requireContext(), this);
        this.binding.recyclerView.setAdapter(adapter);
        fetchServices();
        return view;
    }

    private void fetchServices() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        if(user == null) return;
        Call<ServiceOfferingReservationsResponse> request = ClientUtils.serviceOfferingService.findReservationsByProviderId(user.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ServiceOfferingReservationsResponse> call, @NonNull Response<ServiceOfferingReservationsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ServiceOfferingReservationForEventResponse> reservations = response.body().getReservations();
                    adapter.updateReservations(reservations);
                    turnOffShimmer(binding.eventsLoadingCards, binding.recyclerView);
                }else{
                    Toast.makeText(requireContext(), "Something else went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceOfferingReservationsResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Something went wrong!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void turnOffShimmer(RecyclerView loadingCardsView, RecyclerView actualCards) {
        loadingCardsView.setAdapter(new LoadingCardHorizontalAdapter(0));
        loadingCardsView.setVisibility(View.GONE);
        actualCards.setVisibility(View.VISIBLE);
    }

    private void initializeLoadingCards() {
        binding.eventsLoadingCards.setAdapter(new LoadingCardHorizontalAdapter(5));
    }

    @Override
    public void updateReservationsList() {
        initializeLoadingCards();
        fetchServices();
    }
}