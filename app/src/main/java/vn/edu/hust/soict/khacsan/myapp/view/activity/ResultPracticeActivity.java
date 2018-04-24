package vn.edu.hust.soict.khacsan.myapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Question;
import vn.edu.hust.soict.khacsan.myapp.model.entity.SectionQuestion;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.QuestionAdapter;

import static vn.edu.hust.soict.khacsan.myapp.view.activity.ActivityAlphabetTable.TYPE_KANA;
import static vn.edu.hust.soict.khacsan.myapp.view.activity.PracticeActivity.INTENT_QUESTION;
import static vn.edu.hust.soict.khacsan.myapp.view.activity.PracticeActivity.LIST_QUESTION;

public class ResultPracticeActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private ArrayList<Question> mQuestions;
    private List<SectionQuestion> mSectionQuestions;
    private RecyclerView mRecyclerViewResult;
    private QuestionAdapter adapter;
    private int typeKana;
    private TextView mTextViewStatus,mTextViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_result);
        initView();
        mSectionQuestions = new ArrayList<>();
        Intent intent = getIntent();
        if(intent != null){
            typeKana = intent.getIntExtra(TYPE_KANA,1);
            Bundle bundle = intent.getBundleExtra(INTENT_QUESTION);
            if(bundle != null){
                mQuestions = bundle.getParcelableArrayList(LIST_QUESTION);
                Collections.sort(mQuestions, new Comparator<Question>() {
                    @Override
                    public int compare(Question o1, Question o2) {
                        if(o1.getSoLanTLSai() > o2.getSoLanTLSai())
                            return -1;
                        if(o1.getSoLanTLSai() < o2.getSoLanTLSai())
                            return 1;
                        return  0;
                    }
                });
                setupUI();
            }
        }
    }

    private void setupUI() {
        int n = 0;
        mSectionQuestions.add(new SectionQuestion(true,"Những câu trả lời sai "));
        for(Question q: mQuestions ){
            if(q.getSoLanTLSai() == 0) {
               if( n== 0)
                mSectionQuestions.add(new SectionQuestion(true,"Những câu trả lời đúng "));
                n++;
            }
            mSectionQuestions.add(new SectionQuestion(q));
        }
        adapter = new QuestionAdapter(R.layout.def_section_content,R.layout.def_section_head,mSectionQuestions,typeKana);
        mRecyclerViewResult.setAdapter(adapter);

        if(n >= 10){
            mTextViewStatus.setText("Tuyệt");
        }else if(n == 8 || n == 9){
            mTextViewStatus.setText("Giỏi");
        }else if(n == 6 || n == 7){
            mTextViewStatus.setText("Khá");
        }else if(n == 4 || n == 5){
            mTextViewStatus.setText("Trung bình");
        }else if(n == 2 || n == 3){
            mTextViewStatus.setText("Yếu");
        }else if(n < 2){
            mTextViewStatus.setText("Tệ quá");
        }

        mTextViewDescription.setText("Bạn nhớ được "+n+"/10 từ");
        mToolbar.setTitle("Bạn trả lời đúng được "+n+"0% số câu hỏi");
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_practice_result);
        mRecyclerViewResult = findViewById(R.id.recycler_result);
        mTextViewStatus = findViewById(R.id.text_status_practice_result);
        mTextViewDescription = findViewById(R.id.text_description_practice_result);

        findViewById(R.id.btn_completed_result).setOnClickListener(this);
        findViewById(R.id.btn_continues_result).setOnClickListener(this);

        mRecyclerViewResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewResult.setHasFixedSize(true);

        setSupportActionBar(mToolbar);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_continues_result){
            startActivity(new Intent(this,PracticeActivity.class));
        }else if(id == R.id.btn_completed_result){
            startActivity(new Intent(this,ActivityAlphabetTable.class));
        }
    }
}
