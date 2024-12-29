package com.example.fusmobilni.adapters.admin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fusmobilni.R;

import java.util.ArrayList;

public class AdminApprovalAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();
    Context context;
    int[] imageList = {R.drawable.outline_check_circle_24, R.drawable.outline_check_circle_24};

    public void setContext(Context context) {
        this.context = context;
    }

    public void addFragment(Fragment fragment, String s) {
        fragmentArrayList.add(fragment);
        stringArrayList.add(s);
    }

    public AdminApprovalAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = ContextCompat.getDrawable(context, imageList[position]);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        SpannableString spannableString = new SpannableString("  " + stringArrayList.get(position));

        // Initialize image span
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);

        // Set span
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // return spannable string
        return spannableString;
    }
}
