package com.example.fusmobilni.fragments.items.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;


public class DeleteServiceModal extends Fragment {


    private static final String SERVICE_TITLE = "title";


    private String title;

    public DeleteServiceModal() {

    }

    public static DeleteServiceModal newInstance(String title) {
        DeleteServiceModal fragment = new DeleteServiceModal();
        Bundle args = new Bundle();
        args.putString(SERVICE_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(SERVICE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_service_modal, container, false);
    }
}