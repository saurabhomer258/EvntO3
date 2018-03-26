package com.evnto.indotech.evnto.Hungary;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.evnto.indotech.evnto.Birthday.BirthdayParty;
import com.evnto.indotech.evnto.BookTable.BookingTable;
import com.evnto.indotech.evnto.CPD.CakePastyDrinks;
import com.evnto.indotech.evnto.Chinese;
import com.evnto.indotech.evnto.FastFood;
import com.evnto.indotech.evnto.Giftspackage.GiftsActivity;
import com.evnto.indotech.evnto.Italian;
import com.evnto.indotech.evnto.Network.AsyncPost;
import com.evnto.indotech.evnto.Network.ResponseListener;
import com.evnto.indotech.evnto.Nonveg.NonVeg;
import com.evnto.indotech.evnto.North;
import com.evnto.indotech.evnto.OnItemClic;
import com.evnto.indotech.evnto.R;
import com.evnto.indotech.evnto.South;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class hungarynow extends AppCompatActivity {


        private RecyclerView rvList;
        ImageView ivClub,noDataImg,noNetworkImg,imageView;
        SearchView sv;
        String search_url="https://ceoindotech.000webhostapp.com/search.php";
        private HungaryAdapter hungaryAdapter;
        private ArrayList<HungaryList> hungaryListArrayList = new ArrayList<>();
        private Dbhungary dbhungary;
    Button b1,b2,b3,b4,b5,b6,b11,b12,b13,b14,b15;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hungarynow);
            imageView = (ImageView)findViewById(R.id.ivhungary);


            b2 = (Button)findViewById(R.id.ivgifts);
            b3 = (Button)findViewById(R.id.ivbooktable);
            b4 = (Button)findViewById(R.id.ivparty);
            b5 = (Button)findViewById(R.id.ivCPD);
            b6 = (Button)findViewById(R.id.ivNonvg);
            b11 = (Button)findViewById(R.id.ivitalian);
            b12 = (Button)findViewById(R.id.ivChaines);
            b13 = (Button)findViewById(R.id.ivNorthIndian);
            b14 = (Button)findViewById(R.id.ivSouthIndian);
            b15 = (Button)findViewById(R.id.ivFastFood);




            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(hungarynow.this,GiftsActivity.class);
                    startActivity(intent);
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(hungarynow.this,BookingTable.class);
                    startActivity(intent);
                }
            });
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(hungarynow.this,BirthdayParty.class);
                    startActivity(intent);
                }
            });
            b5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(hungarynow.this,CakePastyDrinks.class);
                    startActivity(intent);
                }
            });
            b6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(hungarynow.this, NonVeg.class);
                    startActivity(i);
                }
            });
            b11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(hungarynow.this, Italian.class);
                    startActivity(i);
                }
            });
            b12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(hungarynow.this, Chinese.class);
                    startActivity(i);
                }
            });
            b13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(hungarynow.this, North.class);
                    startActivity(i);
                }
            });
            b14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(hungarynow.this, South.class);
                    startActivity(i);
                }
            });
            b15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(hungarynow.this, FastFood.class);
                    startActivity(i);
                }
            });



            initView();
        }

        private void initView() {
            dbhungary = new Dbhungary(this);
            if (isNetworkAvailable()) {
                AsyncPost asyncPost = new AsyncPost(hungarynow.this, new ResponseListener() {
                    @Override
                    public void onResponse(String result) {
                        Log.d("JD", "getRespne = " + result);
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject e = jsonArray.getJSONObject(i);
                                HungaryList hungaryList = new HungaryList();
                                hungaryList.setId(e.getInt("id"));
                                hungaryList.setName(e.getString("name"));
                                hungaryList.setDescription(e.getString("description"));
                                hungaryList.setPrice(e.getString("price"));
                                hungaryList.setImageUrl(e.getString("imageurl"));
                                hungaryList.setMinOrder(e.getString("minOrder"));
                                hungaryList.setMenu(e.getString("menu"));
                                dbhungary.addContact(hungaryList);
                                hungaryListArrayList.add(hungaryList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                                intent.putExtra("minOrder",hungaryListArrayList.get(position).getMinOrder());
                                intent.putExtra("menu",hungaryListArrayList.get(position).getMenu());
                                intent.putExtra("imageurl",hungaryListArrayList.get(position).getImageUrl());

                                startActivity(intent);
                            }
                        });

                    }
                }, true);
                asyncPost.execute("https://ceoindotech.000webhostapp.com/hungary.php");
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


