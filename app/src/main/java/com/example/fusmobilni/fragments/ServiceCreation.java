package com.example.fusmobilni.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.RecyclerAdapter;
import com.example.fusmobilni.databinding.FragmentHomeBinding;
import com.example.fusmobilni.databinding.FragmentServiceCreationBinding;
import com.example.fusmobilni.interfaces.ItemClickListener;

import java.util.ArrayList;


public class ServiceCreation extends Fragment implements ItemClickListener {

    private FragmentServiceCreationBinding binding;
    private RecyclerAdapter imageAdapter;
    private RecyclerView recyclerView;
    private Button imageButton;
    int PICK_IMAGE_MULTIPLE = 1;
    ArrayList<Uri> imageUris = new ArrayList<>();


    public ServiceCreation() {

    }


    public static ServiceCreation newInstance() {
        return new ServiceCreation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceCreationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        binding.autoCompleteTextview.setAdapter(adapter);

        String[] eventTypes = getResources().getStringArray(R.array.EventTypes);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, eventTypes);
        binding.eventTypesInput.setAdapter(adapter);


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

        recyclerView = binding.recycler;
        imageAdapter = new RecyclerAdapter(imageUris, this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        recyclerView.setAdapter(imageAdapter);

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

    @Override
    public void onItemRemove(int position) {
        imageAdapter.removeItem(position);
    }
}