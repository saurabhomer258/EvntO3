package com.evnto.indotech.evnto.UpcmingEvnt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evnto.indotech.evnto.R;

import java.util.ArrayList;

/**
 * Created by imakash on 9/8/2017.
 */

public class UpcmAdapter extends  RecyclerView.Adapter<UpcmAdapter.MyViewHolder> {
    private Context con;
    private ArrayList<UpcmList> upcmLists;
    private OnItemClickListener mListener;

    public UpcmAdapter(Context context, ArrayList<UpcmList> upcmListArrayList) {
        this.con = context;
        this.upcmLists = upcmListArrayList;
    }
    @Override
    public UpcmAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(con).inflate(R.layout.evntmodal, parent, false);
        return new UpcmAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpcmAdapter.MyViewHolder holder, int position) {

        holder.tvName.setText(upcmLists.get(position).getName());
        holder.desctxt.setText(upcmLists.get(position).getDescription());

        Glide.with(con)
                .load(upcmLists.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.ivFood);
    }

    @Override
    public int getItemCount() {
        return upcmLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivFood;
        private TextView tvName, tvPrice,minorder,desctxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivFood = (ImageView) itemView.findViewById(R.id.ivupcm);
            tvName = (TextView) itemView.findViewById(R.id.nameupcm);
            desctxt = (TextView) itemView.findViewById(R.id.descupcm);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnItemClick(getAdapterPosition());
                }
            });
        }
    }
  
    public void setOnClickListner(UpcmAdapter.OnItemClickListener onClickListner) {
        mListener = (OnItemClickListener) onClickListner;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
}
