package com.evnto.indotech.evnto.Main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evnto.indotech.evnto.About;
import com.evnto.indotech.evnto.Birthday.BirthdayParty;
import com.evnto.indotech.evnto.Birthday.SurpriseParty;
import com.evnto.indotech.evnto.BookTable.BookingTable;
import com.evnto.indotech.evnto.EventPlanner.EventPlanners;
import com.evnto.indotech.evnto.Eventclub.LoadImage;
import com.evnto.indotech.evnto.Giftspackage.GiftsActivity;
import com.evnto.indotech.evnto.Gwalioritem.Gwalior;
import com.evnto.indotech.evnto.Hungary.hungarynow;
import com.evnto.indotech.evnto.PhoneAuthActivity;
import com.evnto.indotech.evnto.R;
import com.evnto.indotech.evnto.UpcmingEvnt.UpcomeingEvent;
import com.evnto.indotech.evnto.offerpackage.Offers;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,LoadImage.Listener {

    CustomAdapter adapter;
    ViewPager viewPager;
    ImageButton btgwalior;
    ListView lv;
    TextView tv,tv2,tv3;
    ImageView ivClub,noDataImg,noNetworkImg;
    SearchView sv;
    ViewFlipper flipper;

    String serverurl = "https://ceoindotech.000webhostapp.com/text1.php";
    String serverurl2 = "https://ceoindotech.000webhostapp.com/txt2.php";
    String serverurl3 = "https://ceoindotech.000webhostapp.com/txt3.php";
    String server_url = "https://ceoindotech.000webhostapp.com/public/eventclub.jpg";
    final static String urlAddress="https://ceoindotech.000webhostapp.com/user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView) findViewById(R.id.lv);

//        viewPager = (ViewPager)findViewById(R.id.view_pager);
//        adapter = new CustomAdapter(this);
//        viewPager.setAdapter(adapter);
//
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        tv = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);

        //for tv1
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                        requestQueue.stop();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("Check your internet connection");
                error.printStackTrace();
                requestQueue.stop();

            }
        });
        requestQueue.add(stringRequest);

        //for tv2
        final RequestQueue requestQueue1 = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, serverurl2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv2.setText(response);
                        requestQueue1.stop();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv2.setText("Check your internet connection");
                error.printStackTrace();
                requestQueue1.stop();

            }
        });
        requestQueue1.add(stringRequest1);

        //for tv3
        final RequestQueue requestQueue2 = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, serverurl3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv3.setText(response);
                        requestQueue2.stop();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv3.setText("Check your internet connection");
                error.printStackTrace();
                requestQueue2.stop();

            }
        });
        requestQueue2.add(stringRequest2);


        flipper= (ViewFlipper) findViewById(R.id.viewflipper);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_out));

        btgwalior = (ImageButton) findViewById(R.id.btgwalior);
        btgwalior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gwalior.class);
                startActivity(intent);
            }
        });

        ImageButton hungaryBT = (ImageButton) findViewById(R.id.IBhungary);
        hungaryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),hungarynow.class);
                startActivity(intent);
            }
        });


        ImageButton surprise = (ImageButton) findViewById(R.id.surpriseIB);
        surprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SurpriseParty.class);
                startActivity(intent);
            }
        });


        ImageButton birthday = (ImageButton) findViewById(R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BirthdayParty.class);
                startActivity(intent);
            }
        });



        ImageButton booking = (ImageButton) findViewById(R.id.bookingtb);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookingTable.class);
                startActivity(intent);
            }
        });





        ImageButton btwedding = (ImageButton) findViewById(R.id.weddingmain);
        btwedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EventPlanners.class);
                startActivity(i);
            }
        });
        ImageButton btgifts = (ImageButton) findViewById(R.id.gifts_main);
        btgifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GiftsActivity.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ivClub = (ImageView) findViewById(R.id.eventiv);
        ivClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpcomeingEvent.class);
                startActivity(intent);
            }
        });


    }






    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler() {
                public void postDelayed(Runnable runnable, int i) {
                }

                @Override
                public void publish(LogRecord logRecord) {

                }

                @Override
                public void flush() {

                }

                @Override
                public void close() throws SecurityException {

                }
            }.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.signin) {
            Intent intent = new Intent(MainActivity.this, PhoneAuthActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.offers) {
            Intent intent = new Intent(MainActivity.this,Offers.class);
            startActivity(intent);

        } else if (id == R.id.gifts) {
            Intent intent = new Intent(MainActivity.this, GiftsActivity.class);
            startActivity(intent);


        } else if (id == R.id.orderfood) {
            Intent intent = new Intent(MainActivity.this,hungarynow.class);
            startActivity(intent);

        } else if (id == R.id.evnt) {
            Intent intent = new Intent(MainActivity.this,EventPlanners.class);
            startActivity(intent);

        } else if (id == R.id.invite) {
            Intent si = new Intent(Intent.ACTION_SEND);
            si.setType("text/plain");
            si.putExtra(Intent.EXTRA_TEXT,"Discover favourite Restaurant for order food ,Book table, Parties Planning etc."+"\n"+"https://play.google.com/store/apps/details?id=com.evnto.indotech.evnto");
            si.setPackage("com.whatsapp");
            startActivity(si);
        } else if (id == R.id.ratereview) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=com.evnto.indotech.evnto"));
        
            startActivity(intent);




        } else if (id == R.id.facebook) {

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.facebook.com, com.facebook.katana"));
                startActivity(intent);
            } catch(Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/EvntO2017")));
            }


        } else if (id == R.id.email) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("email"));
            String[] s = {"ceoindotech@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL,s);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Give a Subject");
            intent.putExtra(Intent.EXTRA_TEXT,"");
            intent.setType("message/rfc822");
            Intent chooser = Intent.createChooser(intent,"Launch Email");
            startActivity(chooser);

        } else if (id == R.id.callnow) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            callIntent.setData(Uri.parse("tel://8305155116"));
            startActivity(callIntent);

        } else if (id == R.id.feedback) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("email"));
            String[] s = {"ceoindotech@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL,s);
            intent.putExtra(Intent.EXTRA_SUBJECT,"feedback");
            intent.putExtra(Intent.EXTRA_TEXT,"Give us feedback");
            intent.setType("message/rfc822");
            Intent chooser = Intent.createChooser(intent,"Launch Email");
            startActivity(chooser);


        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onImageLoaded(Bitmap bitmap) {

    }

    @Override
    public void onError() {

    }


//    public class MyTimerTask extends TimerTask{
//
//        @Override
//        public void run() {
//            MainActivity.this.runOnUiThread(new Runnable(){
//
//                @Override
//                public void run() {
//                    if (viewPager.getCurrentItem() == 0){
//                        viewPager.setCurrentItem(1);
//                    }else if (viewPager.getCurrentItem() == 1){
//                        viewPager.setCurrentItem(2);
//                    }else if(viewPager.getCurrentItem() == 2){
//                        viewPager.setCurrentItem(3);
//                    }else if (viewPager.getCurrentItem() == 3){
//                        viewPager.setCurrentItem(4);
//                    }else {
//                        viewPager.setCurrentItem(0);
//                    }
//                }
//            });
//        }
//    }

}

