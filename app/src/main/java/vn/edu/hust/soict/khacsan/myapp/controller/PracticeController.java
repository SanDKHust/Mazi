package vn.edu.hust.soict.khacsan.myapp.controller;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Question;

public class PracticeController {
    private List<Kana> kanas;
    private int size;
    public PracticeController() {
        kanas = SQLite.select().from(Kana.class).queryList();
        size = kanas.size();
    }

    public Queue<Question> getListQuestion(int num){
        Queue<Question> questionQueue = new LinkedList<>();
        Question question;
        long seed;
        for(int i = 0 ; i < num ; i++){
            seed = System.nanoTime();
            Collections.shuffle(kanas, new Random(seed));
            question = new Question(kanas.get(size / 2));
            question.addAnswer(kanas.get(size - 1).getRomaji());
            question.addAnswer(kanas.get(0).getRomaji());
            question.addAnswer(kanas.get(1 + size / 2).getRomaji());
            question.addAnswer(kanas.get(size / 2).getRomaji());
            questionQueue.add(question);
        }
        return questionQueue;
    }
}
