package com.evnto.indotech.evnto.BookTable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evnto.indotech.evnto.R;

import java.util.ArrayList;

/**
 * Created by indotech on 8/26/2017.
 */

public class BookTableAdapter extends RecyclerView.Adapter<BookTableAdapter.MyViewHolder> {
    private Context co;
    private ArrayList<BookTableList> bookTableLists;
    private BookTableAdapter.OnItemClickListener mListener;

    public BookTableAdapter(Context context, ArrayList<BookTableList> bookTableListArrayList) {
        this.co = context;
        this.bookTableLists = bookTableListArrayList;
    }
    @Override
    public BookTableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(co).inflate(R.layout.model, parent, false);
        return new BookTableAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookTableAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(bookTableLists.get(position).getName());
        holder.tvPrice.setText(bookTableLists.get(position).getPrice());
        holder.desctxt.setText(bookTableLists.get(position).getDescription());
        holder.minorder.setText(bookTableLists.get(position).getMinOrder());

        Glide.with(co)
                .load(bookTableLists.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.ivFood);

        holder.btnCallNow.setOnClickListener(new BookTableAdapter.OnButtonClick(position));

    }



    @Override
    public int getItemCount() {
        return bookTableLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFood;
        private TextView tvName, tvPrice,minorder,desctxt;
        private Button btnCallNow;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivFood = (ImageView) itemView.findViewById(R.id.ivFood);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            desctxt = (TextView) itemView.findViewById(R.id.descTxt);
            minorder = (TextView) itemView.findViewById(R.id.minOrder);
            btnCallNow = (Button) itemView.findViewById(R.id.btnCallNow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnItemClick(getAdapterPosition());
                }
            });
        }
    }

    public class OnButtonClick implements View.OnClickListener {
        private int mPosition;

        public OnButtonClick(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View view) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            callIntent.setData(Uri.parse("tel://8305155116"));
            co.startActivity(callIntent);
        }


    }
    public void setOnClickListner(BookTableAdapter.OnItemClickListener onClickListner) {
        mListener = onClickListner;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

}
