package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai;
import vn.edu.hust.soict.khacsan.myapp.view.activity.MinaActivity;

public class FragmentMondaiMina extends Fragment implements MinaActivity.DataUpdateListener {
    public static final String STATE_EXERCISE = "STATE_EXERCISE";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MinaController controller;
    private int mExercise;
    private ViewPagerMondaiAdapter mPagerMondaiAdapter;
    private fragmentSelectListener mFragmentListener1, mFragmentListener2, mFragmentListener3;
    List<Fragment> fragments;

    public static FragmentMondaiMina newInstance(int ex) {
        Bundle args = new Bundle();
        args.putInt(STATE_EXERCISE, ex);
        FragmentMondaiMina fragment = new FragmentMondaiMina();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mondai_mina, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tab_fragment_mondai_mina);
        mViewPager = view.findViewById(R.id.view_pager_mondai_mina);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("HAHA", "onTabUnselected: "+tab.getPosition());
                switch (tab.getPosition()) {
                    case 0: {

                        if (mFragmentListener1 != null) mFragmentListener1.onUnselected();
                        break;
                    }
                    case 1: {
                        if (mFragmentListener2 != null) mFragmentListener2.onUnselected();
                        break;
                    }
                    case 2: {
                        if (mFragmentListener3 != null) mFragmentListener3.onUnselected();
                        break;
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        controller = new MinaController();

        Bundle bundle = getArguments();
        if (bundle != null) {
            mExercise = bundle.getInt(STATE_EXERCISE, 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fragments = new ArrayList<>();
        ((MinaActivity) getActivity()).registerFragmentMondaiListener(this);
        onDataUpdate(mExercise, 4);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MinaActivity) getActivity()).unregisterFragmentMondaiListener();
    }

    @Override
    public void onDataUpdate(int ex, int exType) {
        List<Mondai> mondais = controller.getMondaiByLessonId(ex);
        Log.d("HAHA", "onDataUpdate: "+ex+" "+exType);
        fragments = new ArrayList<>();
        if (mondais.size() == 2) {
            fragments.add(FragmentMondai1.newInstance(ex));
            fragments.add(FragmentMondai3.newInstance(ex,"Mondai2"));
        } else if (mondais.size() == 3) {
            fragments.add(FragmentMondai1.newInstance(ex));
            fragments.add(FragmentMondai2.newInstance(ex));
            fragments.add(FragmentMondai3.newInstance(ex,"Mondai3"));
        }
        mPagerMondaiAdapter = new ViewPagerMondaiAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerMondaiAdapter);
    }

    public synchronized void registerListener(fragmentSelectListener listener, int indexFragment) {
        if (indexFragment == 1) mFragmentListener1 = listener;
        else if (indexFragment == 2) mFragmentListener2 = listener;
        else if (indexFragment == 3) mFragmentListener3 = listener;
    }

    public synchronized void unregisterListener(int indexFragment) {
        if (indexFragment == 1) mFragmentListener1 = null;
        else if (indexFragment == 2) mFragmentListener2 = null;
        else if (indexFragment == 3) mFragmentListener3 = null;
    }

    public interface fragmentSelectListener {
        void onUnselected();
    }
}
