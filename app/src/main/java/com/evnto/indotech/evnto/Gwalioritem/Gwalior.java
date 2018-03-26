package com.evnto.indotech.evnto.Gwalioritem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.evnto.indotech.evnto.Birthday.BirthdayParty;
import com.evnto.indotech.evnto.BookTable.BookingTable;
import com.evnto.indotech.evnto.CPD.CakePastyDrinks;
import com.evnto.indotech.evnto.Giftspackage.GiftsActivity;
import com.evnto.indotech.evnto.Hungary.hungarynow;
import com.evnto.indotech.evnto.Network.AsyncPost;
import com.evnto.indotech.evnto.Network.ResponseListener;
import com.evnto.indotech.evnto.Nonveg.NonVeg;
import com.evnto.indotech.evnto.OnItemClic;
import com.evnto.indotech.evnto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Gwalior extends AppCompatActivity {
    private RecyclerView rvList;
    private GwaliorAdapter gwaliorAdapter;
    private ArrayList<GwaliorList> gwaliorListArrayList = new ArrayList<>();
    private Dbgwalior dbgwalior;
    ViewPager viewPager;
    VPGadapter adapter;
    ViewFlipper flipper;
    Button b1,b2,b3,b4,b5,b6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwalior);
        adapter = new VPGadapter(this);

        flipper= (ViewFlipper) findViewById(R.id.viewflipper);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_out));
        b1 = (Button)findViewById(R.id.ivfoodorder);
        b2 = (Button)findViewById(R.id.ivgifts);
        b3 = (Button)findViewById(R.id.ivbooktable);
        b4 = (Button)findViewById(R.id.ivparty);
        b5 = (Button)findViewById(R.id.ivCPD);
        b6 = (Button)findViewById(R.id.ivNonvg);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gwalior.this,hungarynow.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gwalior.this,GiftsActivity.class);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gwalior.this,BookingTable.class);
                startActivity(intent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gwalior.this,BirthdayParty.class);
                startActivity(intent);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gwalior.this,CakePastyDrinks.class);
                startActivity(intent);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Gwalior.this, NonVeg.class);
                startActivity(i);
            }
        });




        initView();
    }

    private void initView() {
        dbgwalior = new Dbgwalior(this);
        if (isNetworkAvailable()) {
            AsyncPost asyncPost = new AsyncPost(Gwalior.this, new ResponseListener() {
                @Override
                public void onResponse(String result) {
                    Log.d("JD", "getRespne = " + result);
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject e = jsonArray.getJSONObject(i);
                            GwaliorList gwaliorList = new GwaliorList();
                            gwaliorList.setId(e.getInt("id"));
                            gwaliorList.setName(e.getString("name"));
                            gwaliorList.setDescription(e.getString("description"));
                            gwaliorList.setPrice(e.getString("price"));
                            gwaliorList.setImageUrl(e.getString("imageurl"));
                            gwaliorList.setMinOrder(e.getString("minOrder"));
                            gwaliorList.setMenu(e.getString("menu"));
                            gwaliorList.addContact(gwaliorList);
                            gwaliorListArrayList.add(gwaliorList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvList = (RecyclerView) findViewById(R.id.rvList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvList.setLayoutManager(layoutManager);
                    gwaliorAdapter = new GwaliorAdapter(getApplication(), gwaliorListArrayList);
                    rvList.setAdapter(gwaliorAdapter);
                    gwaliorAdapter.setOnClickListner(new GwaliorAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent = new Intent(Gwalior.this,OnItemClic.class);
                            intent.putExtra("id",gwaliorListArrayList.get(position).getPrice());
                            intent.putExtra("name",gwaliorListArrayList.get(position).getName());
                            intent.putExtra("desc",gwaliorListArrayList.get(position).getDescription());
                            intent.putExtra("menu",gwaliorListArrayList.get(position).getMenu());
                            intent.putExtra("imageurl",gwaliorListArrayList.get(position).getImageUrl());
                            startActivity(intent);
                        }
                    });

                }
            }, true);
            asyncPost.execute("https://ceoindotech.000webhostapp.com/spacecraft_select_images.php");
        } /*else {
            gwaliorListArrayList.clear();
            gwaliorListArrayList = dbgwalior.getAllContacts();
            rvList = (RecyclerView) findViewById(R.id.rvList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            rvList.setLayoutManager(layoutManager);
            gwaliorAdapter = new GwaliorAdapter(getApplication(), gwaliorListArrayList);
            rvList.setAdapter(gwaliorAdapter);

            gwaliorAdapter.setOnClickListner(new GwaliorAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(Gwalior.this,OnItemClic.class);
                    intent.putExtra("id",gwaliorListArrayList.get(position).getPrice());
                    intent.putExtra("name",gwaliorListArrayList.get(position).getName());
                    intent.putExtra("desc",gwaliorListArrayList.get(position).getDescription());
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
