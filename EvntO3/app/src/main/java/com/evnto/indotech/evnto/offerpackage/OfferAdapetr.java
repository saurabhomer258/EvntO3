package com.evnto.indotech.evnto.offerpackage;

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
 * Created by indotech on 8/27/2017.
 */

public class OfferAdapetr extends RecyclerView.Adapter<OfferAdapetr.MyViewHolder> {

    private Context offercontext;
    private ArrayList<OfferList> offerLists;
    private OfferAdapetr.OnItemClickListener mListener;

    public OfferAdapetr(Context context, ArrayList<OfferList> offerListArrayList) {
        this.offercontext = context;
        this.offerLists = offerListArrayList;
    }

    @Override
    public OfferAdapetr.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(offercontext).inflate(R.layout.model, parent, false);
        return new OfferAdapetr.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferAdapetr.MyViewHolder holder, int position) {
        holder.tvName.setText(offerLists.get(position).getName());
        holder.tvPrice.setText(offerLists.get(position).getPrice());
        holder.desctxt.setText(offerLists.get(position).getDescription());
        holder.minorder.setText(offerLists.get(position).getMinOrder());

        Glide.with(offercontext)
                .load(offerLists.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.ivFood);

        holder.btnCallNow.setOnClickListener(new OfferAdapetr.OnButtonClick(position));

    }

    @Override
    public int getItemCount() {
        return offerLists.size();
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
            offercontext.startActivity(callIntent);
        }


    }
    public void setOnClickListner(OfferAdapetr.OnItemClickListener onClickListner) {
        mListener = onClickListner;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
}
