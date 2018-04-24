package vn.edu.hust.soict.khacsan.myapp.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.controller.PracticeController;
import vn.edu.hust.soict.khacsan.myapp.model.GetAudioFile;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Kana;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Question;
import vn.edu.hust.soict.khacsan.myapp.model.utils.NetworkUtil;

import static vn.edu.hust.soict.khacsan.myapp.view.Fragment.FragmentAlphabet.URL_GET_AUDIO;
import static vn.edu.hust.soict.khacsan.myapp.view.activity.ActivityAlphabetTable.TYPE_KANA;

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener,TextToSpeech.OnInitListener, MediaPlayer.OnCompletionListener,
        TextToSpeech.OnUtteranceCompletedListener, GetAudioFile.AudioListener {
    public static final String LIST_QUESTION = "LIST_QUESTION";
    public static final String INTENT_QUESTION = "INTENT_QUESTION";
    private RelativeLayout mRootLayout;
    private PopupWindow mPopupWindow;
    private PracticeController controller;
    private Button mBtnAnswerA, mBtnAnswerB, mBtnAnswerC, mBtnAnswerD,mBtnCheck;
    private ImageButton mBtnClose, mBtnAudio, mBtnAudioPlay;
    private float mY, offsetY;
    private int indexSelect = -1;
    private View mViewDialogAnswer;
    private Context mContext;
    private String textSelect = "";
    private Queue<Question> mQuestionQueue;
    private int DefaultButtonColor;
    private ProgressBar mProgressBar;
    private TextView mTextProcess,mTextQuestion,mTextPoupDialog,mTextDapAnDialog;
    private ArrayList<Question> mQuestions;
    private int typeKana = 1;

    private MediaPlayer mediaPlayer;
    private TextToSpeech tts;
    private GetAudioFile mAudioFile;
    private String nameAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        mContext = getApplicationContext();
        initView();
        Intent intent = getIntent();
        if(intent != null){
            typeKana = intent.getIntExtra(TYPE_KANA,1);
        }

        tts = new TextToSpeech(mContext, this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);

        mAudioFile = new GetAudioFile(this);

        controller = new PracticeController();
        mQuestionQueue = controller.getListQuestion(10);
        mQuestions = new ArrayList<>();

        upDateQuestion();
    }

    private void upDateQuestion() {
        if(!mQuestionQueue.isEmpty()){
            Question question = mQuestionQueue.peek();
            List<String> answers = question.getAnswer();
            long seed = System.nanoTime();
            Collections.shuffle(answers, new Random(seed));
            try {
                mBtnAnswerA.setText(answers.get(0));
                mBtnAnswerB.setText(answers.get(1));
                mBtnAnswerC.setText(answers.get(2));
                mBtnAnswerD.setText(answers.get(3));
            }catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
            }
            if(mQuestions.contains(question)){
                mQuestions.get(mQuestions.indexOf(question)).increaseSoLanTLSai(1);
            }else {
                mQuestions.add(question);
            }
            if(typeKana == 1) mTextQuestion.setText("Cách phát âm của "+question.getKana().getHira()+" là?");
            else mTextQuestion.setText("Cách phát âm của "+question.getKana().getKata()+" là?");
        }else {
            Intent intent = new Intent(this,ResultPracticeActivity.class);
            intent.putExtra(TYPE_KANA,typeKana);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(LIST_QUESTION,mQuestions);
            intent.putExtra(INTENT_QUESTION,bundle);
            startActivity(intent);
        }
    }

    private void initView() {
        mTextQuestion = findViewById(R.id.text_question);
        mTextProcess = findViewById(R.id.text_process);
        mRootLayout = findViewById(R.id.rl_practice);
        mBtnAnswerA = findViewById(R.id.btn_answer_a);
        mBtnAnswerB = findViewById(R.id.btn_answer_b);
        mBtnAnswerC = findViewById(R.id.btn_answer_c);
        mBtnAnswerD = findViewById(R.id.btn_answer_d);
        mBtnAudio = findViewById(R.id.btn_audio_practice);
        mBtnAudioPlay = findViewById(R.id.btn_audio_play_practice);
        mBtnClose = findViewById(R.id.btn_practice_close);
        mBtnCheck = findViewById(R.id.btn_check_and_continue);
        mProgressBar = findViewById(R.id.progress_bar_practice);

        mProgressBar.setMax(10);

        mBtnClose.setOnClickListener(this);
        mBtnAnswerA.setOnClickListener(this);
        mBtnAnswerB.setOnClickListener(this);
        mBtnAnswerC.setOnClickListener(this);
        mBtnAnswerD.setOnClickListener(this);
        mBtnAudio.setOnClickListener(this);
        mBtnCheck.setOnClickListener(this);

        DefaultButtonColor = mBtnAnswerA.getTextColors().getDefaultColor();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        if (inflater != null) {
            mViewDialogAnswer = inflater.inflate(R.layout.popup_answer, null);
            mPopupWindow = new PopupWindow(mViewDialogAnswer, LayoutParams.WRAP_CONTENT, 300);
            mTextPoupDialog = mViewDialogAnswer.findViewById(R.id.text_popup_answer);
            mTextDapAnDialog = mViewDialogAnswer.findViewById(R.id.text_popup_show_answer);
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }

            mViewDialogAnswer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            offsetY = event.getRawY() - mY;
                            mPopupWindow.update(0, (int) offsetY, -1, -1, true);
                    }
                    return true;
                }
            });

            mPopupWindow.setAnimationStyle(R.style.popup_window_animation);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_check_and_continue: {
                if (mPopupWindow.isShowing()) {
                    mBtnCheck.setText("Kiểm tra");
                    updateUi(indexSelect);
                    mPopupWindow.dismiss();
                    upDateQuestion();
                    nameAudio = null;
                    indexSelect = -1;
                } else {
                    if(indexSelect == -1){
                        Toasty.error(mContext,"Vui lòng chọn đáp án!", Toast.LENGTH_SHORT).show();
                    }else{
                        Question question = mQuestionQueue.peek();
                        if(question.getKana().getRomaji().trim().equals(textSelect)){
                            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                mViewDialogAnswer.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.background_correct));
                            } else {
                                mViewDialogAnswer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_correct));
                            }
                            mTextPoupDialog.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                            mTextDapAnDialog.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                            mTextPoupDialog.setText("Bạn trả lời đúng");
                            mTextDapAnDialog.setText("Đáp án: "+question.getKana().getRomaji());
                            mQuestionQueue.poll();
                            int process = mProgressBar.getProgress() + 1;
                            mProgressBar.setProgress(process);
                            mTextProcess.setText(process+"/10");
                        }else {
                            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                mViewDialogAnswer.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.background_incorrect));
                            } else {
                                mViewDialogAnswer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_incorrect));
                            }
                            mTextPoupDialog.setTextColor(this.getResources().getColor(android.R.color.holo_red_light));
                            mTextDapAnDialog.setTextColor(this.getResources().getColor(android.R.color.holo_red_light));
                            mTextPoupDialog.setText("Bạn trả lời sai");
                            mTextDapAnDialog.setText("Đáp án: "+question.getKana().getRomaji());
                            mQuestionQueue.poll();
                            mQuestionQueue.add(question);
                        }
                        mBtnCheck.setText("Tiếp tục");
                        mPopupWindow.showAtLocation(mRootLayout, Gravity.LEFT,0,0);
                    }
                }
                break;
            }
            case R.id.btn_answer_a: {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerA.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                } else {
                    mBtnAnswerA.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                }
                mBtnAnswerA.setTextColor(this.getResources().getColor(R.color.colorWhite));
                updateUi(indexSelect);
                textSelect = mBtnAnswerA.getText().toString().trim();
                indexSelect = 1;
                break;
            }
            case R.id.btn_answer_b: {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerB.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                } else {
                    mBtnAnswerB.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                }
                mBtnAnswerB.setTextColor(this.getResources().getColor(R.color.colorWhite));
                updateUi(indexSelect);
                textSelect = mBtnAnswerB.getText().toString().trim();
                indexSelect = 2;
                break;
            }
            case R.id.btn_answer_c: {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerC.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                } else {
                    mBtnAnswerC.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                }
                mBtnAnswerC.setTextColor(this.getResources().getColor(R.color.colorWhite));
                updateUi(indexSelect);
                textSelect = mBtnAnswerC.getText().toString().trim();
                indexSelect = 3;
                break;
            }
            case R.id.btn_answer_d: {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerD.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                } else {
                    mBtnAnswerD.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_answer_selected));
                }
                mBtnAnswerD.setTextColor(this.getResources().getColor(R.color.colorWhite));
                updateUi(indexSelect);
                textSelect = mBtnAnswerD.getText().toString().trim();
                indexSelect = 4;
                break;
            }
            case R.id.btn_audio_practice:{
                mBtnAudioPlay.setVisibility(View.VISIBLE);
                if (NetworkUtil.getConnectivityStatus(mContext)) {
                    if (typeKana == 1){
                        if(nameAudio != null){
                            playAudio(nameAudio);
                        } else mAudioFile.getFile(mQuestionQueue.peek().getKana().getHira());
                    } else{
                        if(nameAudio != null){
                            playAudio(nameAudio);
                        } else mAudioFile.getFile(mQuestionQueue.peek().getKana().getKata());
                    }
                } else {
                    if (typeKana == 1) speakOut(mQuestionQueue.peek().getKana().getHira());
                    else speakOut(mQuestionQueue.peek().getKana().getKata());
                }
                break;
            }
            case R.id.btn_practice_close:{
                finish();
                break;
            }
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

    private void updateUi(int pos) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (pos != -1) {
            if (pos == 1) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerA.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                } else {
                    mBtnAnswerA.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                }
                mBtnAnswerA.setTextColor(DefaultButtonColor);
            } else if (pos == 2) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerB.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                } else {
                    mBtnAnswerB.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                }
                mBtnAnswerB.setTextColor(DefaultButtonColor);

            } else if (pos == 3) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerC.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                } else {
                    mBtnAnswerC.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                }
                mBtnAnswerC.setTextColor(DefaultButtonColor);

            } else if (pos == 4) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnAnswerD.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                } else {
                    mBtnAnswerD.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_item_kana));
                }
                mBtnAnswerD.setTextColor(DefaultButtonColor);
            }
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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.JAPANESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toasty.error(mContext, "Error: This Language is not supported!", Toast.LENGTH_SHORT, true).show();
            } else tts.setOnUtteranceCompletedListener(this);

        } else {
            Toasty.error(mContext, "Error: Initilization Failed!", Toast.LENGTH_SHORT, true).show();
        }
    }




    @Override
    public void onCompletion(MediaPlayer mp) {
        invisibleBtnAudio();
    }

    private void invisibleBtnAudio(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnAudioPlay.setVisibility(View.GONE);
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
                    if (getApplicationContext() != null) mp.start();
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
        Toasty.error(mContext, "Error: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSuccess(String namAudio, String text) {
        playAudio(namAudio);
        nameAudio = namAudio;
    }

}
