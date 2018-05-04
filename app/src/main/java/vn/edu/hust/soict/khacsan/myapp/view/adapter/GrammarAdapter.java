package vn.edu.hust.soict.khacsan.myapp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;
import java.util.regex.Pattern;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.database.Grammar;

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.MyViewHolder> implements View.OnClickListener {
    private List<Grammar> grammarList;

   public GrammarAdapter(List<Grammar> grammarList){
       this.grammarList = grammarList;
   }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grammar, parent, false);

        MyViewHolder holder = new MyViewHolder(itemView);

        // Sets the click adapter for the entire cell
        // to the one in this class.
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Grammar grammar = grammarList.get(position);
        holder.mTxtIndex.setText(String.valueOf(grammar.getId()));
        holder.mTxtGrammarName.setText(grammar.getName());
        holder.mTxtGrammarContent.setText(grammar.getContent().replaceAll("[$]\\w[$]"," "));
        if (grammar.isExpanded()){
            holder.mTxtGrammarContent.setVisibility(View.VISIBLE);
        }else {
            holder.mTxtGrammarContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return grammarList.size();
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        Grammar grammar = grammarList.get(holder.getAdapterPosition());
        grammar.setExpanded(!grammar.isExpanded());
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtIndex,mTxtGrammarName,mTxtGrammarContent;
        public MyViewHolder(View view) {
            super(view);
            mTxtIndex = view.findViewById(R.id.text_index_item_grammar);
            mTxtGrammarName = view.findViewById(R.id.text_item_name_grammar);
            mTxtGrammarContent = view.findViewById(R.id.text_item_content_grammar);
        }
    }
}
