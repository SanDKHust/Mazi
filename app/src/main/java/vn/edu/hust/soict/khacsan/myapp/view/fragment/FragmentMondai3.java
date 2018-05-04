package vn.edu.hust.soict.khacsan.myapp.view.fragment;

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

import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai;
import vn.edu.hust.soict.khacsan.myapp.model.utils.AudioPlayer;
import vn.edu.hust.soict.khacsan.myapp.model.utils.Constant;

import static vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentMondaiMina.STATE_EXERCISE;

public class FragmentMondai3 extends Fragment implements View.OnClickListener, FragmentMondaiMina.fragmentSelectListener{
    private static final String STATE_TYPE = "STATE_TYPE";
    private ImageButton mBtnTrueQ1,mBtnFalseQ1,mBtnTrueQ2,mBtnFalseQ2,mBtnTrueQ3,
            mBtnFalseQ3,mBtnTrueQ4,mBtnFalseQ4,mBtnTrueQ5,mBtnFalseQ5;
    private int mAnswerQuestion1 = -1,mAnswerQuestion2 = -1,mAnswerQuestion3 = -1,
            mAnswerQuestion4 = -1,mAnswerQuestion5 = -1,mExercise;

    private TextView mTxtTimePlay,mTxtTotalTime;
    private ImageButton mBtnMediaPlayer,mBtnMediaVolume;
    private SeekBar mSeekBar;
    private Button mBtnCheck;
    private AudioPlayer audioPlayer;
    private MinaController controller;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private String type;
    private Mondai mondai;

