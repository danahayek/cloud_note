package com.example.cloudnoteassignment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudnoteassignment.EditNote;
import com.example.cloudnoteassignment.MainActivity;
import com.example.cloudnoteassignment.R;
import com.example.cloudnoteassignment.ShowNote;
import com.example.cloudnoteassignment.model.note;

import java.util.ArrayList;
import java.util.List;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {
    ArrayList<note> noteArrayList;
    Context context;
    private ItemClickListener mClickListener;
    private EditClickListener eClickListener;


    //


     public noteAdapter(ArrayList<note> noteArrayList, Context context , ItemClickListener onClick , EditClickListener OnClick2 ) {
        this.noteArrayList = noteArrayList;
        this.context = context;
        this.mClickListener = onClick;
        this.eClickListener=OnClick2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView address;
        TextView body;
        ImageView delete;
        ImageView edit;

        ViewHolder(View itemView) {
            super(itemView);
            this.address = itemView.findViewById(R.id.address_show);
            this.body = itemView.findViewById(R.id.body_show);
            this.delete = itemView.findViewById(R.id.delete);
            this.edit = itemView.findViewById(R.id.edit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        note not = noteArrayList.get(position);
        holder.address.setText(not.getAddress());
        holder.body.setText(not.getBody());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClickListener.onItemClick(holder.getAdapterPosition(), noteArrayList.get(position).getId());

            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eClickListener.onItemClick2(holder.getAdapterPosition(),noteArrayList.get(position).getId());
//                Intent intent = new Intent(context, EditNote.class);
//                context.startActivity(intent);

            }
        });
    }
    public interface ItemClickListener {
        void onItemClick(int position, String id);

    }
    public interface EditClickListener {
        void onItemClick2(int position, String id);

    }

    note getItem(int id) {

        return noteArrayList.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    void setClickListener(EditClickListener editClickListener) {
        this.eClickListener = editClickListener;
    }
    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

}
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.ClickListener = itemClickListener;
//    }
//
//    public interface ItemClickListener {
//        void onItemClick(int position, String id);
//    }
//
//    public interface ItemClickListener2{
//        void onItemClick2(int position, String id);
//    }
