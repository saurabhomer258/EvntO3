package com.evnto.indotech.evnto.Giftspackage;
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
import android.widget.Toast;

import com.evnto.indotech.evnto.Network.AsyncPost;
import com.evnto.indotech.evnto.Network.ResponseListener;
import com.evnto.indotech.evnto.OnItemClic;
import com.evnto.indotech.evnto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GiftsActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private GiftsAdapter giftsAdapter;
    private ArrayList<GiftsList> giftsListArrayList = new ArrayList<>();
    private Dbgifts dbgifts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }
    private void initView() {
        dbgifts = new Dbgifts(this);
        if (isNetworkAvailable()) {
            AsyncPost asyncPost = new AsyncPost(GiftsActivity.this, new ResponseListener() {
                @Override
                public void onResponse(String result) {
                    Log.d("JD", "getRespne = " + result);
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject e = jsonArray.getJSONObject(i);
                            GiftsList giftsList = new GiftsList();
                            giftsList.setId(e.getInt("id"));
                            giftsList.setName(e.getString("name"));
                            giftsList.setDescription(e.getString("description"));
                            giftsList.setPrice(e.getString("price"));
                            giftsList.setImageUrl(e.getString("imageurl"));
                            giftsList.setMinOrder(e.getString("minOrder"));
                            giftsList.setMenu(e.getString("menu"));
                            giftsList.addContact(giftsList);
                            giftsListArrayList.add(giftsList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvList = (RecyclerView) findViewById(R.id.rvList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvList.setLayoutManager(layoutManager);
                    giftsAdapter = new GiftsAdapter(getApplication(), giftsListArrayList);
                    rvList.setAdapter(giftsAdapter);
                    giftsAdapter.setOnClickListner(new GiftsAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent = new Intent(GiftsActivity.this,OnItemClic.class);
                            intent.putExtra("id",giftsListArrayList.get(position).getPrice());
                            intent.putExtra("name",giftsListArrayList.get(position).getName());
                            intent.putExtra("desc",giftsListArrayList.get(position).getDescription());
                            intent.putExtra("menu",giftsListArrayList.get(position).getMenu());
                            intent.putExtra("imageurl",giftsListArrayList.get(position).getImageUrl());

                            startActivity(intent);
                        }
                    });

                }
            }, true);
            asyncPost.execute("https://ceoindotech.000webhostapp.com/gifts.php");
        }/* else {
            giftsListArrayList.clear();
            giftsListArrayList = dbgifts.getAllContacts();
            rvList = (RecyclerView) findViewById(R.id.rvList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            rvList.setLayoutManager(layoutManager);
            giftsAdapter = new GiftsAdapter(getApplication(), giftsListArrayList);
            rvList.setAdapter(giftsAdapter);

            giftsAdapter.setOnClickListner(new GiftsAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(GiftsActivity.this,OnItemClic.class);
                    intent.putExtra("id",giftsListArrayList.get(position).getPrice());
                    intent.putExtra("name",giftsListArrayList.get(position).getName());
                    intent.putExtra("desc",giftsListArrayList.get(position).getDescription());
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


