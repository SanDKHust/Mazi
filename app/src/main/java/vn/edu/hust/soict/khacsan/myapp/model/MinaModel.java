package vn.edu.hust.soict.khacsan.myapp.model;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.model.database.Bunkei;
import vn.edu.hust.soict.khacsan.myapp.model.database.Bunkei_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kaiwa;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kaiwa_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kotoba;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kotoba_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Listen;
import vn.edu.hust.soict.khacsan.myapp.model.database.Listen_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reference;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reference_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reibun;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reibun_Table;

public class MinaModel {
    public List<Kotoba> getKotobaByLessonId(int lesson_id){
        return SQLite.select().from(Kotoba.class).where(Kotoba_Table.lesson_id.is(lesson_id))
                .queryList();
    }
    public List<Kaiwa> getKaiwaByLessonId(int lesson_id){
        return SQLite.select().from(Kaiwa.class).where(Kaiwa_Table.lesson_id.is(lesson_id)).queryList();
    }
    public List<Grammar> getGrammarByLessonId(int lesson_id){
        return SQLite.select().from(Grammar.class).where(Grammar_Table.lesson_id.is(lesson_id)).queryList();
    }
    public List<Mondai> getMondaiByLessonId(int lesson_id){
        return SQLite.select().from(Mondai.class).where(Mondai_Table.lesson_id.is(lesson_id)).queryList();
    }
    public List<Bunkei> getBunkeiByLessonId(int lesson_id){
        return SQLite.select().from(Bunkei.class).where(Bunkei_Table.lesson_id.is(lesson_id)).queryList();
    }
    public List<Reibun> getReibunByLessonId(int lesson_id){
        return SQLite.select().from(Reibun.class).where(Reibun_Table.lesson_id.is(lesson_id)).queryList();
    }
    public List<Reference> getReferenceByLessonId(int lesson_id){
        return SQLite.select().from(Reference.class).where(Reference_Table.lesson_id.is(lesson_id)).queryList();
    }

    public Listen getAudioByLessonIdAndType(int lesson_id, String type){
        return SQLite.select().from(Listen.class).where(Listen_Table.lesson.is(lesson_id)).and(Listen_Table.type.is(type)).querySingle();
    }

    public Mondai getMondaiByLessonIdAndType(int lesson_id,int type){
        return SQLite.select().from(Mondai.class).where(Mondai_Table.lesson_id.is(lesson_id)).and(Mondai_Table.type.is(type)).querySingle();
    }
}
