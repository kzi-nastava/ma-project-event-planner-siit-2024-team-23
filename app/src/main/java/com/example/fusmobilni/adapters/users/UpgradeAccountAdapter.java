package com.example.fusmobilni.adapters.users;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class UpgradeAccountAdapter extends FragmentStateAdapter {

    private final List<Fragment> _fragmentList;

    public UpgradeAccountAdapter(@NonNull Fragment fragmentActivity, List<Fragment> fragments) {
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
