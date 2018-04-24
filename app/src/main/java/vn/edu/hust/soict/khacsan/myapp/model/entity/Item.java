package vn.edu.hust.soict.khacsan.myapp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Item implements MultiItemEntity {
    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    private String mText;
    private int mItemType;

    private Item(){

    }

    public static Item newInstanceTitle(String text) {
        Item item = new Item();
        item.setmItemType(TITLE);
        item.setmText(text);
        return item;
    }

    public static Item newInstanceContent(String text) {
        Item item = new Item();
        item.setmItemType(CONTENT);
        item.setmText(text);
        return item;
    }


    private void setmItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }
}
