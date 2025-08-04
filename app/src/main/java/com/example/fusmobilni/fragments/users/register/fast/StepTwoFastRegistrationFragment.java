package com.example.fusmobilni.fragments.users.register.fast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fusmobilni.databinding.FragmentStepTwoFastRegistrationBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.users.register.FastRegisterViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepTwoFastRegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepTwoFastRegistrationFragment extends Fragment implements FragmentValidation {

    private FastRegisterViewModel _viewModel;

    private FragmentStepTwoFastRegistrationBinding _binding;

    public StepTwoFastRegistrationFragment() {
    }

    public static StepTwoFastRegistrationFragment newInstance() {
        StepTwoFastRegistrationFragment fragment = new StepTwoFastRegistrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private ImageView _captureImage;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentStepTwoFastRegistrationBinding.inflate(inflater,container,false);
        View view = _binding.getRoot();
        _captureImage = _binding.profileImg;
        CardView cardView = _binding.cardView;

        _viewModel = new ViewModelProvider(requireActivity()).get(FastRegisterViewModel.class);

        cardView.setOnClickListener(v -> pickImage());
        return view;

    }
    ActivityResultLauncher<Intent> startForProfileImageResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Get the URI from the data intent
                        Uri uri = result.getData() != null ? result.getData().getData() : null;
                        if (uri != null) {
                            _captureImage.setImageURI(uri);
                            _captureImage.setImageTintList(null);

                            try {
                                Bitmap image = getBitmapFromUri(requireContext(), uri);
                                _captureImage.setImageBitmap(image);
                                _viewModel.setProfileImage(convertBitmapToByteArray(image));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        Log.d("EventPlanner", "Image selection failed or was canceled");
                    }
                }

            }
    );

    @Override
    public boolean validate() {
        String city = Objects.requireNonNull(_binding.cityInput.getEditText()).getText().toString().trim();
        String address = Objects.requireNonNull(_binding.addressInput.getEditText()).getText().toString().trim();
        String phone = Objects.requireNonNull(_binding.phoneInput.getEditText()).getText().toString().trim();

        if (city.isEmpty()) {
            _binding.cityInput.setErrorEnabled(true);
            _binding.cityInput.setError("City is required");
            return false;
        } else {
            _binding.cityInput.setError(null);
            _binding.cityInput.setErrorEnabled(false);
        }

        if (address.isEmpty()) {
            _binding.addressInput.setErrorEnabled(true);
            _binding.addressInput.setError("Address is required");
            return false;
        } else {
            _binding.addressInput.setError(null);
            _binding.addressInput.setErrorEnabled(false);
        }

        if (phone.isEmpty()) {
            _binding.phoneInput.setErrorEnabled(true);
            _binding.phoneInput.setError("Phone is required");
            return false;
        } else {
            _binding.phoneInput.setError(null);
            _binding.phoneInput.setErrorEnabled(false);
        }
        _viewModel.setCity(city);
        _viewModel.setAddress(address);
        _viewModel.setPhone(phone);

        return true;
    }
    public Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        Bitmap bitmap;
        ImageDecoder.Source source = ImageDecoder.createSource(context.getContentResolver(), uri);
        bitmap = ImageDecoder.decodeBitmap(source);
        return bitmap;
    }
    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Compress the Bitmap into a PNG or JPEG format
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void pickImage() {
        ImagePicker.with(this)
                .compress(1024)
                .crop(1f, 1f)
                .maxResultSize(240,240)
                .createIntent(intent->{
                            startForProfileImageResult.launch(intent);
                            return null;
                        }
                );
    }
}