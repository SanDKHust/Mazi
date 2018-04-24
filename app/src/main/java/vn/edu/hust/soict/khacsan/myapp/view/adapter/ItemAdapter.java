package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Item;

public class ItemAdapter extends BaseMultiItemQuickAdapter<Item,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ItemAdapter(List<Item> data) {
        super(data);
        addItemType(Item.TITLE, R.layout.item_title);
        addItemType(Item.CONTENT, R.layout.item_content);
    }
    @Override
    protected void convert(BaseViewHolder helper, Item item) {
        int type = helper.getItemViewType();
        if(type == Item.TITLE) helper.setText(R.id.text_item_title,item.getmText());
        if(type == Item.CONTENT) helper.setText(R.id.text_item_content,item.getmText());
    }
}
