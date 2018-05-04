package vn.edu.hust.soict.khacsan.myapp.controller;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.model.MinaModel;
import vn.edu.hust.soict.khacsan.myapp.model.database.Bunkei;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kaiwa;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kotoba;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai;
import vn.edu.hust.soict.khacsan.myapp.model.database.Mondai_Table;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reference;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reibun;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemReibun;

public class MinaController {
    private MinaModel minaModel;

    public MinaController() {
        minaModel = new MinaModel();
    }

    public List<Kotoba> getKotobaByLessonId(int lesson_id){
        return minaModel.getKotobaByLessonId(lesson_id);
    }

    public List<Kaiwa> getKaiwaByLessonId(int lesson_id){
        return minaModel.getKaiwaByLessonId(lesson_id);
    }

    public List<Grammar> getGrammarByLessonId(int lesson_id){
        return minaModel.getGrammarByLessonId(lesson_id);
    }

    public List<Mondai> getMondaiByLessonId(int lesson_id){
        return minaModel.getMondaiByLessonId(lesson_id);
    }

    public List<Bunkei> getBunkeiByLessonId(int lesson_id) {
        return minaModel.getBunkeiByLessonId(lesson_id);
    }

    public List<ItemReibun> getItemReibunByLessonId(int lesson_id) {
        List<ItemReibun> itemReibuns = new ArrayList<>();
        List<Reibun> reibuns = minaModel.getReibunByLessonId(lesson_id);
        try {
            for (int i = 0; i < reibuns.size(); i+= 2) {
                Reibun reibunType0 = reibuns.get(i);
                Reibun reibunType1 = reibuns.get(i + 1);
                if(reibunType0.getType() == 0 && reibunType1. getType() == 1){
                    itemReibuns.add(new ItemReibun(reibunType0,reibunType1));
                }else if(reibunType0.getType() == 1 && reibunType1. getType() == 0){
                    itemReibuns.add(new ItemReibun(reibunType1,reibunType0));
                }
            }
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemReibuns;
    }

    public List<Reference> getReferenceByLessonId(int lesson_id) {
        return minaModel.getReferenceByLessonId(lesson_id);
    }

    public String getLinkAudio(int lesson_id,String type){
        return minaModel.getAudioByLessonIdAndType(lesson_id,type).getLink();
    }

    public Mondai getMondaiByLessonIdAndType(int lesson_id,int type){
        return minaModel.getMondaiByLessonIdAndType(lesson_id, type);
    }
}
