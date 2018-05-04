package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemListKana;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;

public class KanaAdapter extends BaseMultiItemQuickAdapter<ItemListKana, BaseViewHolder> {
    private int typeTable = 0;
    private AudioClickListener listener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public KanaAdapter(List<ItemListKana> data, int type, AudioClickListener listener) {
        super(data);
        addItemType(ItemListKana.FIVE_ITEM, R.layout.item_five_kana);
        addItemType(ItemListKana.THREE_ITEM, R.layout.item_three_kana);
        this.typeTable = type;
        this.listener = listener;
    }


    @Override
    protected void convert(BaseViewHolder helper, ItemListKana item) {
        int type = helper.getItemViewType();
        ArrayList<Kana> kanas = item.getKanas();
        if (type == ItemListKana.FIVE_ITEM) {
            final View view1 = helper.getView(R.id.view_item_one_five_kana);
            final View view2 = helper.getView(R.id.view_item_tow_five_kana);
            final View view3 = helper.getView(R.id.view_item_three_five_kana);
            final View view4 = helper.getView(R.id.view_item_four_five_kana);
            final View view5 = helper.getView(R.id.view_item_five_five_kana);
            for (int i = 0; i < kanas.size(); i++) {
                final Kana kana = kanas.get(i);
                if (i == 0) {
                    ((TextView) view1.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) view1.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) view1.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    view1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(view1,kana);
                        }
                    });
                    View btnAudio = view1.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                } else if (i == 1) {
                    ((TextView) view2.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) view2.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) view2.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    view2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(view2,kana);
                        }
                    });
                    View btnAudio = view2.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                } else if (i == 2) {
                    ((TextView) view3.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) view3.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) view3.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    view3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(view3,kana);
                        }
                    });
                    View btnAudio = view3.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                } else if (i == 3) {
                    ((TextView) view4.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) view4.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) view4.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    view4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(view4,kana);
                        }
                    });
                    View btnAudio = view4.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                } else if (i == 4) {
                    ((TextView) view5.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) view5.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) view5.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    view5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(view5,kana);
                        }
                    });
                    View btnAudio = view5.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                }
            }

        } else if (type == ItemListKana.THREE_ITEM) {
            final View viewLeft = helper.getView(R.id.view_item_left_three_kana);
            final View viewCenter = helper.getView(R.id.view_item_center_three_kana);
            final View viewRight = helper.getView(R.id.view_item_right_three_kana);
            for (int i = 0; i < kanas.size(); i++) {
                final Kana kana = kanas.get(i);
                if (i == 0) {
                    ((TextView) viewLeft.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) viewLeft.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) viewLeft.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    viewLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(viewLeft,kana);
                        }
                    });
                    View btnAudio = viewLeft.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                } else if (i == 1) {
                    ((TextView) viewCenter.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) viewCenter.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) viewCenter.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    viewCenter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(viewCenter,kana);
                        }
                    });
                    View btnAudio = viewCenter.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                } else if (i == 2) {
                    ((TextView) viewRight.findViewById(R.id.text_romaji)).setText(kana.getRomaji());
                    if (typeTable == 1)
                        ((TextView) viewRight.findViewById(R.id.text_hira_kara)).setText(kana.getHira());
                    else if (typeTable == 2)
                        ((TextView) viewRight.findViewById(R.id.text_hira_kara)).setText(kana.getKata());
                    viewRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClickViewAudio(viewRight,kana);
                        }
                    });
                    View btnAudio = viewRight.findViewById(R.id.btn_audio);
                    if(kana.isClicked()) btnAudio.setVisibility(View.VISIBLE);
                    else btnAudio.setVisibility(View.GONE);
                }
            }
        }
    }

    public int getType() {
        return typeTable;
    }


    public interface AudioClickListener {
        void onClickViewAudio(View view,Kana kana);
    }
}
