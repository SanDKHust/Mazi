package vn.edu.hust.soict.khacsan.myapp.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.AlphabetController;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemListKana;
import vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentAlphabet;
import vn.edu.hust.soict.khacsan.myapp.view.fragment.ViewPagerAdapter;

public class AlphabetTableActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LIST_ITEM_KANA = "LIST_ITEM_KANA";
    public static final String TITLE_HIRAGANA = "Hiragana";
    public static final String TITLE_KATAKANA = "Katakana";
    public static final String TITLE_FRAGMENT = "TitleFragment";
    public static final String TYPE_KANA = "TYPE_KANA";
    public static final String MENU = "MENU";
    private Toolbar mToolbar;
    private TabLayout mTab;
    private ViewPager mViewPager;
    private View viewTabLeft, viewTabRight;
    private AlphabetController controller;
    private static boolean isAudio = true;
    private FloatingActionButton mFabBtnMenuKana, mFabBtnMenuHira;
    private FloatingActionMenu mFloatingActionMenu;
    private ArrayList<ItemListKana> mItemListKana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_table);
        if (Hawk.contains(MENU)) isAudio = Hawk.get(MENU);
        controller = new AlphabetController(getApplicationContext());
        initView();
        setupTabLayout();
    }

    private void setupTabLayout() {
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null)
                    if (tab.getPosition() == 0) {
                        ((TextView) view.findViewById(R.id.tab_title_left)).setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground((getResources().getDrawable(R.drawable.tab_left_select)));
                        } else {
                            view.setBackgroundDrawable((getResources().getDrawable(R.drawable.tab_left_select)));
                        }
                    } else {
                        ((TextView) view.findViewById(R.id.tab_title_right)).setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground((getResources().getDrawable(R.drawable.tab_right_select)));
                        } else {
                            view.setBackgroundDrawable((getResources().getDrawable(R.drawable.tab_right_select)));
                        }
                    }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null)
                    if (tab.getPosition() == 0) {
                        ((TextView) view.findViewById(R.id.tab_title_left)).setTextColor(getResources().getColor(R.color.colorWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground((getResources().getDrawable(R.drawable.border_tab_left)));
                        } else {
                            view.setBackgroundDrawable((getResources().getDrawable(R.drawable.border_tab_left)));
                        }
                    } else {
                        ((TextView) view.findViewById(R.id.tab_title_right)).setTextColor(getResources().getColor(R.color.colorWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground((getResources().getDrawable(R.drawable.border_tab_right)));
                        } else {
                            view.setBackgroundDrawable((getResources().getDrawable(R.drawable.border_tab_right)));
                        }
                    }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        mTab = findViewById(R.id.tab_bang_chu_cai);
        mToolbar = findViewById(R.id.toolbar_bang_chu_cai);
        mViewPager = findViewById(R.id.view_pager_alphabet);
        mFabBtnMenuHira = findViewById(R.id.fab_bottom_hira);
        mFabBtnMenuKana = findViewById(R.id.fab_bottom_kana);
        mFloatingActionMenu = findViewById(R.id.social_floating_menu);

        mFabBtnMenuKana.setOnClickListener(this);
        mFabBtnMenuHira.setOnClickListener(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        viewTabRight = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.custom_tab_right, null);
        viewTabLeft = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.custom_tab_left, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_ITEM_KANA, mItemListKana);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mItemListKana = savedInstanceState.getParcelableArrayList(LIST_ITEM_KANA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUi();
    }

    public void setupUi() {
        mTab.setupWithViewPager(mViewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (mItemListKana == null)
            mItemListKana = controller.getAlphabetKanas();

        adapter.addFragment(FragmentAlphabet.newInstance(mItemListKana, TITLE_HIRAGANA));
        adapter.addFragment(FragmentAlphabet.newInstance(mItemListKana, TITLE_KATAKANA));
        mViewPager.setAdapter(adapter);

        TabLayout.Tab tab = mTab.getTabAt(0);

        if (tab != null && viewTabLeft != null) {
            TextView textViewLeft = viewTabLeft.findViewById(R.id.tab_title_left);
            textViewLeft.setText(TITLE_HIRAGANA);
            textViewLeft.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewTabLeft.setBackground((getResources().getDrawable(R.drawable.tab_left_select)));
            } else {
                viewTabLeft.setBackgroundDrawable((getResources().getDrawable(R.drawable.tab_left_select)));
            }
            tab.setCustomView(viewTabLeft);
        }


        tab = mTab.getTabAt(1);
        if (tab != null && viewTabRight != null) {
            TextView textViewRight = viewTabRight.findViewById(R.id.tab_title_right);
            textViewRight.setText(TITLE_KATAKANA);
            textViewRight.setTextColor(getResources().getColor(R.color.colorWhite));
            tab.setCustomView(viewTabRight);
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alphabet_menu, menu);
        if (isAudio) menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_volume_up));
        else menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_import_contact));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_audio_flat) {
            if (isAudio) {
                item.setIcon(getResources().getDrawable(R.drawable.ic_import_contact));
                isAudio = false;
            } else {
                item.setIcon(getResources().getDrawable(R.drawable.ic_volume_up));
                isAudio = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Hawk.put(MENU, isAudio);
    }

    public static boolean isIsAudio() {
        return isAudio;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_bottom_kana) {
            Intent intent = new Intent(this, PracticeActivity.class);
            intent.putExtra(TYPE_KANA, 2);
            startActivity(intent);
            mFloatingActionMenu.close(true);
        } else if (id == R.id.fab_bottom_hira) {
            Intent intent = new Intent(this, PracticeActivity.class);
            intent.putExtra(TYPE_KANA, 1);
            startActivity(intent);
            mFloatingActionMenu.close(true);
        }
    }
}
