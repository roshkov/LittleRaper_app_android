package com.example.testhandin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RhymeAdapter  extends RecyclerView.Adapter<RhymeAdapter.ViewHolder>
{

    final private OnListItemClickListener mOnListItemClickListener;
    private ArrayList<String> mRhymeList;


    RhymeAdapter(ArrayList<String> rhymeList, OnListItemClickListener listener )
    {
        mRhymeList=rhymeList;
        mOnListItemClickListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rhyme_list_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.word.setText(mRhymeList.get(position));


    }



    public int getItemCount(){
        return mRhymeList.size();
    }



    /////////////////
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView word;

        ViewHolder (View itemView){
            super(itemView);
            word = itemView.findViewById(R.id.myItemName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }

    }


    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
