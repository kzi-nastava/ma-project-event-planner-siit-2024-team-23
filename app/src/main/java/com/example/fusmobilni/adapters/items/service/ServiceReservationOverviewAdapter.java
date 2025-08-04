package com.example.fusmobilni.adapters.items.service;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.interfaces.ReservationUpdateListener;
import com.example.fusmobilni.model.enums.ReservationStatus;
import com.example.fusmobilni.responses.items.services.ServiceOfferingReservationForEventResponse;
import com.example.fusmobilni.responses.items.services.home.ServiceHomeResponse;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceReservationOverviewAdapter extends RecyclerView.Adapter<ServiceReservationOverviewAdapter.ServiceReservationsViewHolder> {
    private List<ServiceOfferingReservationForEventResponse> reservations;

    private Context context;

    private ReservationUpdateListener listener;

    public ServiceReservationOverviewAdapter(List<ServiceOfferingReservationForEventResponse> reservations, Context context,
                                             ReservationUpdateListener listener) {
        this.reservations = reservations;
        this.context = context;
        this.listener = listener;
    }
    public void updateReservations(List<ServiceOfferingReservationForEventResponse> reservations){
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceReservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_reservation_overview_card, parent, false);
        return new ServiceReservationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceReservationsViewHolder holder, int position) {
        ServiceOfferingReservationForEventResponse reservation = reservations.get(position);

        holder.serviceName.setText(reservation.getServiceName());
        holder.eventTitle.setText(reservation.getEventTitle());
        holder.organizerName.setText(reservation.getOrganizerName());
        holder.serviceDescription.setText(reservation.getServiceDescription());
        holder.categoryChip.setText(reservation.getCategory().name);
        holder.price.setText(String.valueOf(reservation.getPrice()));
        holder.statusChip.setText(reservation.getStatus().name());
        if(reservation.getStatus().equals(ReservationStatus.ACCEPTED)){
            holder.acceptBtn.setVisibility(View.GONE);
            holder.declineBtn.setVisibility(View.GONE);
        }

        try {
            holder.serviceImage.setImageURI(convertToUrisFromBase64(holder.serviceImage.getContext(), reservation.getServiceImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.acceptBtn.setOnClickListener(v -> {
            handleAcceptReservation(reservation, position);
        });

        holder.declineBtn.setOnClickListener(v -> {
            handleDeclineReservation(reservation, position);
        });

    }

    private void handleDeclineReservation(ServiceOfferingReservationForEventResponse reservation, int position) {
        Call<Void> request = ClientUtils.serviceOfferingService.declineReservation(reservation.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Reservation declined for: " + reservation.getServiceName(), Toast.LENGTH_SHORT).show();
                    listener.updateReservationsList();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(context, "Something went wrong! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleAcceptReservation(ServiceOfferingReservationForEventResponse reservation, int position) {
        Call<Void> request = ClientUtils.serviceOfferingService.acceptReservation(reservation.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Reservation accepted for: " + reservation.getServiceName(), Toast.LENGTH_SHORT).show();
                    listener.updateReservationsList();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(context, "Something went wrong! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ServiceReservationsViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceName;
        public TextView eventTitle;
        public TextView organizerName;
        public TextView serviceDescription;
        public TextView categoryChip;
        public TextView price;
        public ImageView serviceImage;
        public TextView statusChip;
        public Button acceptBtn;
        public Button declineBtn;

        public ServiceReservationsViewHolder(@NonNull View view) {
            super(view);
            this.serviceName = view.findViewById(R.id.serviceName);
            this.eventTitle = view.findViewById(R.id.eventTitle);
            this.organizerName = view.findViewById(R.id.organizerName);
            this.serviceDescription = view.findViewById(R.id.serviceDescription);
            this.categoryChip = view.findViewById(R.id.categoryChip);
            this.serviceImage = view.findViewById(R.id.serviceImage);
            this.price = view.findViewById(R.id.price);
            this.statusChip = view.findViewById(R.id.statusChip);
            this.acceptBtn = view.findViewById(R.id.acceptBtn);
            this.declineBtn = view.findViewById(R.id.declineBtn);

        }
    }
}
