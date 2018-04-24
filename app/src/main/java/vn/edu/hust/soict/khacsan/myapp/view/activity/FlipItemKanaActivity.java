package vn.edu.hust.soict.khacsan.myapp.view.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Kana;
import vn.edu.hust.soict.khacsan.myapp.view.Fragment.ClickFlipListener;
import vn.edu.hust.soict.khacsan.myapp.view.Fragment.FragmentContainerFlip;
import vn.edu.hust.soict.khacsan.myapp.view.Fragment.ViewPageFlipAdapter;

import static vn.edu.hust.soict.khacsan.myapp.view.Fragment.FragmentAlphabet.KANA_SELECT_FLIP;
import static vn.edu.hust.soict.khacsan.myapp.view.Fragment.FragmentAlphabet.TYPE_TABLE;

public class FlipItemKanaActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, ClickFlipListener {
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ViewPageFlipAdapter adapter;
    private Kana kana;
    private List<Kana> kanas;
    private int typeTable;
    private int page;
    private int mPositionLeft = 0, mPositionRight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_item_kana);
        mViewPager = findViewById(R.id.view_pager_flip);
        mToolbar = findViewById(R.id.toolbar_flip);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mToolbar.setTitle("Card flip");

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(KANA_SELECT_FLIP) && intent.hasExtra(TYPE_TABLE)) {
            kana = intent.getParcelableExtra(KANA_SELECT_FLIP);
            typeTable = intent.getIntExtra(TYPE_TABLE, 1);
        } else return;

        kanas = SQLite.select().from(Kana.class).queryList();
        page = kanas.size();

        int pos = kanas.indexOf(kana);
        mPositionLeft = pos == 0 ? pos : (pos - 1);
        mPositionRight = pos == (page - 1) ? pos : pos + 1;

        adapter = new ViewPageFlipAdapter(getSupportFragmentManager());
        for (int i = 0; i < pos + 2; i++) {
            adapter.addRightFragment(FragmentContainerFlip.newInstance(kanas.get(i), typeTable, page, this));
        }


//        if(pos != (page -1)) adapter.addRightFragment(FragmentContainerFlip.newInstance(kanas.get(mPositionRight),typeTable,page));
//        if(pos != 0) adapter.addLeftFragment(FragmentContainerFlip.newInstance(kanas.get(mPositionLeft),typeTable,page));


        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true, new RotateUpTransformer());
        mViewPager.setCurrentItem(pos);

        mViewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == (adapter.getCount() - 1)) {
            if (++mPositionRight < page) {
                adapter.addRightFragment(FragmentContainerFlip.newInstance(kanas.get(mPositionRight), typeTable, page, this));
            }
        }
//        if(position == 0){
//            if(--mPositionLeft >= 0){
//                adapter.addLeftFragment(FragmentContainerFlip.newInstance(kanas.get(mPositionLeft),typeTable,page));
//            }
//        }
//        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onFlip(boolean isBack, boolean isRight) {
        if (isBack) {
            if (isRight) {
                if (adapter.getCount() != mViewPager.getCurrentItem() + 1)
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            } else {
                if (mViewPager.getCurrentItem() != 0)
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        }
    }

    @Override
    public void onPlayAudio(boolean isBack) {

    }
}
