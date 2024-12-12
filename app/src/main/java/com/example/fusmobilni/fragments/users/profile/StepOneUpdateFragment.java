package com.example.fusmobilni.fragments.users.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentStepOneUpdateBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.users.UpdateProfileViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;


public class StepOneUpdateFragment extends Fragment implements FragmentValidation {
    private FragmentStepOneUpdateBinding _binding;
    private UpdateProfileViewModel _updateProfileViewModel;
    private Bitmap _image;
    ActivityResultLauncher<Intent> startForProfileImageResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Get the URI from the data intent
                        Uri uri = result.getData() != null ? result.getData().getData() : null;
                        if (uri != null) {
                            _captureImage.setBackground(null);
                            _captureImage.setImageURI(uri);
                            _captureImage.setImageTintList(null);

                            try {
                                _image = getBitmapFromUri(requireContext(), uri);
                                _captureImage.setImageBitmap(_image);
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

    private ImageView _captureImage;

    public StepOneUpdateFragment() {
        // Required empty public constructor
    }
    public static StepOneUpdateFragment newInstance() {
        return new StepOneUpdateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentStepOneUpdateBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _captureImage = _binding.profileImg;
        CardView cardView = _binding.cardView;

        _updateProfileViewModel = new ViewModelProvider(requireActivity()).get(UpdateProfileViewModel.class);
        cardView.setOnClickListener(v -> pickImage());
        populateInputs();
        return view;
    }

    private void populateInputs() {
        _updateProfileViewModel.getName().observe(getViewLifecycleOwner(),
                name -> Objects.requireNonNull(_binding.firstNameInput.getEditText()).setText(name));
        _updateProfileViewModel.getLastName().observe(getViewLifecycleOwner(),
                lastName -> Objects.requireNonNull(_binding.lastNameInput.getEditText()).setText(lastName));
        _updateProfileViewModel.getEmail().observe(getViewLifecycleOwner(),
                email -> Objects.requireNonNull(_binding.emailInput.getEditText()).setText(email));
        _updateProfileViewModel.getCity().observe(getViewLifecycleOwner(),
                city -> Objects.requireNonNull(_binding.cityInput.getEditText()).setText(city));
        _updateProfileViewModel.getAddress().observe(getViewLifecycleOwner(),
                streetAddress -> Objects.requireNonNull(_binding.addressInput.getEditText()).setText(streetAddress));
        _updateProfileViewModel.getPhone().observe(getViewLifecycleOwner(),
                phone -> Objects.requireNonNull(_binding.phoneInput.getEditText()).setText(phone));
        _updateProfileViewModel.getProfileImage().observe(getViewLifecycleOwner(), image -> {
            if (image != null && image.length > 0) {
                // Set the profile image if available
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                _captureImage.setImageBitmap(bitmap);
                _captureImage.setBackground(null);
            } else {
                // No image available, show the placeholder
                _captureImage.setBackgroundResource(R.drawable.event_image_placeholder);
                _captureImage.setImageBitmap(null);
                _captureImage.setBackground(null);
            }
        });

    }

    @Override
    public boolean validate() {
        // Retrieve values from input fields
        String name = Objects.requireNonNull(_binding.firstNameInput.getEditText()).getText().toString().trim();
        String lastName = Objects.requireNonNull(_binding.lastNameInput.getEditText()).getText().toString().trim();
        String email = Objects.requireNonNull(_binding.emailInput.getEditText()).getText().toString().trim();
        String city = Objects.requireNonNull(_binding.cityInput.getEditText()).getText().toString().trim();
        String address = Objects.requireNonNull(_binding.addressInput.getEditText()).getText().toString().trim();
        String phone = Objects.requireNonNull(_binding.phoneInput.getEditText()).getText().toString().trim();


        // Validate each field
        if (name.isEmpty()) {
            _binding.firstNameInput.setErrorEnabled(true);
            _binding.firstNameInput.setError("Name is required");
            return false;
        } else {
            _binding.firstNameInput.setError(null);
            _binding.firstNameInput.setErrorEnabled(false);
        }
        if (lastName.isEmpty()) {
            _binding.lastNameInput.setErrorEnabled(true);
            _binding.lastNameInput.setError("Last name is required");
            return false;
        } else {
            _binding.lastNameInput.setError(null);
            _binding.lastNameInput.setErrorEnabled(false);
        }
        // TODO check if the email is in the db already - emails already in use!
        if (email.isEmpty()) {
            _binding.emailInput.setErrorEnabled(true);
            _binding.emailInput.setError("Email is required");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _binding.emailInput.setErrorEnabled(true);
            _binding.emailInput.setError("Invalid email format");
            return false;
        } else {
            _binding.emailInput.setError(null);
            _binding.emailInput.setErrorEnabled(false);
        }
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
        _updateProfileViewModel.setCity(city);
        _updateProfileViewModel.setAddress(address);
        _updateProfileViewModel.setPhone(phone);
        _updateProfileViewModel.setName(name);
        _updateProfileViewModel.setLastName(lastName);
        _updateProfileViewModel.setEmail(email);
        if(_image != null) {
            _updateProfileViewModel.setProfileImage(convertBitmapToByteArray(_image));
        }
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