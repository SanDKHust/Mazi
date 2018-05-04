package vn.edu.hust.soict.khacsan.myapp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;

public class ItemListKana implements MultiItemEntity, Parcelable {
    public static final int THREE_ITEM = 3;
    public static final int FIVE_ITEM = 5;
    private int mItemType = FIVE_ITEM;
    ArrayList<Kana> kanas;

    protected ItemListKana(Parcel in) {
        mItemType = in.readInt();
    }
    protected ItemListKana() {

    }

    public static final Creator<ItemListKana> CREATOR = new Creator<ItemListKana>() {
        @Override
        public ItemListKana createFromParcel(Parcel in) {
            return new ItemListKana(in);
        }

        @Override
        public ItemListKana[] newArray(int size) {
            return new ItemListKana[size];
        }
    };

    public static ItemListKana newInstance(ArrayList<Kana> kanas, int type) {
        ItemListKana itemListKana = new ItemListKana();
        itemListKana.setKanas(kanas);
        if(type == FIVE_ITEM)itemListKana.setmItemType(FIVE_ITEM);
        else  if(type == THREE_ITEM)itemListKana.setmItemType(THREE_ITEM);
        return itemListKana;
    }

    public void setmItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    public ArrayList<Kana> getKanas() {
        return kanas;
    }

    public void setKanas(ArrayList<Kana> kanas) {
        this.kanas = kanas;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mItemType);
    }
}
