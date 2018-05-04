package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.database.Reibun;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemReibun;

public class ReibunAdapter extends BaseQuickAdapter<ItemReibun, BaseViewHolder> {
    private int index;

    public ReibunAdapter(int layoutResId, @Nullable List<ItemReibun> data) {
        super(layoutResId, data);
        index = 1;
    }


    @Override
    protected void convert(BaseViewHolder helper, ItemReibun item) {
        Reibun reibun0 = item.getmReibunType0(), reibun1 = item.getmReibunTyppe1();
        helper.setText(R.id.text_index_item_reibun, String.valueOf(index++))
                .setText(R.id.text_reibun_type0, reibun0.getReibun())
                .setText(R.id.text_roumaji_item_reibun_type0, reibun0.getRoumaji())
                .setText(R.id.text_vi_mean_item_reibun_type0, reibun0.getVi_mean())
                .setText(R.id.text_reibun_type1, reibun1.getReibun())
                .setText(R.id.text_roumaji_item_reibun_type1, reibun1.getRoumaji())
                .setText(R.id.text_vi_mean_item_reibun_type1, reibun1.getVi_mean());
    }

    @Override
    public void setNewData(@Nullable List<ItemReibun> data) {
        super.setNewData(data);
        index = 1;
    }
}
