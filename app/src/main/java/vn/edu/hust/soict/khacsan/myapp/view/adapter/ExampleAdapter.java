package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemExample;

public class ExampleAdapter extends BaseQuickAdapter<ItemExample,BaseViewHolder> {
    public ExampleAdapter(int layoutResId, @Nullable List<ItemExample> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemExample item) {
        helper.setText(R.id.text_example_1,item.getText1())
                .setText(R.id.text_example_2,item.getText2())
                .setText(R.id.mean_example,item.getMean());
    }
}
