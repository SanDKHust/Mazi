package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.database.Bunkei;

public class BunkeiAdapter extends BaseQuickAdapter<Bunkei,BaseViewHolder> {
    private int index;
    public BunkeiAdapter(int layoutResId, @Nullable List<Bunkei> data) {
        super(layoutResId, data);
        index = 1;
    }

    @Override
    protected void convert(BaseViewHolder helper, Bunkei item) {
        helper.setText(R.id.text_index_item_Extype,String.valueOf(index++))
                .setText(R.id.text_1_item_Extype,item.getBunkei())
                .setText(R.id.text_2_item_Extype,item.getRoumaji())
                .setText(R.id.text_3_item_Extype,"")
                .setText(R.id.text_4_item_Extype,item.getVi_mean());
    }

    @Override
    public void setNewData(@Nullable List<Bunkei> data) {
        super.setNewData(data);
        index = 1;
    }
}
