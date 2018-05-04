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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai;
import vn.edu.hust.soict.khacsan.myapp.model.utils.AudioPlayer;
import vn.edu.hust.soict.khacsan.myapp.model.utils.Constant;

import static vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentMondaiMina.STATE_EXERCISE;

public class FragmentMondai1 extends Fragment implements FragmentMondaiMina.fragmentSelectListener, View.OnClickListener {
    private int mExercise;
    private TextView mTxtTimePlay,mTxtTotalTime;
    private ImageButton mBtnMediaPlayer,mBtnMediaVolume;
    private SeekBar mSeekBar;
    private EditText mEdtAnswer1,mEdtAnswer2,mEdtAnswer3,mEdtAnswer4,mEdtAnswer5;
    private TextView mTxtAnswer1,mTxtAnswer2,mTxtAnswer3,mTxtAnswer4,mTxtAnswer5;
    private Button mBtnCheck;
    private AudioPlayer audioPlayer;
    private MinaController controller;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    public static FragmentMondai1 newInstance(int ex) {
        Bundle args = new Bundle();
        args.putInt(STATE_EXERCISE,ex);
        FragmentMondai1 fragment = new FragmentMondai1();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_mondai_1,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null){
            mExercise = bundle.getInt(STATE_EXERCISE,1);
        }

        mEdtAnswer1 = view.findViewById(R.id.edt_answer_1_fm_mondai1);
        mEdtAnswer2 = view.findViewById(R.id.edt_answer_2_fm_mondai1);
        mEdtAnswer3 = view.findViewById(R.id.edt_answer_3_fm_mondai1);
        mEdtAnswer4 = view.findViewById(R.id.edt_answer_4_fm_mondai1);
        mEdtAnswer5 = view.findViewById(R.id.edt_answer_5_fm_mondai1);

        mTxtAnswer1 = view.findViewById(R.id.text_answer1);
        mTxtAnswer2 = view.findViewById(R.id.text_answer2);
        mTxtAnswer3 = view.findViewById(R.id.text_answer3);
        mTxtAnswer4 = view.findViewById(R.id.text_answer4);
        mTxtAnswer5 = view.findViewById(R.id.text_answer5);

        mSeekBar = view.findViewById(R.id.seek_bar_fm_modai1);

        view.findViewById(R.id.btn_check_fm_mondai1).setOnClickListener(this);
        mBtnMediaPlayer = view.findViewById(R.id.btn_play_or_pause_fm_modai1);
        mBtnMediaVolume = view.findViewById(R.id.btn_volume_change_fm_modai1);

        mBtnCheck = view.findViewById(R.id.btn_check_fm_mondai1);

        mTxtTimePlay = view.findViewById(R.id.text_time_play_fm_modai1);
        mTxtTotalTime = view.findViewById(R.id.text_total_time_fm_modai1);

        avLoadingIndicatorView = view.findViewById(R.id.avi_progress_bar_fm_mondai1);

        audioPlayer = new AudioPlayer(getContext(),true,mBtnMediaPlayer,mBtnMediaVolume,avLoadingIndicatorView,mSeekBar,mTxtTimePlay,mTxtTotalTime);
    }


    @Override
    public void onResume() {
        super.onResume();
        controller = new MinaController();
        if(getParentFragment() instanceof FragmentMondaiMina){
            audioPlayer.reset(Constant.URL.AUDIO + controller.getLinkAudio(mExercise, "Mondai1"),0);
            ((FragmentMondaiMina) getParentFragment()).registerListener(this,1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(audioPlayer != null) audioPlayer.release();
        audioPlayer = null;
        if(getParentFragment() instanceof FragmentMondaiMina){
            ((FragmentMondaiMina)  getParentFragment()).unregisterListener(1);
        }
    }


    @Override
    public void onUnselected() {
        audioPlayer.pause();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_check_fm_mondai1){
            Mondai mondai = controller.getMondaiByLessonIdAndType(mExercise,1);
            List<String> items = Arrays.asList(mondai.getAnswer().split("∴"));
            List<String> japanese;
            List<String> phienAms;
            try {
                japanese = Arrays.asList(items.get(0).split("※"));
                phienAms = Arrays.asList(items.get(1).split("※"));
                mTxtAnswer1.setText(japanese.get(0)+"\n"+phienAms.get(0));
                mTxtAnswer2.setText(japanese.get(1)+"\n"+phienAms.get(1));
                mTxtAnswer3.setText(japanese.get(2)+"\n"+phienAms.get(2));
                mTxtAnswer4.setText(japanese.get(3)+"\n"+phienAms.get(3));
                mTxtAnswer5.setText(japanese.get(4)+"\n"+phienAms.get(4));
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }

        }
    }
}
