package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.GetAudioFile;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;
import vn.edu.hust.soict.khacsan.myapp.model.utils.NetworkUtil;

import static vn.edu.hust.soict.khacsan.myapp.view.fragment.FragmentAlphabet.URL_GET_AUDIO;

public class FragmentContainerFlip extends Fragment implements ClickFlipListener,
        TextToSpeech.OnInitListener, MediaPlayer.OnCompletionListener,
        TextToSpeech.OnUtteranceCompletedListener, GetAudioFile.AudioListener{
    private Kana kana;
    private CardFlipBackFragment mBackFragment;
    private CardFlipFrontFragment mFrontFragment;
    private int mTypeTable,mPage;
    private ClickFlipListener listener;

    private MediaPlayer mediaPlayer;
    private TextToSpeech tts;
    private Activity activity;
    private GetAudioFile mAudioFile;
    private String nameAudio;
    private boolean mIsBack = false;

    public static FragmentContainerFlip newInstance(Kana kana,int typeTable,int page,ClickFlipListener listener) {
        Bundle args = new Bundle();
        args.putParcelable("KANA",kana);
        args.putInt("TYPETABLE",typeTable);
        args.putInt("PAGE",page);
        FragmentContainerFlip fragment = new FragmentContainerFlip();
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.card_flip_container, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            kana = bundle.getParcelable("KANA");
            mTypeTable = bundle.getInt("TYPETABLE");
            mPage = bundle.getInt("PAGE");
        } else return view;
        if(mTypeTable == 1)
            mFrontFragment =  CardFlipFrontFragment.newInstance(kana.getHira(),kana.getId()+"/"+mPage,this);
        else  mFrontFragment = CardFlipFrontFragment.newInstance(kana.getKata(),kana.getId()+"/"+mPage,this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);

        mAudioFile = new GetAudioFile(this);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container_card_flip,mFrontFragment)
                .addToBackStack(null)
                .commit();
        return view;
    }

    public void setListener(ClickFlipListener listener) {
        this.listener = listener;
    }


    @Override
    public void onFlip(boolean isBack, boolean isRight) {
        mIsBack = isBack;
        //listener.onFlip(isBack,isRight);
        if(isBack){
            if(mFrontFragment == null){
                if(mTypeTable == 1)mFrontFragment = CardFlipFrontFragment.newInstance(kana.getHira(),kana.getId()+"/"+mPage,this);
                else mFrontFragment = CardFlipFrontFragment.newInstance(kana.getKata(),kana.getId()+"/"+mPage,this);
            }
            getChildFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.card_flip_right_in,
                            R.anim.card_flip_right_out,
                            R.anim.card_flip_left_in,
                            R.anim.card_flip_left_out)
                    .replace(R.id.container_card_flip, mFrontFragment)
                    .addToBackStack(null)
                    .commit();
        }else {
            if(!(kana.getHira_full() == null && kana.getExample() == null)){

                if (mBackFragment == null) {
                    mBackFragment = CardFlipBackFragment.newInstance(kana, kana.getId() + "/" + mPage, mTypeTable, this);
                }
                getChildFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.card_flip_right_in,
                                R.anim.card_flip_right_out,
                                R.anim.card_flip_left_in,
                                R.anim.card_flip_left_out)
                        .replace(R.id.container_card_flip, mBackFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public void onPlayAudio(boolean isBack) {
        mIsBack = isBack;
        if (NetworkUtil.getConnectivityStatus(activity)) {
            if (mTypeTable == 1){
                if(nameAudio != null){
                    playAudio(nameAudio);
                } else mAudioFile.getFile(kana.getHira());
            } else{

                if(nameAudio != null){
                    playAudio(nameAudio);
                } else mAudioFile.getFile(kana.getKata());
            }
        } else {
            if (mTypeTable == 1) speakOut(kana.getHira());
            else speakOut(kana.getKata());
        }
    }

    private void speakOut(String text) {
        if (!tts.isSpeaking() && text != null && !text.isEmpty()) {
            HashMap<String, String> myHashAlarm = new HashMap<>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(getContext(), this);
        activity = getActivity();
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.JAPANESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toasty.error(activity, "Error: This Language is not supported!", Toast.LENGTH_SHORT, true).show();
            } else tts.setOnUtteranceCompletedListener(this);

        } else {
            Toasty.error(activity, "Error: Initilization Failed!", Toast.LENGTH_SHORT, true).show();
        }
    }




    @Override
    public void onCompletion(MediaPlayer mp) {
        invisibleBtnAudio();
    }

    private void invisibleBtnAudio(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mIsBack) {
                    View view = mBackFragment.getView();
                    if(view != null) view.findViewById(R.id.btn_audio_play_back).setVisibility(View.GONE);
                }else{
                    View view = mFrontFragment.getView();
                    if(view != null) view.findViewById(R.id.btn_audio_play_front).setVisibility(View.GONE);
                }
            }
        });
    }

    private void playAudio(String namAudio){
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(URL_GET_AUDIO + namAudio);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (getActivity() != null) mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        invisibleBtnAudio();
    }

    @Override
    public void onGetFailure(String msg) {
        invisibleBtnAudio();
        Toasty.error(activity, "Error: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSuccess(String namAudio, String text) {
        playAudio(namAudio);
        nameAudio = namAudio;
    }
}
