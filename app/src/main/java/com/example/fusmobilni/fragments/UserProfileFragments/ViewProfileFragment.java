package com.example.fusmobilni.fragments.UserProfileFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.HomeActivity;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentViewProfileBinding;
import com.google.android.material.tabs.TabLayout;


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
        return  view;
    }
    private void logout() {
        CustomSharedPrefs sharedPrefs = new CustomSharedPrefs(requireContext());
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