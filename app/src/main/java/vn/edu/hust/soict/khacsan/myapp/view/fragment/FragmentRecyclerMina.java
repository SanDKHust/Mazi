package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;
import vn.edu.hust.soict.khacsan.myapp.model.database.Bunkei;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kaiwa;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kotoba;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reference;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemReibun;
import vn.edu.hust.soict.khacsan.myapp.model.utils.Constant;
import vn.edu.hust.soict.khacsan.myapp.view.activity.MinaActivity;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.BunkeiAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.GrammarAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.KaiwaAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.KotobaAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.ReferenceAdapter;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.ReibunAdapter;

import static vn.edu.hust.soict.khacsan.myapp.view.activity.MinaActivity.EXERCISE_NUM;
import static vn.edu.hust.soict.khacsan.myapp.view.activity.MinaActivity.EXERCISE_TYPE;

public class FragmentRecyclerMina extends Fragment implements MinaActivity.DataUpdateListener,
        MediaPlayer.OnCompletionListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String STATE_AUDIO = "STATE_AUDIO";
    public static final String STATE_MEDIAPlAYER = "STATE_MEDIAPlAYER";
    private int mExercise = 1, mExerciseType = 1;
    private ImageButton mBtnMediaPlayer, mBtnMediaVolume;
    private LinearLayout mLinearLayoutAudio;
    private String[] mListListenType;
    private ReferenceAdapter referenceAdapter;
    private ReibunAdapter reibunAdapter;
    private BunkeiAdapter bunkeiAdapter;
    private KaiwaAdapter kaiwaAdapter;
    private KotobaAdapter kotobaAdapter;
    private RecyclerView mRecyclerView;
    private MinaController mController;
    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBarAudio;
    private TextView mTxtTimePlay, mTxtTotalTimeAudio;
    private Handler handler;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private boolean isAudio = true;
    private int currentPos = 0;


    public static FragmentRecyclerMina newInstance(int ex, int exType) {
        Bundle args = new Bundle();
        args.putInt(EXERCISE_NUM, ex);
        args.putInt(EXERCISE_TYPE, exType);
        FragmentRecyclerMina fragment = new FragmentRecyclerMina();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_mina, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mExercise = savedInstanceState.getInt(EXERCISE_NUM, 1);
            mExerciseType = savedInstanceState.getInt(EXERCISE_TYPE, 1);
            isAudio = savedInstanceState.getBoolean(STATE_AUDIO);
            currentPos = savedInstanceState.getInt(STATE_MEDIAPlAYER, 0);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_mina);
        mLinearLayoutAudio = view.findViewById(R.id.line_audio);
        mBtnMediaPlayer = view.findViewById(R.id.btn_play_or_pause);
        mTxtTotalTimeAudio = view.findViewById(R.id.text_total_time_audio);
        mTxtTimePlay = view.findViewById(R.id.text_time_play);
        mSeekBarAudio = view.findViewById(R.id.seek_bar_audio);
        avLoadingIndicatorView = view.findViewById(R.id.avi_progress_bar);
        mBtnMediaVolume = view.findViewById(R.id.btn_volume_change_fm_recycler);

        mSeekBarAudio.setOnSeekBarChangeListener(this);
        mBtnMediaPlayer.setOnClickListener(this);
        mBtnMediaVolume.setOnClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mController = new MinaController();
        mListListenType = getResources().getStringArray(R.array.type_listen);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mExercise = bundle.getInt(EXERCISE_NUM, 1);
            mExerciseType = bundle.getInt(EXERCISE_TYPE, 1);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXERCISE_NUM, mExercise);
        outState.putInt(EXERCISE_TYPE, mExerciseType);
        outState.putBoolean(STATE_AUDIO, isAudio);
        outState.putInt(STATE_MEDIAPlAYER, mMediaPlayer.getCurrentPosition());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this);
        ((MinaActivity) getActivity()).registerFragmentRecyclerListener(this);
        setupUi(mExercise, mExerciseType);
        if (!isAudio) {
            mMediaPlayer.setVolume(0, 0);
            mBtnMediaVolume.setImageResource(R.drawable.ic_volume_off);
        }
    }

    private void setupUi(int lesson_id, int exType) {
        mExercise = lesson_id;
        mExerciseType = exType;
        int mListenType = -1;

        switch (exType) {
            case 1: {
                mLinearLayoutAudio.setVisibility(View.VISIBLE);
                mListenType = 0;
                List<Kotoba> kotobas = mController.getKotobaByLessonId(lesson_id);
                if (kotobaAdapter == null)
                    kotobaAdapter = new KotobaAdapter(R.layout.item_kotoba_bunkei_reference, kotobas);
                else kotobaAdapter.setNewData(kotobas);
                mRecyclerView.setAdapter(kotobaAdapter);
                break;
            }
            case 2: {
                mLinearLayoutAudio.setVisibility(View.GONE);
                List<Grammar> grammars = mController.getGrammarByLessonId(lesson_id);
                GrammarAdapter grammarAdapter = new GrammarAdapter(grammars);
                mRecyclerView.setAdapter(grammarAdapter);
                break;
            }
            case 3: {
                mLinearLayoutAudio.setVisibility(View.VISIBLE);
                mListenType = 3;
                List<Kaiwa> kaiwas = mController.getKaiwaByLessonId(lesson_id);
                if (kaiwaAdapter == null)
                    kaiwaAdapter = new KaiwaAdapter(R.layout.item_kaiwa, kaiwas);
                else kaiwaAdapter.setNewData(kaiwas);
                mRecyclerView.setAdapter(kaiwaAdapter);
                break;
            }
            case 5: {
                mLinearLayoutAudio.setVisibility(View.VISIBLE);
                mListenType = 1;
                List<Bunkei> bunkeis = mController.getBunkeiByLessonId(lesson_id);
                if (bunkeiAdapter == null)
                    bunkeiAdapter = new BunkeiAdapter(R.layout.item_kotoba_bunkei_reference, bunkeis);
                else bunkeiAdapter.setNewData(bunkeis);
                mRecyclerView.setAdapter(bunkeiAdapter);
                break;
            }
            case 6: {
                mLinearLayoutAudio.setVisibility(View.VISIBLE);
                mListenType = 2;
                List<ItemReibun> reibuns = mController.getItemReibunByLessonId(lesson_id);
                if (reibunAdapter == null)
                    reibunAdapter = new ReibunAdapter(R.layout.item_reibun, reibuns);
                else reibunAdapter.setNewData(reibuns);
                mRecyclerView.setAdapter(reibunAdapter);
            }
            case 7: {
                mLinearLayoutAudio.setVisibility(View.GONE);
                List<Reference> references = mController.getReferenceByLessonId(lesson_id);
                if (referenceAdapter == null)
                    referenceAdapter = new ReferenceAdapter(R.layout.item_kotoba_bunkei_reference, references);
                else referenceAdapter.setNewData(references);
                mRecyclerView.setAdapter(referenceAdapter);
            }
        }

        if (mListenType != -1) {
            try {
                mBtnMediaPlayer.setImageResource(R.drawable.ic_play_arrow);
                avLoadingIndicatorView.smoothToShow();
                mMediaPlayer.reset();
                mSeekBarAudio.setProgress(0);
                mTxtTotalTimeAudio.setText(new SimpleDateFormat("mm:ss").format(0));
                mTxtTimePlay.setText(new SimpleDateFormat("mm:ss").format(0));
                mMediaPlayer.setDataSource(Constant.URL.AUDIO + mController.getLinkAudio(mExercise, mListListenType[mListenType]));
                mMediaPlayer.prepareAsync();
                mBtnMediaPlayer.setVisibility(View.GONE);
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mTxtTotalTimeAudio.setText(new SimpleDateFormat("mm:ss").format(mMediaPlayer.getDuration()));
                        mSeekBarAudio.setMax(mMediaPlayer.getDuration());
                        avLoadingIndicatorView.smoothToHide();
                        mBtnMediaPlayer.setVisibility(View.VISIBLE);
                        if (currentPos != 0) {
                            mMediaPlayer.seekTo(currentPos);
                            mMediaPlayer.start();
                            mBtnMediaPlayer.setImageResource(R.drawable.ic_pause);
                            updateTimeSeekBar();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mSeekBarAudio.setProgress(0);
        mp.seekTo(0);
        mBtnMediaPlayer.setImageResource(R.drawable.ic_play_arrow);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_play_or_pause) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mBtnMediaPlayer.setImageResource(R.drawable.ic_play_arrow);
            } else {
                mBtnMediaPlayer.setImageResource(R.drawable.ic_pause);
                if (getActivity() != null) mMediaPlayer.start();
                updateTimeSeekBar();
            }
        }
        if (id == R.id.btn_volume_change_fm_recycler) {
            if (isAudio) {
                mMediaPlayer.setVolume(0, 0);
                mBtnMediaVolume.setImageResource(R.drawable.ic_volume_off);
                isAudio = false;
            } else {
                mMediaPlayer.setVolume(1, 1);
                mBtnMediaVolume.setImageResource(R.drawable.ic_volume_up_24dp);
                isAudio = true;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        if (handler != null) handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMediaPlayer.seekTo(seekBar.getProgress());
    }

    private void updateTimeSeekBar() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTxtTimePlay.setText(new SimpleDateFormat("mm:ss").format(mMediaPlayer.getCurrentPosition()));
                mSeekBarAudio.setProgress(mMediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    @Override
    public void onDataUpdate(int ex, int exType) {
        setupUi(ex, exType);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        ((MinaActivity) getActivity()).unregisterFragmentRecyclerListener();
        super.onDestroy();
    }
}
