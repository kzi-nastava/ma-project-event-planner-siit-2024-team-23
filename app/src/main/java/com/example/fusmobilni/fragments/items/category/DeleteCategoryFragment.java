package com.example.fusmobilni.fragments.items.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;


public class DeleteCategoryFragment extends Fragment {

    private static final String CATEGORY_TITLE = "title";

    private String title;

    public DeleteCategoryFragment() {
        // Required empty public constructor
    }

    public static DeleteCategoryFragment newInstance(String title) {
        DeleteCategoryFragment fragment = new DeleteCategoryFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_category, container, false);
    }
}