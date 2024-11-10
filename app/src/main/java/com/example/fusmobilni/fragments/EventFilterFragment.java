package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CategoryFilterAdapter;
import com.example.fusmobilni.databinding.FragmentEventFilterBinding;
import com.example.fusmobilni.model.Category;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFilterFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView categoryRecyclerView;
    private FragmentEventFilterBinding _binding;
    private List<Category> _categories;
    private CategoryFilterAdapter _adapter;

    public EventFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFilterFragment newInstance(String param1, String param2) {
        EventFilterFragment fragment = new EventFilterFragment();
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
        _binding = FragmentEventFilterBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();
        categoryRecyclerView = _binding.categoryRecyclerView;
        this._categories = Arrays.asList(
                new Category("Sports", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Music", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category("Art", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category("Food", R.drawable.ic_category_food_active, R.drawable.ic_category_food_inactive)
        );
        _adapter = new CategoryFilterAdapter(_categories);
        categoryRecyclerView.setAdapter(_adapter);

        return view;
    }
}