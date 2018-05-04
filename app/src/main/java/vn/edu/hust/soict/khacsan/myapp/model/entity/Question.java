package vn.edu.hust.soict.khacsan.myapp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;

public class Question implements Parcelable {
    private Kana kana;
    private List<String> answer;
    private int soLanTLSai;

    public Question(Kana kana) {
        this.kana = kana;
        answer = new ArrayList<>();
        soLanTLSai = 0;
    }

    protected Question(Parcel in) {
        kana = in.readParcelable(Kana.class.getClassLoader());
        answer = in.createStringArrayList();
        soLanTLSai = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public void addAnswer(String ans){
        answer.add(ans);
    }

    public Kana getKana() {
        return kana;
    }

    public void setKana(Kana kana) {
        this.kana = kana;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answerIncorrect) {
        this.answer = answerIncorrect;
    }

    public int getSoLanTLSai() {
        return soLanTLSai;
    }

    public void setSoLanTLSai(int soLanTLSai) {
        this.soLanTLSai = soLanTLSai;
    }

    public void increaseSoLanTLSai(int n){
        this.soLanTLSai += n;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Question)
            return this.kana.getId() == ((Question) obj).getKana().getId();
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(kana, flags);
        dest.writeStringList(answer);
        dest.writeInt(soLanTLSai);
    }
}
