package com.evnto.indotech.evnto.Gwalioritem;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evnto.indotech.evnto.R;

/**
 * Created by imakash on 9/14/2017.
 */

public class VPGadapter extends PagerAdapter {
    private int [] imgs = {R.mipmap.slider1,R.mipmap.slider2,R.mipmap.slide3,R.mipmap.dining,R.mipmap.event};
    private LayoutInflater inflater;
    private Context ctx;

    public VPGadapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override

    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.swip,container,false);
        ImageView img = (ImageView)v.findViewById(R.id.imageView);
        img.setImageResource(imgs[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
