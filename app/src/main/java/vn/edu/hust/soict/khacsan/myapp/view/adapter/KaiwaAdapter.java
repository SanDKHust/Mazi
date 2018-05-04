package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kaiwa;

public class KaiwaAdapter extends BaseQuickAdapter<Kaiwa,BaseViewHolder> {
    public KaiwaAdapter(int layoutResId, @Nullable List<Kaiwa> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Kaiwa item) {
        if(item.getKaiwa() != null){
            helper.setText(R.id.text_character_item_kaiwa,item.getCharacter())
                    .setText(R.id.text_c_roumaji_item_kaiwa,item.getC_roumaji())
                    .setText(R.id.text_kaiwa_item_kaiwa,item.getKaiwa())
                    .setText(R.id.text_j_roumaji_item_kaiwa,item.getJ_roumaji())
                    .setText(R.id.text_vi_mean_kaiwa,item.getVi_mean());
        }else {
            helper.setText(R.id.text_character_item_kaiwa,"")
                    .setText(R.id.text_c_roumaji_item_kaiwa,"")
                    .setText(R.id.text_kaiwa_item_kaiwa,item.getCharacter())
                    .setText(R.id.text_j_roumaji_item_kaiwa,item.getC_roumaji())
                    .setText(R.id.text_vi_mean_kaiwa,item.getVi_mean());
        }
    }
}
