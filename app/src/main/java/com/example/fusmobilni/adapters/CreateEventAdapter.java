package com.example.fusmobilni.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class CreateEventAdapter extends FragmentStateAdapter {
    private final List<Fragment> _fragmentList;

    private RegistrationAdapter _adapter;
    private List<Fragment> _fragments;


    public CreateEventAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this._fragmentList = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return _fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return _fragmentList.size();
    }

    public void setFragments(List<Fragment> fragments) {
        _fragmentList.clear();
        _fragmentList.addAll(fragments);
        notifyItemRangeChanged(0, _fragmentList.size());
    }
}
