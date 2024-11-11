package com.example.fusmobilni.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.RecyclerAdapter;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormThreeBinding;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormTwoBinding;
import com.example.fusmobilni.interfaces.ItemClickListener;
import com.example.fusmobilni.viewModel.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MultiStepServiceFormThree extends Fragment implements ItemClickListener {


    private FragmentMultiStepServiceFormThreeBinding binding;
    private ServiceViewModel viewModel;
    private Button imageButton;
    private RecyclerView recyclerView;

    private RecyclerAdapter imageAdapter;

    private ArrayList<Uri> imageUris = new ArrayList<>();

    private int PICK_IMAGE_MULTIPLE = 1;

    public MultiStepServiceFormThree() {
        // Required empty public constructor
    }


    public static MultiStepServiceFormThree newInstance(String param1, String param2) {
        return new MultiStepServiceFormThree();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMultiStepServiceFormThreeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
        View view = binding.getRoot();

        imageButton = binding.imageButton;
        imageButton.setOnClickListener(
                v -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
                }
        );

        populateInputs();
        recyclerView = binding.recycler;
        imageAdapter = new RecyclerAdapter(imageUris, this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        recyclerView.setAdapter(imageAdapter);

        binding.backwardsButton.setOnClickListener(v -> {
            setValues();
            Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepThree_toServiceCreationStepTwo);
        });
        binding.forwardButton.setOnClickListener(v -> {
            //create new service
            Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepThree_toServiceView);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imageUris.add(uri);
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                imageUris.add(uri);
            }
            imageAdapter.notifyDataSetChanged();
        }
    }

    private void setValues() {
        viewModel.clearImageUris();
        for(Uri image: imageUris) {
            viewModel.addImageUri(image);
        }
    }


    private void populateInputs() {
        imageUris.clear();
        imageUris.addAll(Objects.requireNonNull(viewModel.getImageUris().getValue()));
    }

    @Override
    public void onItemRemove(int position) {
        imageAdapter.removeItem(position);
    }
}