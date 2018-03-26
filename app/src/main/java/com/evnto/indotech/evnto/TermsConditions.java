package com.evnto.indotech.evnto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TermsConditions extends AppCompatActivity {
    TextView tv;

 String serverurl="https://ceoindotech.000webhostapp.com/terms.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        tv = (TextView)findViewById(R.id.termscondition);



        final RequestQueue requestQueue = Volley.newRequestQueue(TermsConditions.this);
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
