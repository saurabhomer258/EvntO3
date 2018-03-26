package com.evnto.indotech.evnto.UpcmingEvnt;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.evnto.indotech.evnto.Network.AsyncPost;
import com.evnto.indotech.evnto.Network.ResponseListener;
import com.evnto.indotech.evnto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpcomeingEvent extends AppCompatActivity {
    private UpcmAdapter upcmAdapter;
    private RecyclerView rvList;
    private ArrayList<UpcmList> upcmListArrayList = new ArrayList<>();
    private Dbupcm dbupcm;
    String names[],desc[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomeing_event);

        initView();
    }
    private void initView() {
        dbupcm = new Dbupcm(this);
        if (isNetworkAvailable()) {
            AsyncPost asyncPost = new AsyncPost(UpcomeingEvent.this, new ResponseListener() {
                @Override
                public void onResponse(String result) {
                    Log.d("JD", "getRespne = " + result);
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        names=new String[jsonArray.length()];
                        desc  =new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject e = jsonArray.getJSONObject(i);
                            UpcmList upcmList = new UpcmList();
                              names[i]=e.getString("name");
                            upcmList.setName(e.getString("name"));
                            desc[i]=e.getString("description");
                            upcmList.setDescription(e.getString("description"));
                            upcmList.setImageUrl(e.getString("imageurl"));
                            dbupcm.addContact(upcmList);
                            upcmListArrayList.add(upcmList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvList = (RecyclerView) findViewById(R.id.rvList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvList.setLayoutManager(layoutManager);
                    upcmAdapter = new UpcmAdapter(getApplication(), upcmListArrayList);
                    rvList.setAdapter(upcmAdapter);
                    upcmAdapter.setOnClickListner(new UpcmAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent = new Intent(UpcomeingEvent.this,NextUpcm.class);

                            intent.putExtra("name",names[position]);
                            intent.putExtra("desc",desc[position]);
                            intent.putExtra("imageurl",upcmListArrayList.get(position).getImageUrl());

                            startActivity(intent);
                        }
                    });

                }
            }, true);
            asyncPost.execute("https://ceoindotech.000webhostapp.com/UpcomeEvnt.php");
        } /*else {
                hungaryListArrayList.clear();
                hungaryListArrayList = dbhungary.getAllContacts();
                rvList = (RecyclerView) findViewById(R.id.rvList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvList.setLayoutManager(layoutManager);
                hungaryAdapter = new HungaryAdapter(getApplication(), hungaryListArrayList);
                rvList.setAdapter(hungaryAdapter);

                hungaryAdapter.setOnClickListner(new HungaryAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        Intent intent = new Intent(hungarynow.this,OnItemClic.class);
                        intent.putExtra("id",hungaryListArrayList.get(position).getPrice());
                        intent.putExtra("name",hungaryListArrayList.get(position).getName());
                        intent.putExtra("desc",hungaryListArrayList.get(position).getDescription());
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




