package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFlipAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ViewPagerFlipAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();

    }

    public void addRightFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    public void addLeftFragment(Fragment fragment) {
        fragments.add(0, fragment);
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
