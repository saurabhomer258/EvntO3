package com.evnto.indotech.evnto.Birthday;

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

public class BirthdayParty extends AppCompatActivity {
    private RecyclerView rvList;
    private BirthdayAdapter birthdayAdapter;
    private ArrayList<BirthdayList> birthdayListArrayList = new ArrayList<>();
    private Dbbirthday dbbirthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_party);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }
    private void initView() {
        dbbirthday = new Dbbirthday(this);
        if (isNetworkAvailable()) {
            AsyncPost asyncPost = new AsyncPost(BirthdayParty.this, new ResponseListener() {
                @Override
                public void onResponse(String result) {
                    Log.d("JD", "getRespne = " + result);
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject e = jsonArray.getJSONObject(i);
                            BirthdayList birthdayList = new BirthdayList();
                            birthdayList.setId(e.getInt("id"));
                            birthdayList.setName(e.getString("name"));
                            birthdayList.setDescription(e.getString("description"));
                            birthdayList.setPrice(e.getString("price"));
                            birthdayList.setImageUrl(e.getString("imageurl"));
                            birthdayList.setMinOrder(e.getString("minOrder"));
                            birthdayList.setMenu(e.getString("menu"));
                            birthdayList.addContact(birthdayList);
                            birthdayListArrayList.add(birthdayList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvList = (RecyclerView) findViewById(R.id.rvList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvList.setLayoutManager(layoutManager);
                    birthdayAdapter = new BirthdayAdapter(getApplication(), birthdayListArrayList);
                    rvList.setAdapter(birthdayAdapter);
                    birthdayAdapter.setOnClickListner(new BirthdayAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent = new Intent(BirthdayParty.this,OnItemClic.class);
                            intent.putExtra("id",birthdayListArrayList.get(position).getPrice());
                            intent.putExtra("name",birthdayListArrayList.get(position).getName());
                            intent.putExtra("desc",birthdayListArrayList.get(position).getDescription());
                            intent.putExtra("menu",birthdayListArrayList.get(position).getMenu());
                            intent.putExtra("imageurl",birthdayListArrayList.get(position).getImageUrl());
                            startActivity(intent);
                        }
                    });

                }
            }, true);
            asyncPost.execute("https://ceoindotech.000webhostapp.com/birthday.php");
        } /*else {
            birthdayListArrayList.clear();
            birthdayListArrayList = dbbirthday.getAllContacts();
            rvList = (RecyclerView) findViewById(R.id.rvList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            rvList.setLayoutManager(layoutManager);
            birthdayAdapter = new BirthdayAdapter(getApplication(), birthdayListArrayList);
            rvList.setAdapter(birthdayAdapter);

            birthdayAdapter.setOnClickListner(new BirthdayAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(BirthdayParty.this,OnItemClic.class);
                    intent.putExtra("id",birthdayListArrayList.get(position).getPrice());
                    intent.putExtra("name",birthdayListArrayList.get(position).getName());
                    intent.putExtra("desc",birthdayListArrayList.get(position).getDescription());
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


