package vn.edu.hust.soict.khacsan.myapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import vn.edu.hust.soict.khacsan.myapp.R;

public class CardFlipFrontFragment extends Fragment{
    private ImageButton mBtnAudioPlay;
    private TextView mTextViewKana,mTextViewPage;
    private String text,page;
    private ClickFlipListener listener;



    public CardFlipFrontFragment() {
    }

    public static CardFlipFrontFragment newInstance(String text, String page,ClickFlipListener listener) {
        Bundle args = new Bundle();
        args.putString("TEXT",text);
        args.putString("PAGE_FRONT",page);
        CardFlipFrontFragment fragment = new CardFlipFrontFragment();
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.card_flip_front, container, false);
        view.findViewById(R.id.btn_audio_card_front).setOnClickListener(audioClick);
        mBtnAudioPlay = view.findViewById(R.id.btn_audio_play_front);
        mTextViewKana = view.findViewById(R.id.text_item_front_card);
        mTextViewPage = view.findViewById(R.id.text_page);

        Bundle bundle = getArguments();
        if(bundle != null){
            text = bundle.getString("TEXT");
            page = bundle.getString("PAGE_FRONT");
        }else {
            text = "";
            page = "";
        }


        mTextViewKana.setText(text);
        mTextViewPage.setText(page);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFlip(false,false);
            }
        });


        return view;
    }

    private View.OnClickListener audioClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBtnAudioPlay.setVisibility(View.VISIBLE);
            listener.onPlayAudio(false);
        }
    };


    public void setListener(ClickFlipListener listener) {
        this.listener = listener;
    }

}