    public static FragmentMondai3 newInstance(int ex,String type) {
        Bundle args = new Bundle();
        args.putInt(STATE_EXERCISE,ex);
        args.putString(STATE_TYPE,type);
        FragmentMondai3 fragment = new FragmentMondai3();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("KIKI", "onCreateView: fgm3");
        return inflater.inflate(R.layout.fragment_content_mondai_3,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            mExercise = bundle.getInt(STATE_EXERCISE,1);
            type = bundle.getString(STATE_TYPE);
        }

        mSeekBar = view.findViewById(R.id.seek_bar_fm_modai3);

        mBtnMediaPlayer = view.findViewById(R.id.btn_play_or_pause_fm_modai3);
        mBtnMediaVolume = view.findViewById(R.id.btn_volume_change_fm_modai3);

        mBtnCheck = view.findViewById(R.id.btn_check_fm_mondai3);
        mBtnCheck.setOnClickListener(this);

        mTxtTimePlay = view.findViewById(R.id.text_time_play_fm_modai3);
        mTxtTotalTime = view.findViewById(R.id.text_total_time_fm_modai3);

        avLoadingIndicatorView = view.findViewById(R.id.avi_progress_bar_fm_mondai3);

        audioPlayer = new AudioPlayer(getContext(),true,mBtnMediaPlayer,mBtnMediaVolume,avLoadingIndicatorView,mSeekBar,mTxtTimePlay,mTxtTotalTime);

        mBtnTrueQ1 = view.findViewById(R.id.btn_true_index1_fm_mondai3);
        mBtnTrueQ2 = view.findViewById(R.id.btn_true_index2_fm_mondai3);
        mBtnTrueQ3 = view.findViewById(R.id.btn_true_index3_fm_mondai3);
        mBtnTrueQ4 = view.findViewById(R.id.btn_true_index4_fm_mondai3);
        mBtnTrueQ5 = view.findViewById(R.id.btn_true_index5_fm_mondai3);

        mBtnFalseQ1 = view.findViewById(R.id.btn_false_index1_fm_mondai3);
        mBtnFalseQ2 = view.findViewById(R.id.btn_false_index2_fm_mondai3);
        mBtnFalseQ3 = view.findViewById(R.id.btn_false_index3_fm_mondai3);
        mBtnFalseQ4 = view.findViewById(R.id.btn_false_index4_fm_mondai3);
        mBtnFalseQ5 = view.findViewById(R.id.btn_false_index5_fm_mondai3);

        mBtnFalseQ1.setOnClickListener(this);
        mBtnFalseQ2.setOnClickListener(this);
        mBtnFalseQ3.setOnClickListener(this);
        mBtnFalseQ4.setOnClickListener(this);
        mBtnFalseQ5.setOnClickListener(this);

        mBtnTrueQ1.setOnClickListener(this);
        mBtnTrueQ2.setOnClickListener(this);
        mBtnTrueQ3.setOnClickListener(this);
        mBtnTrueQ4.setOnClickListener(this);
        mBtnTrueQ5.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        controller = new MinaController();
        mondai = controller.getMondaiByLessonIdAndType(mExercise,3);
        if(Integer.parseInt(mondai.getQuestion_num()) == 2){
            getView().findViewById(R.id.line_question_3).setVisibility(View.GONE);
            getView().findViewById(R.id.line_question_4).setVisibility(View.GONE);
            getView().findViewById(R.id.line_question_5).setVisibility(View.GONE);
        }else {
            if(Integer.parseInt(mondai.getQuestion_num()) == 3){
                getView().findViewById(R.id.line_question_4).setVisibility(View.GONE);
                getView().findViewById(R.id.line_question_5).setVisibility(View.GONE);
            }
        }

        if(getParentFragment() instanceof FragmentMondaiMina){
            audioPlayer.reset(Constant.URL.AUDIO + controller.getLinkAudio(mExercise, type),0);
            ((FragmentMondaiMina)  getParentFragment()).registerListener(this,3);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        if(audioPlayer != null) audioPlayer.release();
        audioPlayer = null;
        if(getParentFragment() instanceof FragmentMondaiMina){
            ((FragmentMondaiMina)  getParentFragment()).unregisterListener(3);
        }
    }

    @Override
    public void onUnselected() {
        audioPlayer.pause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_true_index1_fm_mondai3:{
                if(mAnswerQuestion1 == 0) mBtnFalseQ1.setImageResource(R.drawable.ic_close_black_png);
                if(mAnswerQuestion1 != 1) mBtnTrueQ1.setImageResource(R.drawable.ic_check_green_png);
                mAnswerQuestion1 = 1;
                break;
            }
            case R.id.btn_true_index2_fm_mondai3:{
                if(mAnswerQuestion2 == 0) mBtnFalseQ2.setImageResource(R.drawable.ic_close_black_png);
                if(mAnswerQuestion2 != 1) mBtnTrueQ2.setImageResource(R.drawable.ic_check_green_png);
                mAnswerQuestion2 = 1;
                break;
            }
            case R.id.btn_true_index3_fm_mondai3:{
                if(mAnswerQuestion3 == 0) mBtnFalseQ3.setImageResource(R.drawable.ic_close_black_png);
                if(mAnswerQuestion3 != 1) mBtnTrueQ3.setImageResource(R.drawable.ic_check_green_png);
                mAnswerQuestion3 = 1;
                break;
            }
            case R.id.btn_true_index4_fm_mondai3:{
                if(mAnswerQuestion4 == 0) mBtnFalseQ4.setImageResource(R.drawable.ic_close_black_png);
                if(mAnswerQuestion4 != 1) mBtnTrueQ4.setImageResource(R.drawable.ic_check_green_png);
                mAnswerQuestion4 = 1;
                break;
            }
            case R.id.btn_true_index5_fm_mondai3:{
                if(mAnswerQuestion5 == 0) mBtnFalseQ5.setImageResource(R.drawable.ic_close_black_png);
                if(mAnswerQuestion5 != 1) mBtnTrueQ5.setImageResource(R.drawable.ic_check_green_png);
                mAnswerQuestion5 = 1;
                break;
            }

            case R.id.btn_false_index1_fm_mondai3:{
                if(mAnswerQuestion1 != 0) mBtnFalseQ1.setImageResource(R.drawable.ic_close_green_png);
                if(mAnswerQuestion1 == 1) mBtnTrueQ1.setImageResource(R.drawable.ic_check_black_png);
                mAnswerQuestion1 = 0;
                break;
            }
            case R.id.btn_false_index2_fm_mondai3:{
                if(mAnswerQuestion2 != 0) mBtnFalseQ2.setImageResource(R.drawable.ic_close_green_png);
                if(mAnswerQuestion2 == 1) mBtnTrueQ2.setImageResource(R.drawable.ic_check_black_png);
                mAnswerQuestion2 = 0;
                break;
            }
            case R.id.btn_false_index3_fm_mondai3:{
                if(mAnswerQuestion3 != 0) mBtnFalseQ3.setImageResource(R.drawable.ic_close_green_png);
                if(mAnswerQuestion3 == 1) mBtnTrueQ3.setImageResource(R.drawable.ic_check_black_png);
                mAnswerQuestion3 = 0;
                break;
            }
            case R.id.btn_false_index4_fm_mondai3:{
                if(mAnswerQuestion4 != 0) mBtnFalseQ4.setImageResource(R.drawable.ic_close_green_png);
                if(mAnswerQuestion4 == 1) mBtnTrueQ4.setImageResource(R.drawable.ic_check_black_png);
                mAnswerQuestion4 = 0;
                break;
            }
            case R.id.btn_false_index5_fm_mondai3:{
                if(mAnswerQuestion5 != 0) mBtnFalseQ5.setImageResource(R.drawable.ic_close_green_png);
                if(mAnswerQuestion5 == 1) mBtnTrueQ5.setImageResource(R.drawable.ic_check_black_png);
                mAnswerQuestion5 = 0;
                break;
            }

            case R.id.btn_check_fm_mondai3:{
                List<String> answers = Arrays.asList(mondai.getAnswer().split("※"));
                for(int i = 0; i < answers.size(); i++){
                    if(i== 0){
                        switch (mAnswerQuestion1){
                            case  0:{
                                if(!answers.get(0).equals("f")) mBtnTrueQ1.setImageResource(R.drawable.ic_check_red_png);
                                break;
                            }
                            case 1:{
                                if(!answers.get(0).equals("t")) mBtnFalseQ1.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                            default:{
                                if(answers.get(0).equals("t")) mBtnTrueQ1.setImageResource(R.drawable.ic_check_red_png);
                                else mBtnFalseQ1.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                        }
                    }else if (i== 1){
                        switch (mAnswerQuestion2){
                            case  0:{
                                if(!answers.get(1).equals("f")) mBtnTrueQ2.setImageResource(R.drawable.ic_check_red_png);
                                break;
                            }
                            case 1:{
                                if(!answers.get(1).equals("t")) mBtnFalseQ2.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                            default:{
                                if(answers.get(1).equals("t")) mBtnTrueQ2.setImageResource(R.drawable.ic_check_red_png);
                                else mBtnFalseQ2.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                        }
                    }else if (i== 2){
                        switch (mAnswerQuestion3){
                            case  0:{
                                if(!answers.get(2).equals("f")) mBtnTrueQ3.setImageResource(R.drawable.ic_check_red_png);
                                break;
                            }
                            case 1:{
                                if(!answers.get(2).equals("t")) mBtnFalseQ3.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                            default:{
                                if(answers.get(2).equals("t")) mBtnTrueQ3.setImageResource(R.drawable.ic_check_red_png);
                                else mBtnFalseQ3.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                        }
                    }else if (i == 3){
                        switch (mAnswerQuestion4){
                            case  0:{
                                if(!answers.get(3).equals("f")) mBtnTrueQ4.setImageResource(R.drawable.ic_check_red_png);
                                break;
                            }
                            case 1:{
                                if(!answers.get(3).equals("t")) mBtnFalseQ4.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                            default:{
                                if(answers.get(3).equals("t")) mBtnTrueQ4.setImageResource(R.drawable.ic_check_red_png);
                                else mBtnFalseQ4.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                        }
                    }else if (i == 4){
                        switch (mAnswerQuestion2){
                            case  0:{
                                if(!answers.get(4).equals("f")) mBtnTrueQ5.setImageResource(R.drawable.ic_check_red_png);
                                break;
                            }
                            case 1:{
                                if(!answers.get(4).equals("t")) mBtnFalseQ5.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                            default:{
                                if(answers.get(4).equals("t")) mBtnTrueQ5.setImageResource(R.drawable.ic_check_red_png);
                                else mBtnFalseQ2.setImageResource(R.drawable.ic_close_red_png);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
