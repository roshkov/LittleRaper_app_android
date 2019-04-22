package com.example.testhandin;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.ViewHolder> {


    private ArrayList<String> songList;
    final private OnListItemClickListener mOnListItemClickListener;


    recycleAdapter(ArrayList<String> songList, OnListItemClickListener listener) {
        this.songList = songList;
        mOnListItemClickListener = listener;
    }



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.song_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(songList.get(i));
    }


    public int getItemCount() {
        return songList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.myItemName);
            itemView.setOnClickListener(this); //list item listens for any clicks
        }

        @Override
        public void onClick(View v){
            mOnListItemClickListener.onListItemClick(getAdapterPosition()); //position of the view that was clicked

        }

    }


    ///////////clickListener
    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }






}
