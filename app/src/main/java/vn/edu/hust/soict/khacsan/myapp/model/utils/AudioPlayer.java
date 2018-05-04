package vn.edu.hust.soict.khacsan.myapp.model.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.text.SimpleDateFormat;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.MinaController;

public class AudioPlayer implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private ImageButton mBtnMediaPlayer, mBtnMediaVolume;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private SeekBar mSeekBarAudio;
    private TextView mTxtTimePlay, mTxtTotalTimeAudio;
    private Handler handler;
    private Context context;
    private boolean isAudio;

    public AudioPlayer(Context context,boolean isAudio, ImageButton mBtnMediaPlayer, ImageButton mBtnMediaVolume, AVLoadingIndicatorView avLoadingIndicatorView, SeekBar mSeekBarAudio, TextView mTxtTimePlay, TextView mTxtTotalTimeAudio) {
        this.mBtnMediaPlayer = mBtnMediaPlayer;
        this.mBtnMediaVolume = mBtnMediaVolume;
        this.avLoadingIndicatorView = avLoadingIndicatorView;
        this.mSeekBarAudio = mSeekBarAudio;
        this.mTxtTimePlay = mTxtTimePlay;
        this.mTxtTotalTimeAudio = mTxtTotalTimeAudio;
        this.context = context;
        this.isAudio = isAudio;

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this);

        handler = new Handler();

        setOnclickListener();
    }

    private void setOnclickListener() {
        mBtnMediaPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    pause();
                } else {
                    mBtnMediaPlayer.setImageResource(R.drawable.ic_pause);
                    mMediaPlayer.start();
                    updateTimeSeekBar();
                }
            }
        });

        mBtnMediaVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public void setVolume(float leftVolume, float rightVolume){
        mMediaPlayer.setVolume(leftVolume, rightVolume);
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    public void reset(String link, final int currentPos){
        try {
            mBtnMediaPlayer.setImageResource(R.drawable.ic_play_arrow);
            avLoadingIndicatorView.smoothToShow();
            mMediaPlayer.reset();
            mSeekBarAudio.setProgress(0);
            mTxtTotalTimeAudio.setText(new SimpleDateFormat("mm:ss").format(0));
            mTxtTimePlay.setText(new SimpleDateFormat("mm:ss").format(0));
            mMediaPlayer.setDataSource(link);
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


    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public void pause(){
        if(mMediaPlayer.isPlaying()) mMediaPlayer.pause();
        mBtnMediaPlayer.setImageResource(R.drawable.ic_play_arrow);
    }

    public void release(){
        pause();
        mMediaPlayer.release();
        if (handler != null) handler.removeCallbacksAndMessages(null);
    }

    public void seekTo(int sec){
        mMediaPlayer.seekTo(sec);
    }

}
