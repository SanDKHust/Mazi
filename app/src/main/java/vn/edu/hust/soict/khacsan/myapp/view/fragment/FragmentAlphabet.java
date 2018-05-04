package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.GetAudioFile;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemListKana;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;
import vn.edu.hust.soict.khacsan.myapp.model.utils.NetworkUtil;
import vn.edu.hust.soict.khacsan.myapp.view.activity.AlphabetTableActivity;
import vn.edu.hust.soict.khacsan.myapp.view.activity.FlipItemKanaActivity;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.KanaAdapter;

import static vn.edu.hust.soict.khacsan.myapp.view.activity.AlphabetTableActivity.LIST_ITEM_KANA;
import static vn.edu.hust.soict.khacsan.myapp.view.activity.AlphabetTableActivity.TITLE_FRAGMENT;
import static vn.edu.hust.soict.khacsan.myapp.view.activity.AlphabetTableActivity.TITLE_HIRAGANA;

public class FragmentAlphabet extends Fragment implements TextToSpeech.OnInitListener, MediaPlayer.OnCompletionListener, KanaAdapter.AudioClickListener,
        TextToSpeech.OnUtteranceCompletedListener, GetAudioFile.AudioListener {
    public static final String URL_GET_AUDIO = "http://dws2.voicetext.jp/ASLCLCLVVS/JMEJSYGDCHMSMHSRKPJL/";
    public static final String KANA_SELECT_FLIP = "KANA";
    public static final String TYPE_TABLE = "TYPE_TABLE";

    private String title;
    private ArrayList<ItemListKana> mItemList;
    private KanaAdapter adapter;
    private Kana kanaSelect;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    private GetAudioFile mAudioFile;
    private MediaPlayer mediaPlayer;
    private TextToSpeech tts;
    private HashMap<String,String> mMapNameAudio;
    private Activity activity;

    public FragmentAlphabet() {
    }

    public static FragmentAlphabet newInstance(ArrayList<ItemListKana> itemListKanas,String title) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_ITEM_KANA,itemListKanas);
        args.putString(TITLE_FRAGMENT,title);
        FragmentAlphabet fragment = new FragmentAlphabet();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kana, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_alphabet);

        mMapNameAudio = new HashMap<>();

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(LIST_ITEM_KANA) && bundle.containsKey(TITLE_FRAGMENT)){
            mItemList = bundle.getParcelableArrayList(LIST_ITEM_KANA);
            this.title = bundle.getString(TITLE_FRAGMENT);
        }

        mAudioFile = new GetAudioFile(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);

        mGridLayoutManager = new GridLayoutManager(activity, 1);
        if (title.equals(TITLE_HIRAGANA)) {
            adapter = new KanaAdapter(mItemList, 1, this);
        } else adapter = new KanaAdapter(mItemList, 2, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void speakOut(String text) {
        if (!tts.isSpeaking() && text != null && !text.isEmpty()) {
            HashMap<String, String> myHashAlarm = new HashMap<>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onClickViewAudio(View view, Kana kana) {
        if(AlphabetTableActivity.isIsAudio()){
            if(kanaSelect != null && kanaSelect.isClicked()){
                kanaSelect.setClicked(false);
                adapter.notifyDataSetChanged();
            }

            if (kana != null) {
                kana.setClicked(true);
                kanaSelect = kana;
                adapter.notifyDataSetChanged();
                if (NetworkUtil.getConnectivityStatus(activity)) {
                    if (adapter.getType() == 1){
                        String hira = kana.getHira();
                        if(mMapNameAudio.containsKey(hira)){
                            playAudio(mMapNameAudio.get(hira));
                        } else mAudioFile.getFile(hira);
                    } else{
                        String kata = kana.getKata();
                        if(mMapNameAudio.containsKey(kata)){
                            playAudio(mMapNameAudio.get(kata));
                        } else mAudioFile.getFile(kata);
                    }
                } else {
                    if (adapter.getType() == 1) speakOut(kana.getHira());
                    else speakOut(kana.getKata());
                }
            }
        }else {
            Intent intent = new Intent(activity, FlipItemKanaActivity.class);
            intent.putExtra(KANA_SELECT_FLIP,kana);
            intent.putExtra(TYPE_TABLE,adapter.getType());
            startActivity(intent);
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
    public void onUtteranceCompleted(String utteranceId) {
        if (kanaSelect != null) {
            activity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   kanaSelect.setClicked(false);
                   adapter.notifyDataSetChanged();
               }
           });
        }
    }

    @Override
    public void onGetFailure(String msg) {
        Toasty.error(activity, "Error: " + msg, Toast.LENGTH_SHORT).show();
        if (kanaSelect != null) {
            kanaSelect.setClicked(false);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetSuccess(String namAudio,String text) {
       playAudio(namAudio);
       mMapNameAudio.put(text,namAudio);
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
    public void onCompletion(MediaPlayer mp) {
        if (kanaSelect != null) {
            kanaSelect.setClicked(false);
            adapter.notifyDataSetChanged();
        }
    }

}
