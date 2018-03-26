package com.evnto.indotech.evnto.EventPlanner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.evnto.indotech.evnto.Network.AsyncPost;
import com.evnto.indotech.evnto.Network.ResponseListener;
import com.evnto.indotech.evnto.OnItemClic;
import com.evnto.indotech.evnto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventPlanners extends AppCompatActivity {
    private RecyclerView rvList;
    private WeddingAdapter weddingAdapter;
    private ArrayList<WeddingList> weddingListArrayList = new ArrayList<>();
    private Dbwedding dbwedding;
 ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_planners);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        flipper= (ViewFlipper) findViewById(R.id.viewflipper);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_out));

        initView();

    }
    private void initView() {
        dbwedding = new Dbwedding(this);
        if (isNetworkAvailable()) {
            AsyncPost asyncPost = new AsyncPost(EventPlanners.this, new ResponseListener() {
                @Override
                public void onResponse(String result) {
                    Log.d("JD", "getRespne = " + result);
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject e = jsonArray.getJSONObject(i);
                            WeddingList weddingList = new WeddingList();
                            weddingList.setId(e.getInt("id"));
                            weddingList.setName(e.getString("name"));
                            weddingList.setDescription(e.getString("description"));
                            weddingList.setPrice(e.getString("price"));
                            weddingList.setImageUrl(e.getString("imageurl"));
                            weddingList.setMinOrder(e.getString("minOrder"));
                            weddingList.setMenu(e.getString("menu"));
                            weddingList.addContact(weddingList);
                            weddingListArrayList.add(weddingList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvList = (RecyclerView) findViewById(R.id.rvList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvList.setLayoutManager(layoutManager);
                    weddingAdapter = new WeddingAdapter(getApplication(), weddingListArrayList);
                    rvList.setAdapter(weddingAdapter);
                    weddingAdapter.setOnClickListner(new WeddingAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent = new Intent(EventPlanners.this,OnItemClic.class);
                            intent.putExtra("id",weddingListArrayList.get(position).getPrice());
                            intent.putExtra("name",weddingListArrayList.get(position).getName());
                            intent.putExtra("desc",weddingListArrayList.get(position).getDescription());
                            intent.putExtra("menu",weddingListArrayList.get(position).getMenu());
                            intent.putExtra("imageurl",weddingListArrayList.get(position).getImageUrl());
                            startActivity(intent);
                        }
                    });

                }
            }, true);
            asyncPost.execute("https://ceoindotech.000webhostapp.com/wedding.php");
        } /*else {
            weddingListArrayList.clear();
            weddingListArrayList = dbwedding.getAllContacts();
            rvList = (RecyclerView) findViewById(R.id.rvList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            rvList.setLayoutManager(layoutManager);
            weddingAdapter = new WeddingAdapter(getApplication(), weddingListArrayList);
            rvList.setAdapter(weddingAdapter);

            weddingAdapter.setOnClickListner(new WeddingAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(EventPlanners.this,OnItemClic.class);
                    intent.putExtra("id",weddingListArrayList.get(position).getPrice());
                    intent.putExtra("name",weddingListArrayList.get(position).getName());
                    intent.putExtra("desc",weddingListArrayList.get(position).getDescription());
                    startActivity(intent);
                }
            });
        }*/
        else Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}



