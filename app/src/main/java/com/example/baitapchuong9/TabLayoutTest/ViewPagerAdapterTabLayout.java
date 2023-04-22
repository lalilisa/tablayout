package com.example.baitapchuong9.TabLayoutTest;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.baitapchuong9.fragment.FragmentHistory;
import com.example.baitapchuong9.fragment.FragmentHome;
import com.example.baitapchuong9.fragment.FragmentSearch;

/**
 * Created by Priyabrat on 21-08-2015.
 */
public class ViewPagerAdapterTabLayout extends FragmentPagerAdapter {

    public ViewPagerAdapterTabLayout(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new FragmentHome();
            case 1:return new FragmentHistory();
            case 2:return new FragmentSearch();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "FragmentHome";
        }
        else if (position == 1)
        {
            title = "FragmentHistory";
        }
        else if (position == 2)
        {
            title = "FragmentSearch";
        }
        return title;
    }
}
