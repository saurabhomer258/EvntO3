package com.evnto.indotech.evnto.offerpackage;

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

public class Offers extends AppCompatActivity {
    private RecyclerView rvList;
    private OfferAdapetr offerAdapetr;
    private ArrayList<OfferList> offerListArrayList = new ArrayList<>();
    private Dboffer dboffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      initView();
    }

    private void initView() {
        dboffer = new Dboffer(this);
        if (isNetworkAvailable()) {
            AsyncPost asyncPost = new AsyncPost(Offers.this, new ResponseListener() {
                @Override
                public void onResponse(String result) {
                    Log.d("JD", "getRespne = " + result);
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject e = jsonArray.getJSONObject(i);
                            OfferList offerList = new OfferList();
                            offerList.setId(e.getInt("id"));
                            offerList.setName(e.getString("name"));
                            offerList.setDescription(e.getString("description"));
                            offerList.setPrice(e.getString("price"));
                            offerList.setImageUrl(e.getString("imageurl"));
                            offerList.setMinOrder(e.getString("offer"));
                            offerList.setMenu(e.getString("menu"));
                            dboffer.addContact(offerList);
                            offerListArrayList.add(offerList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvList = (RecyclerView) findViewById(R.id.rvList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvList.setLayoutManager(layoutManager);
                    offerAdapetr = new OfferAdapetr(getApplication(), offerListArrayList);
                    rvList.setAdapter(offerAdapetr);
                    offerAdapetr.setOnClickListner(new OfferAdapetr.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent = new Intent(Offers.this,OnItemClic.class);
                            intent.putExtra("id",offerListArrayList.get(position).getPrice());
                            intent.putExtra("name",offerListArrayList.get(position).getName());
                            intent.putExtra("desc",offerListArrayList.get(position).getDescription());
                            intent.putExtra("menu",offerListArrayList.get(position).getMenu());
                            intent.putExtra("imageurl",offerListArrayList.get(position).getImageUrl());

                            startActivity(intent);
                        }
                    });
                }
            }, true);
            asyncPost.execute("https://ceoindotech.000webhostapp.com/offer.php");
        } /*else {
            offerListArrayList.clear();
            offerListArrayList = dboffer.getAllContacts();
            rvList = (RecyclerView) findViewById(R.id.rvList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            rvList.setLayoutManager(layoutManager);
            offerAdapetr = new OfferAdapetr(getApplication(), offerListArrayList);
            rvList.setAdapter(offerAdapetr);

            offerAdapetr.setOnClickListner(new OfferAdapetr.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(Offers.this,OnItemClic.class);
                    intent.putExtra("id",offerListArrayList.get(position).getPrice());
                    intent.putExtra("name",offerListArrayList.get(position).getName());
                    intent.putExtra("desc",offerListArrayList.get(position).getDescription());
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




