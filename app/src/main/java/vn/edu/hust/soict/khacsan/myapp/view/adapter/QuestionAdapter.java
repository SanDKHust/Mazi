package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Question;
import vn.edu.hust.soict.khacsan.myapp.model.entity.SectionQuestion;

public class QuestionAdapter  extends BaseSectionQuickAdapter<SectionQuestion, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */

    private int typeKana;

    public QuestionAdapter(int layoutResId, int sectionHeadResId, List<SectionQuestion> data,int typeKana) {
        super(layoutResId, sectionHeadResId, data);
        this.typeKana = typeKana;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionQuestion item) {
        helper.setText(R.id.header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionQuestion item) {
        Question question = item.t;
        if(question.getSoLanTLSai() == 0){
            ((TextView) helper.getView(R.id.text_kata_hira_item_result)).setTextColor(mContext.getResources().getColor(R.color.colorPrimary));;
            ((TextView) helper.getView(R.id.text_romaji_item_result)).setTextColor(mContext.getResources().getColor(R.color.colorPrimary));;
            ((TextView) helper.getView(R.id.text_count_item_result)).setTextColor(mContext.getResources().getColor(R.color.colorPrimary));;
        }else {
            ((TextView) helper.getView(R.id.text_kata_hira_item_result)).setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
            ((TextView) helper.getView(R.id.text_romaji_item_result)).setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));;
            ((TextView) helper.getView(R.id.text_count_item_result)).setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));;
        }
        if(typeKana == 2) {

            helper.setText(R.id.text_kata_hira_item_result, question.getKana().getKata())
                    .setText(R.id.text_romaji_item_result, question.getKana().getRomaji())
                    .setText(R.id.text_count_item_result, question.getSoLanTLSai() == 0 ? "" : String.valueOf(question.getSoLanTLSai()));
        }else {
            helper.setText(R.id.text_kata_hira_item_result, question.getKana().getHira())
                    .setText(R.id.text_romaji_item_result, question.getKana().getRomaji())
                    .setText(R.id.text_count_item_result, question.getSoLanTLSai() == 0 ? "" : String.valueOf(question.getSoLanTLSai()));
        }
    }
}
