package vn.edu.hust.soict.khacsan.myapp.model.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import vn.edu.hust.soict.khacsan.myapp.model.database.MinaDatabase;

@Table(database = MinaDatabase.class, name = "kotoba")
public class Kotoba extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private int id;
    @Column(name = "lesson_id")
    private int lesson_id;
    @Column(name = "hiragana")
    private String hiragana;
    @Column(name = "kanji")
    private String kanji;
    @Column(name = "roumaji")
    private String roumaji;
    @Column(name = "mean")
    private String mean;
    @Column(name = "mean_unsigned")
    private String mean_unsigned;
    @Column(name = "tag")
    private String tag;
    @Column(name = "favorite")
    private int favorite;
    @Column(name = "kanji_id")
    private String kanji_id;
    @Column(name = "cn_mean")
    private String cn_mean;

    public Kotoba() {
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

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getRoumaji() {
        return roumaji;
    }

    public void setRoumaji(String roumaji) {
        this.roumaji = roumaji;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getMean_unsigned() {
        return mean_unsigned;
    }

    public void setMean_unsigned(String mean_unsigned) {
        this.mean_unsigned = mean_unsigned;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getKanji_id() {
        return kanji_id;
    }

    public void setKanji_id(String kanji_id) {
        this.kanji_id = kanji_id;
    }

    public String getCn_mean() {
        return cn_mean;
    }

    public void setCn_mean(String cn_mean) {
        this.cn_mean = cn_mean;
    }
}
