package vn.edu.hust.soict.khacsan.myapp.model.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MinaDatabase.class, name = "reibun")
public class Reibun extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private int id;
    @Column(name = "lesson_id")
    private int lesson_id;
    @Column(name = "type")
    private int type;
    @Column(name = "reibun")
    private String reibun;
    @Column(name = "vi_mean")
    private String vi_mean;
    @Column(name = "roumaji")
    private String roumaji;

    public Reibun() {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReibun() {
        return reibun;
    }

    public void setReibun(String reibun) {
        this.reibun = reibun;
    }

    public String getVi_mean() {
        return vi_mean;
    }

    public void setVi_mean(String vi_mean) {
        this.vi_mean = vi_mean;
    }

    public String getRoumaji() {
        return roumaji;
    }

    public void setRoumaji(String roumaji) {
        this.roumaji = roumaji;
    }
}
