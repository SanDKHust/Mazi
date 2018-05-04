package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapButtonGroup;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai;
import vn.edu.hust.soict.khacsan.myapp.model.utils.AudioPlayer;
import vn.edu.hust.soict.khacsan.myapp.model.utils.Constant;
import vn.edu.hust.soict.khacsan.myapp.model.utils.CustomBootstrapStyle;

import static android.graphics.PorterDuff.Mode.CLEAR;
import static vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentMondaiMina.STATE_EXERCISE;

public class FragmentMondai2 extends Fragment implements FragmentMondaiMina.fragmentSelectListener, View.OnClickListener {
    private int mExercise;
    private BootstrapButtonGroup mBtnGroupAnswer1,mBtnGroupAnswer2,mBtnGroupAnswer3;
    private TextView mTxtTimePlay,mTxtTotalTime;
    private ImageButton mBtnMediaPlayer,mBtnMediaVolume;
    private SeekBar mSeekBar;
    private Button mBtnCheck;
    private AudioPlayer audioPlayer;
    private MinaController controller;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private Mondai mondai;
    private BootstrapButton mBtnAnswer11,mBtnAnswer12,mBtnAnswer13,mBtnAnswer21,mBtnAnswer22,mBtnAnswer23,mBtnAnswer31,
    mBtnAnswer32,mBtnAnswer33;

    public static FragmentMondai2 newInstance(int ex) {
        Bundle args = new Bundle();
        args.putInt(STATE_EXERCISE,ex);
        FragmentMondai2 fragment = new FragmentMondai2();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_mondai_2,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            mExercise = bundle.getInt(STATE_EXERCISE,1);
        }


        mSeekBar = view.findViewById(R.id.seek_bar_fm_modai2);

        mBtnMediaPlayer = view.findViewById(R.id.btn_play_or_pause_fm_modai2);
        mBtnMediaVolume = view.findViewById(R.id.btn_volume_change_fm_modai2);

        mBtnCheck = view.findViewById(R.id.btn_check_fm_mondai2);
        mBtnCheck.setOnClickListener(this);

        mTxtTimePlay = view.findViewById(R.id.text_time_play_fm_modai2);
        mTxtTotalTime = view.findViewById(R.id.text_total_time_fm_modai2);

        avLoadingIndicatorView = view.findViewById(R.id.avi_progress_bar_fm_mondai2);

        audioPlayer = new AudioPlayer(getContext(),true,mBtnMediaPlayer,mBtnMediaVolume,avLoadingIndicatorView,mSeekBar,mTxtTimePlay,mTxtTotalTime);

        CustomBootstrapStyle  bootstrapStyle = new CustomBootstrapStyle();
        mBtnGroupAnswer1 = view.findViewById(R.id.btn_group1);
        mBtnGroupAnswer2 = view.findViewById(R.id.btn_group2);
        mBtnGroupAnswer3 = view.findViewById(R.id.btn_group3);

        mBtnAnswer11 = view.findViewById(R.id.btn_answer_1_index1);
        mBtnAnswer12 = view.findViewById(R.id.btn_answer_2_index1);
        mBtnAnswer13 = view.findViewById(R.id.btn_answer_3_index1);
        mBtnAnswer21 = view.findViewById(R.id.btn_answer_1_index2);
        mBtnAnswer22 = view.findViewById(R.id.btn_answer_2_index2);
        mBtnAnswer23 = view.findViewById(R.id.btn_answer_3_index2);
        mBtnAnswer31 = view.findViewById(R.id.btn_answer_1_index3);
        mBtnAnswer32 = view.findViewById(R.id.btn_answer_2_index3);
        mBtnAnswer33 = view.findViewById(R.id.btn_answer_3_index3);


        mBtnGroupAnswer1.setBootstrapBrand(bootstrapStyle);
        mBtnGroupAnswer2.setBootstrapBrand(bootstrapStyle);
        mBtnGroupAnswer3.setBootstrapBrand(bootstrapStyle);
    }

    @Override
    public void onResume() {
        super.onResume();
        controller = new MinaController();
        mondai = controller.getMondaiByLessonIdAndType(mExercise,2);
        if(Integer.parseInt(mondai.getQuestion_num()) == 2){
            mBtnGroupAnswer3.setVisibility(View.GONE);
            getView().findViewById(R.id.text_index_3_fm_mondai2).setVisibility(View.GONE);
        }

        if(getParentFragment() instanceof FragmentMondaiMina){
            audioPlayer.reset(Constant.URL.AUDIO + controller.getLinkAudio(mExercise, "Mondai2"),0);
            ((FragmentMondaiMina)  getParentFragment()).registerListener(this,2);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(audioPlayer != null) audioPlayer.release();
        audioPlayer = null;
        if(getParentFragment() instanceof FragmentMondaiMina){
            ((FragmentMondaiMina)  getParentFragment()).unregisterListener(2);
        }

    }

    @Override
    public void onUnselected() {
        audioPlayer.pause();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_check_fm_mondai2){
            List<String> answers = Arrays.asList(mondai.getAnswer().split("※"));
            for (int i = 0; i < answers.size(); i++){
                if(i == 0){
                    switch (Integer.parseInt(answers.get(0))){
                        case 1:{

                            if(!mBtnAnswer11.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer11.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer11.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                        case 2:{
                            if(!mBtnAnswer12.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer12.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer12.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                        case 3:{
                            if(!mBtnAnswer13.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer13.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer13.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                    }
                }else if(i == 1){
                    switch (Integer.parseInt(answers.get(1))){
                        case 1:{
                            if(!mBtnAnswer21.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer21.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer21.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                        case 2:{
                            if(!mBtnAnswer22.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer22.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer22.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                        case 3:{
                            if(!mBtnAnswer23.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer23.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer23.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                    }
                }else if(i == 2){
                    switch (Integer.parseInt(answers.get(2))){
                        case 1:{
                            if(!mBtnAnswer31.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer31.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer31.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                        case 2:{
                            if(!mBtnAnswer32.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer32.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer32.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                        case 3:{
                            if(!mBtnAnswer33.isSelected()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mBtnAnswer33.setBackground(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }else {
                                    mBtnAnswer33.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_incorrect_mondai));
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
