package vn.edu.hust.soict.khacsan.myapp.model.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MinaDatabase.class, name = "kaiwa")
public class Kaiwa extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private int id;
    @Column(name = "lesson_id")
    private int lesson_id;
    @Column(name = "character")
    private String character;
    @Column(name = "kaiwa")
    private String kaiwa;
    @Column(name = "vi_mean")
    private String vi_mean;
    @Column(name = "c_roumaji")
    private String c_roumaji;
    @Column(name = "j_roumaji")
    private String j_roumaji;

    public Kaiwa() {
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

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getKaiwa() {
        return kaiwa;
    }

    public void setKaiwa(String kaiwa) {
        this.kaiwa = kaiwa;
    }

    public String getVi_mean() {
        return vi_mean;
    }

    public void setVi_mean(String vi_mean) {
        this.vi_mean = vi_mean;
    }

    public String getC_roumaji() {
        return c_roumaji;
    }

    public void setC_roumaji(String c_roumaji) {
        this.c_roumaji = c_roumaji;
    }

    public String getJ_roumaji() {
        return j_roumaji;
    }

    public void setJ_roumaji(String j_roumaji) {
        this.j_roumaji = j_roumaji;
    }
}
