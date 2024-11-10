package com.example.fusmobilni.fragments.RegisterFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentStepOneBinding;
import com.example.fusmobilni.databinding.FragmentStepTwoBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.RegisterViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepTwoFragment extends Fragment implements FragmentValidation {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RegisterViewModel _registerViewModel;

    ActivityResultLauncher<String[]> requestMultiplePermissions = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                // Log permissions:
                for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                    Log.d("DEBUG", entry.getKey() + " = " + entry.getValue());
                }

                // Check for specific permissions:
                Boolean a = permissions.get(Manifest.permission.CAMERA);
                Boolean b = permissions.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                if (Boolean.TRUE.equals(a) && Boolean.TRUE.equals(b)) {
                    Log.d("LOG_TAG", "Has permissions: READ_CONTACTS, ACCESS_FINE_LOCATION.");
                    pickImage();
                }
            }
    );

    ActivityResultLauncher<Intent> startForProfileImageResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Get the URI from the data intent
                        Uri uri = result.getData() != null ? result.getData().getData() : null;
                        if (uri != null) {
                            Log.d("ShopApp", "Image URI: " + uri.toString());
                            // You can now use the URI to set the image in your ImageView
                            _captureImage.setImageURI(uri);
                            _captureImage.setImageTintList(null);

                            try {
                                Bitmap image = getBitmapFromUri(requireContext(), uri);
                                _captureImage.setImageBitmap(image);
                                _registerViewModel.setProfileImage(convertBitmapToByteArray(image));
                                Log.d("SHOP", _registerViewModel.getProfileImage().getValue().toString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        // Handle other cases like ImagePicker error or cancellation
                        Log.d("ShopApp", "Image selection failed or was canceled");
                    }
                }

    }
    );




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView _captureTxt;
    private String _path;

    private Uri _uri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ImageView _captureImage;
    private CardView _cardView;

    private FragmentStepTwoBinding _binding;

    public StepTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepTwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepTwoFragment newInstance(String param1, String param2) {
        StepTwoFragment fragment = new StepTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentStepTwoBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _captureImage = _binding.profileImg;
        _cardView = _binding.cardView;

        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        _cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMultiplePermissions.launch(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                });
            }
        });
        return view;

    }


    @Override
    public boolean validate() {
        String city = _binding.cityInput.getEditText().getText().toString().trim();
        String address = _binding.addressInput.getEditText().getText().toString().trim();
        String phone = _binding.phoneInput.getEditText().getText().toString().trim();

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
        _registerViewModel.setCity(city);
        _registerViewModel.setAddress(address);
        _registerViewModel.setPhone(phone);

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