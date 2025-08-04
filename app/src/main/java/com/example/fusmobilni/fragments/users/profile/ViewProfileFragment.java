package com.example.fusmobilni.fragments.users.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.HomeActivity;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentViewProfileBinding;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.auth.UserAvatarResponse;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewProfileFragment extends Fragment {
    private FragmentViewProfileBinding _binding;

    public ViewProfileFragment() {
        // Required empty public constructor
    }
    public static ViewProfileFragment newInstance() {
        return new ViewProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentViewProfileBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();

        TabLayout tabLayout = _binding.profileTabs;
        NestedScrollView contentScrollView = _binding.contentScrollView;
        loadFragment(new UserFavEventsFragment());

        Call<ResponseBody> request = ClientUtils.authService.findProfileImage();
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful() && response.body() != null){
                    try{
                        byte[] imageBytes = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        if(bitmap != null){
                            _binding.profilePicture.setImageBitmap(bitmap);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
            }
        });
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        _binding.userName.setText(String.format("%s %s", user.getFirstName(), user.getSurname()));
        _binding.userEmail.setText(user.getEmail());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Get the selected tab's position
                int position = tab.getPosition();
                contentScrollView.removeAllViews();
                Fragment fragment = new UserFavEventsFragment();
                switch (position) {
                    case 0:
                        fragment = new UserFavEventsFragment();
                        break;
                    case 1:
                        fragment = new UserFavItemsFragment();
                        break;
                    case 2:
                        fragment = new AboutUserFragment();
                        break;
                    default:
                        break;
                }
                loadFragment(fragment);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        _binding.logout.setOnClickListener(v -> logout());
        _binding.updateProfile.setOnClickListener(v-> onUpdateClick());
        _binding.calendarButton.setOnClickListener(v->onCalendarClick());
        _binding.deactivateBtn.setOnClickListener(v-> showDeactivateConfirmationDialog());
        _binding.deactivateBtn.setVisibility(isDeactivateBtnVisible() ? View.VISIBLE : View.GONE);
        return  view;
    }

    private boolean isDeactivateBtnVisible() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        return user.getRole().equals(UserType.EVENT_ORGANIZER) || user.getRole().equals(UserType.SERVICE_PROVIDER);
    }

    private void showDeactivateConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Deactivate Account");
        builder.setMessage("Are you sure you want to deactivate your account?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            onDeactivateClick(dialog);
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onDeactivateClick(DialogInterface dialog) {
        Call<Void> request = ClientUtils.userService.deactivateUserProfile();
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Your account is now deactivated", Toast.LENGTH_SHORT).show();
                    logout();
                }else{
                    dialog.dismiss();
                    if(response.raw().code() == 403){
                        Toast.makeText(requireContext(), "You have reserved services or events in future. You cannot deactivate account!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireContext(), "Error occurred try again later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Error occurred try again later!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        CustomSharedPrefs sharedPrefs = CustomSharedPrefs.getInstance();
        sharedPrefs.clearAll();
        Intent intent = new Intent(requireContext(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
    private void loadFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentScrollView, fragment)
                .commit();
    }


    private void onUpdateClick() {
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_viewProfileFragment_to_updateProfileFragment);
    }
    private void onCalendarClick(){
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_viewProfileFragment_to_userCalendarFragment);
    }
}