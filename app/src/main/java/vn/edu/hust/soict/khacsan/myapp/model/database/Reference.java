package vn.edu.hust.soict.khacsan.myapp.model.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MinaDatabase.class, name = "reference")
public class Reference extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private int id;
    @Column(name = "lesson_id")
    private int lesson_id;
    @Column(name = "japanese")
    private String japanese;
    @Column(name = "roumaji")
    private String roumaji;
    @Column(name = "vietnamese")
    private String vietnamese;
    @Column(name = "note")
    private String note;

    public Reference() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getJapanese() {
        return japanese;
    }

    public void setJapanese(String japanese) {
        this.japanese = japanese;
    }

    public String getRoumaji() {
        return roumaji;
    }

    public void setRoumaji(String roumaji) {
        this.roumaji = roumaji;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
