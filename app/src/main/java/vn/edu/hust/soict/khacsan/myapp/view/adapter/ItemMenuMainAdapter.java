package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemMenuMain;

public class ItemMenuMainAdapter extends BaseMultiItemQuickAdapter<ItemMenuMain,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ItemMenuMainAdapter(List<ItemMenuMain> data) {
        super(data);
        addItemType(ItemMenuMain.TITLE, R.layout.item_menu_main_title);
        addItemType(ItemMenuMain.CONTENT, R.layout.item_menu_main_content);
    }
    @Override
    protected void convert(BaseViewHolder helper, ItemMenuMain item) {
        int type = helper.getItemViewType();
        if(type == ItemMenuMain.TITLE) helper.setText(R.id.text_item_title,item.getmText());
        if(type == ItemMenuMain.CONTENT) helper.setText(R.id.text_item_content,item.getmText());
    }
}
