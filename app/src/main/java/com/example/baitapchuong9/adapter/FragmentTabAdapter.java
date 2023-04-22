package com.example.baitapchuong9.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baitapchuong9.fragment.FragmentHistory;
import com.example.baitapchuong9.fragment.FragmentHome;
import com.example.baitapchuong9.fragment.FragmentSearch;

public class FragmentTabAdapter extends FragmentStatePagerAdapter {
    private int numPage=3;
    public FragmentTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHome();
            case 1: return new FragmentHistory();
            case 2: return new FragmentSearch();
            default: return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Home";
            case 1:return "History";
            case 2:return "Search";
            default:return "Home";
        }
    }
}
