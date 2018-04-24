package vn.edu.hust.soict.khacsan.myapp.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import vn.edu.hust.soict.khacsan.myapp.model.database.MinaDatabase;

@Table(database = MinaDatabase.class, name = "kana")
public class Kana extends BaseModel implements Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private int id;
    @Column(name = "romaji")
    private String romaji;
    @Column(name = "hira")
    private String hira;
    @Column(name = "kata")
    private String kata;
    @Column(name = "groupe")
    private String groupe;
    @Column(name = "hira_full")
    private String hira_full;
    @Column(name = "kata_full")
    private String kata_full;
    @Column(name = "example")
    private String example;
    @Column(name = "favorite")
    private int favorite;
    @ColumnIgnore
    private boolean clicked;

    public Kana() {
    }

    public Kana(int id, String romaji, String hira, String kata, String groupe, String hira_full, String kata_full, String example, int favorite) {
        this.id = id;
        this.romaji = romaji;
        this.hira = hira;
        this.kata = kata;
        this.groupe = groupe;
        this.hira_full = hira_full;
        this.kata_full = kata_full;
        this.example = example;
        this.favorite = favorite;
        this.clicked = false;
    }

    protected Kana(Parcel in) {
        id = in.readInt();
        romaji = in.readString();
        hira = in.readString();
        kata = in.readString();
        groupe = in.readString();
        hira_full = in.readString();
        kata_full = in.readString();
        example = in.readString();
        favorite = in.readInt();
        clicked = in.readByte() != 0;
    }

    public static final Creator<Kana> CREATOR = new Creator<Kana>() {
        @Override
        public Kana createFromParcel(Parcel in) {
            return new Kana(in);
        }

        @Override
        public Kana[] newArray(int size) {
            return new Kana[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getHira() {
        return hira;
    }

    public void setHira(String hira) {
        this.hira = hira;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getHira_full() {
        return hira_full;
    }

    public void setHira_full(String hira_full) {
        this.hira_full = hira_full;
    }

    public String getKata_full() {
        return kata_full;
    }

    public void setKata_full(String kata_full) {
        this.kata_full = kata_full;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(romaji);
        dest.writeString(hira);
        dest.writeString(kata);
        dest.writeString(groupe);
        dest.writeString(hira_full);
        dest.writeString(kata_full);
        dest.writeString(example);
        dest.writeInt(favorite);
        dest.writeByte((byte) (clicked ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Kana){
            if(((Kana)obj).getId() == this.id) return true;
        }
        return false;
    }
}