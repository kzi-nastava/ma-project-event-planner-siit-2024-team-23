package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentAboutUserBinding;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.responses.auth.UserAvatarResponse;
import com.example.fusmobilni.responses.users.UserInfoPreviewResponse;
import com.example.fusmobilni.responses.users.UserInfoResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUserFragment extends Fragment {
    private FragmentAboutUserBinding _binding;
    private boolean isLoading = false;

    public AboutUserFragment() {
        // Required empty public constructor
    }

    public static AboutUserFragment newInstance() {
        return new AboutUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentAboutUserBinding.inflate(getLayoutInflater());
        Call<UserInfoPreviewResponse> request = ClientUtils.userService.findUserPreviewInfo();

        _binding.loading.setVisibility(View.VISIBLE);

        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserInfoPreviewResponse> call, @NonNull Response<UserInfoPreviewResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    _binding.phoneNumber.setText(response.body().getPhoneNumber());
                    _binding.address.setText(String.format("%s %s", response.body().getAddress(), response.body().getStreetNumber()));
                    _binding.city.setText(response.body().getCity());
                    _binding.role.setText(response.body().getUserRole().toString());
                    if(response.body().getUserRole().equals(UserType.SERVICE_PROVIDER)){
                        _binding.companyDescriptionLayout.setVisibility(View.VISIBLE);
                        _binding.companyNameLayout.setVisibility(View.VISIBLE);
                        _binding.companyName.setText(response.body().getCompanyName());
                        _binding.companyDescription.setText(response.body().getCompanyDescription());
                    }else{
                        _binding.companyDescriptionLayout.setVisibility(View.GONE);
                        _binding.companyNameLayout.setVisibility(View.GONE);
                    }
                    _binding.loading.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfoPreviewResponse> call, @NonNull Throwable t) {
            }

        });
        return _binding.getRoot();
    }
}