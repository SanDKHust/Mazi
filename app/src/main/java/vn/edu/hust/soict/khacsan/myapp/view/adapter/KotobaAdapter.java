package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kotoba;

public class KotobaAdapter extends BaseQuickAdapter<Kotoba,BaseViewHolder> {
    private int index;
    public KotobaAdapter(int layoutResId, @Nullable List<Kotoba> data) {
        super(layoutResId, data);
        index = 1;
    }

    @Override
    protected void convert(BaseViewHolder helper, Kotoba item) {
        helper.setText(R.id.text_index_item_Extype,String.valueOf(index++))
                .setText(R.id.text_1_item_Extype,item.getHiragana())
                .setText(R.id.text_2_item_Extype,item.getKanji() == null ? "-" : item.getKanji()+" : ")
                .setText(R.id.text_3_item_Extype,item.getCn_mean())
                .setText(R.id.text_4_item_Extype,item.getMean());
    }

    @Override
    public void setNewData(@Nullable List<Kotoba> data) {
        super.setNewData(data);
        index = 1;
    }
}
