package vn.edu.hust.soict.khacsan.myapp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ItemMenuMain implements MultiItemEntity {
    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    private String mText;
    private int mItemType;

    private ItemMenuMain(){

    }

    public static ItemMenuMain newInstanceTitle(String text) {
        ItemMenuMain item = new ItemMenuMain();
        item.setmItemType(TITLE);
        item.setmText(text);
        return item;
    }

    public static ItemMenuMain newInstanceContent(String text) {
        ItemMenuMain item = new ItemMenuMain();
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
