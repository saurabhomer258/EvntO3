package com.evnto.indotech.evnto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class About extends AppCompatActivity {

    String serverurl="https://ceoindotech.000webhostapp.com/about.php";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tv=(TextView)findViewById(R.id.tvvv);


        final RequestQueue requestQueue = Volley.newRequestQueue(About.this);
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
    }
}
