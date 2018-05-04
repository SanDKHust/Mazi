package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemExample;
import vn.edu.hust.soict.khacsan.myapp.model.database.Kana;
import vn.edu.hust.soict.khacsan.myapp.svgwriter.SVGCanvasView;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.ExampleAdapter;


public class CardFlipBackFragment extends Fragment implements View.OnClickListener {
    private ImageButton mBtnAudioPlay, mBtnLeft, mBtnRight;
    private RecyclerView mRecyclerViewExample;
    private SVGCanvasView svgCanvasView;
    private Kana kana;
    private String page;
    private int typeTable;
    private ClickFlipListener listener;

    public CardFlipBackFragment() {
    }

    public static CardFlipBackFragment newInstance(Kana kana, String page, int type, ClickFlipListener listener) {
        Bundle args = new Bundle();
        args.putParcelable("KANA_BACK", kana);
        args.putString("PAGE_BACK", page);
        args.putInt("TYPE_TABLE", type);
        CardFlipBackFragment fragment = new CardFlipBackFragment();
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_flip_back, container, false);
        view.findViewById(R.id.btn_audio_card_back).setOnClickListener(this);
        mBtnAudioPlay = view.findViewById(R.id.btn_audio_play_back);
        mRecyclerViewExample = view.findViewById(R.id.recycler_example);
        svgCanvasView = view.findViewById(R.id.svg_cana_view);

        mRecyclerViewExample.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewExample.setHasFixedSize(true);

        view.findViewById(R.id.btn_left_card_back).setOnClickListener(this);
        view.findViewById(R.id.btn_right_card_back).setOnClickListener(this);


        Bundle bundle = getArguments();
        if (bundle != null) {
            kana = bundle.getParcelable("KANA_BACK");
            page = bundle.getString("PAGE_BACK");
            typeTable = bundle.getInt("TYPE_TABLE");
            ((TextView) view.findViewById(R.id.text_page_back_flip)).setText(page);
        } else {
            kana = new Kana();
            page = "";
            typeTable = 0;
        }

        if (kana.getHira_full() != null) {
            ArrayList<String> listPath;
            if (typeTable == 1)
                listPath = new ArrayList<>(Arrays.asList(kana.getHira_full().split("##")));
            else listPath = new ArrayList<>(Arrays.asList(kana.getKata_full().split("##")));
            svgCanvasView.init(listPath);
            view.findViewById(R.id.btn_repaint).setOnClickListener(this);
        } else {
            view.findViewById(R.id.btn_repaint).setVisibility(View.GONE);
            svgCanvasView.setVisibility(View.GONE);
        }
        if (kana.getExample() != null) {
            setupExample();
        }
        view.setOnClickListener(this);
        return view;
    }

    private void setupExample() {
        List<ItemExample> examples = new ArrayList<>();
        String strExample = kana.getExample();
        if (strExample.contains("∴")) {
            if(strExample.contains("※")){
                try {
                    ArrayList<String> listEx = new ArrayList<>(Arrays.asList(strExample.split("※")));

                    for (int i = 0; i < listEx.size(); i++) {
                        ArrayList<String> itemFormat = new ArrayList<>(Arrays.asList(listEx.get(i).split("∴")));
                        ItemExample example = new ItemExample(itemFormat.get(0), "", itemFormat.get(1));
                        examples.add(example);
                    }
                } catch (IndexOutOfBoundsException indexEx) {
                    indexEx.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else {
                try {
                    ArrayList<String> listEx = new ArrayList<>(Arrays.asList(strExample.split("\n")));

                    for (int i = 0; i < listEx.size(); i++) {
                        ArrayList<String> itemFormat = new ArrayList<>(Arrays.asList(listEx.get(i).split("∴")));
                        if(itemFormat.size() == 3) {
                            ItemExample example = new ItemExample(itemFormat.get(0), itemFormat.get(1), itemFormat.get(2));
                            examples.add(example);
                        }else {
                            if(itemFormat.size() == 2) {
                                ItemExample example = new ItemExample(itemFormat.get(0), "", itemFormat.get(1));
                                examples.add(example);
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException indexEx) {
                    indexEx.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }else {
            ItemExample example = new ItemExample(strExample,"","");
            examples.add(example);
        }

        ExampleAdapter adapter = new ExampleAdapter(R.layout.item_example, examples);
        mRecyclerViewExample.setAdapter(adapter);
    }


    public void setListener(ClickFlipListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_repaint) {
            svgCanvasView.rePaint();
        } else if (id == R.id.container_flip_back) {
            listener.onFlip(true, false);
        } else if (id == R.id.btn_right_card_back) {
            listener.onFlip(true, true);
        } else if (id == R.id.btn_left_card_back) {
            listener.onFlip(true, false);
        } else if (id == R.id.btn_audio_card_back) {
            mBtnAudioPlay.setVisibility(View.VISIBLE);
            listener.onPlayAudio(true);
        }
    }


}
