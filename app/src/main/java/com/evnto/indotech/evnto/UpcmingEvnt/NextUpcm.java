package com.evnto.indotech.evnto.UpcmingEvnt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evnto.indotech.evnto.R;
import com.evnto.indotech.evnto.RegistrTickets.SenderR;

public class NextUpcm extends AppCompatActivity {
    private TextView name, desc;
    EditText nametv, addressTxt, numbertv, evnttv, ticketstv;
    String urlAddress = "https://ceoindotech.000webhostapp.com/RgistrEntry.php";
    ImageView iv;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_upcm);


        nametv = (EditText) findViewById(R.id.editTextName);
        numbertv = (EditText) findViewById(R.id.editTextNumber);
        evnttv = (EditText) findViewById(R.id.editTextevntname);
        ticketstv = (EditText) findViewById(R.id.editTextTickets);
        saveBtn = (Button) findViewById(R.id.button);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SenderR s = new SenderR(NextUpcm.this, urlAddress, nametv, numbertv, evnttv, ticketstv);
                s.execute();
            }
        });


        initView();
    }

    private void initView() {

        name = (TextView) findViewById(R.id.upname);
        desc = (TextView) findViewById(R.id.updesc);
        iv = (ImageView) findViewById(R.id.ivupcm);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            evnttv.setText(bundle.getString("name"));
            desc.setText(bundle.getString("desc"));
            String imageUrl = bundle.getString("imageurl");

            Glide.with(NextUpcm.this)

                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv);

        }

    }
}
