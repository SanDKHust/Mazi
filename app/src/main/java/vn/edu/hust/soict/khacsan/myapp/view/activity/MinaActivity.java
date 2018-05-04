package vn.edu.hust.soict.khacsan.myapp.view.activity;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;
import vn.edu.hust.soict.khacsan.myapp.model.database.Bunkei;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kaiwa;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kotoba;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reference;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemReibun;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.BunkeiAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.GrammarAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.KaiwaAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.KotobaAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.ReferenceAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.ReibunAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentMondaiMina;
import vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentRecyclerMina;

public class MinaActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXERCISE_NUM = "EXERCISE_NUM";
    public static final String EXERCISE_TYPE = "EXERCISE_TYPE";
    public static final String IS_MONDAI_SELECT = "IS_MONDAI_SELECT";
    public static final String FRAGMENT_MONDAI = "FRAGMENT_MONDAI";
    public static final String FRAGMENT_RECYCLER = "FRAGMENT_RECYCLER";
    private static final String TAG = MinaActivity.class.getSimpleName();

    private BottomSheetBehavior mBottomSheet;
    private Toolbar mToolbar;
    private RelativeLayout mRelativeLayoutBottomSheet;
    private String[] mListNameExercise,mListTypeExercise;
    private NumberPicker mPicker;
    private boolean isEx = true,isMondaiSelect = false;
    private Button mBtnEx,mBtnExType;

    private int mExercise = 1,mExerciseType = 1;
    private FragmentMondaiMina mFragmentMondaiMina;
    private FragmentRecyclerMina mFragmentRecyclerMina;
    private FragmentManager mFragmentManager;
    private DataUpdateListener mRecyclerDataListener,mMondaiDataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina);

        mFragmentManager = getSupportFragmentManager();
        initView();

        mListNameExercise = getResources().getStringArray(R.array.name_exercise);
        mListTypeExercise = getResources().getStringArray(R.array.type_exercise);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mExercise = savedInstanceState.getInt(EXERCISE_NUM,1);
        mExerciseType = savedInstanceState.getInt(EXERCISE_TYPE,1);
        isMondaiSelect = savedInstanceState.getBoolean(IS_MONDAI_SELECT);
        if(isMondaiSelect)
            mFragmentMondaiMina = (FragmentMondaiMina) getSupportFragmentManager().getFragment(savedInstanceState,FRAGMENT_MONDAI);
        else   mFragmentRecyclerMina = (FragmentRecyclerMina) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_RECYCLER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBtnEx.setText(mListNameExercise[mExercise - 1]);
        mBtnExType.setText(mListTypeExercise[mExerciseType -1]);
        if(isMondaiSelect){
            if(mFragmentMondaiMina == null) mFragmentMondaiMina = FragmentMondaiMina.newInstance(mExercise);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.container_mina, mFragmentMondaiMina);
            transaction.commit();
        }else {
            if(mFragmentRecyclerMina == null)
                mFragmentRecyclerMina = FragmentRecyclerMina.newInstance(mExercise, mExerciseType);

            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.container_mina, mFragmentRecyclerMina);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXERCISE_NUM,mExercise);
        outState.putInt(EXERCISE_TYPE,mExerciseType);
        outState.putBoolean(IS_MONDAI_SELECT,isMondaiSelect);
        if(!isMondaiSelect) getSupportFragmentManager().putFragment(outState, FRAGMENT_RECYCLER, mFragmentRecyclerMina);
        else getSupportFragmentManager().putFragment(outState,FRAGMENT_MONDAI,mFragmentMondaiMina);
    }



    private void setupUi(int lesson_id, int exType) {
        if(exType == 4){
            if(isMondaiSelect){
                if(mMondaiDataListener != null) mMondaiDataListener.onDataUpdate(lesson_id, exType);
            }else {
                if(mFragmentMondaiMina == null) mFragmentMondaiMina = FragmentMondaiMina.newInstance(lesson_id);
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.remove(mFragmentRecyclerMina);
                transaction.replace(R.id.container_mina, mFragmentMondaiMina);
                transaction.commit();
                isMondaiSelect = true;
            }
        }else {
            if(isMondaiSelect){
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.remove(mFragmentMondaiMina);
                if(mFragmentRecyclerMina == null) mFragmentRecyclerMina = FragmentRecyclerMina.newInstance(lesson_id, exType);
                transaction.replace(R.id.container_mina, mFragmentRecyclerMina);
                transaction.commit();
                isMondaiSelect = false;
            }
            if(mRecyclerDataListener != null)mRecyclerDataListener.onDataUpdate(lesson_id, exType);
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_mina);

        mRelativeLayoutBottomSheet = findViewById(R.id.bottom_sheet_mina);
        mPicker = findViewById(R.id.picker_bottom_sheet);

        findViewById(R.id.btn_done_picker).setOnClickListener(this);
        findViewById(R.id.btn_bottom_sheet_drop_up).setOnClickListener(this);
        findViewById(R.id.btn_bottom_sheet_drop_down).setOnClickListener(this);

        mBottomSheet = BottomSheetBehavior.from(mRelativeLayoutBottomSheet);

        mToolbar.setTitle("50 Bài Mina");
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mBtnEx = findViewById(R.id.btn_mina_select_exercise);
        mBtnEx.setOnClickListener(this);
        mBtnExType = findViewById(R.id.btn_mina_select_type);
        mBtnExType.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case  R.id.btn_mina_select_exercise: {
                setupValuePickerEx();
                mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            }
            case R.id.btn_mina_select_type: {
                setupValuePickerExType();
                mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            }

            case R.id.btn_bottom_sheet_drop_down:{
                mBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            }
            case R.id.btn_bottom_sheet_drop_up:{
                if(isEx) {
                    setupValuePickerExType();
                }
                else {
                    setupValuePickerEx();
                }
                break;
            }

            case R.id.btn_done_picker:{
                if(isEx){
                    mExercise = mPicker.getValue() + 1;
                    mBtnEx.setText(mListNameExercise[mExercise - 1]);
                } else {
                    mExerciseType = mPicker.getValue() + 1;
                    mBtnExType.setText(mListTypeExercise[mExerciseType - 1]);
                }

                setupUi(mExercise,mExerciseType);
                mBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            }
        }
    }

    private void setupValuePickerExType(){
        mPicker.setDisplayedValues(null);
        isEx = false;
        mPicker.setMinValue(0);
        mPicker.setMaxValue(mListTypeExercise.length - 1);
        mPicker.setDisplayedValues(mListTypeExercise);
        mPicker.setValue(mExerciseType - 1);
        mPicker.setWrapSelectorWheel(true);
    }

    private void setupValuePickerEx(){
        isEx = true;
        mPicker.setDisplayedValues(null);
        mPicker.setMinValue(0);
        mPicker.setMaxValue(mListNameExercise.length - 1);
        mPicker.setDisplayedValues(mListNameExercise);
        mPicker.setValue(mExercise - 1);
        mPicker.setWrapSelectorWheel(true);
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
    public void onBackPressed() {
        if(mBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED){
            mBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else super.onBackPressed();
    }

    public interface DataUpdateListener {
        void onDataUpdate(int ex, int exType);
    }
    public synchronized void registerFragmentRecyclerListener(DataUpdateListener listener) {
        mRecyclerDataListener = listener;
    }

    public synchronized void unregisterFragmentRecyclerListener() {
        mRecyclerDataListener = null;
    }

    public synchronized void registerFragmentMondaiListener(DataUpdateListener listener) {
        mMondaiDataListener = listener;
    }

    public synchronized void unregisterFragmentMondaiListener() {
        mMondaiDataListener = null;
    }

}
