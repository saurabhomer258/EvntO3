package com.evnto.indotech.evnto.Gwalioritem;

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


public class GwaliorAdapter extends RecyclerView.Adapter<GwaliorAdapter.MyViewHolder> {
    private Context c;
    private ArrayList<GwaliorList> gwaliorLists;
    private OnItemClickListener mListener;

    public GwaliorAdapter(Context context, ArrayList<GwaliorList> gwaliorListArrayList) {
        this.c = context;
        this.gwaliorLists = gwaliorListArrayList;
    }
    @Override
    public GwaliorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        return new GwaliorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GwaliorAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(gwaliorLists.get(position).getName());
        holder.tvPrice.setText(gwaliorLists.get(position).getPrice());
        holder.desctxt.setText(gwaliorLists.get(position).getDescription());
        holder.minorder.setText(gwaliorLists.get(position).getMinOrder());

        Glide.with(c)
                .load(gwaliorLists.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.ivFood);

        holder.btnCallNow.setOnClickListener(new GwaliorAdapter.OnButtonClick(position));

    }

    @Override
    public int getItemCount() {
        return gwaliorLists.size();
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
            c.startActivity(callIntent);
        }


    }
    public void setOnClickListner(GwaliorAdapter.OnItemClickListener onClickListner) {
        mListener = onClickListner;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
}

