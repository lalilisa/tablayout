package com.example.baitapchuong9.TabLayoutTest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baitapchuong9.fragment.FragmentHistory;
import com.example.baitapchuong9.fragment.FragmentHome;
import com.example.baitapchuong9.fragment.FragmentInfo;
import com.example.baitapchuong9.fragment.FragmentSearch;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new FragmentHome();
            case 1:return new FragmentInfo();
            case 2:return new FragmentSearch();

        }
        return null;
    }
}