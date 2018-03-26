package com.evnto.indotech.evnto;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evnto.indotech.evnto.InsertOrder.Insert;

/**
 * Created by JD on 8/22/2017.
 */

public class OnItemClic extends AppCompatActivity {

    private TextView id, name, desc, minOrder, menu;
String menus;
    CheckBox[] checkBoxes;

    ImageView iv;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);

        Button button2 = (Button)findViewById(R.id.chatus);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("918305155116")+"@s.whatsapp.net");
                //phone number without "+" prefix
                startActivity(sendIntent);


            }
        });
        Button btcallorder = (Button) findViewById(R.id.callnow);
        btcallorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnItemClic.this, Insert.class);
                startActivity(intent);

            }
        });


        initView();
    }

    private void initView() {
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.txtname);
        desc = (TextView) findViewById(R.id.txtdesc);
        minOrder = (TextView) findViewById(R.id.minOrder);
        menu = (TextView) findViewById(R.id.menu);
        iv = (ImageView) findViewById(R.id.iv);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id.setText(bundle.getString("id"));
            name.setText(bundle.getString("name"));
            desc.setText(bundle.getString("desc"));
            minOrder.setText(bundle.getString("minOrder"));
            menu.setText(bundle.getString("menu"));
          /*  checkBoxes = new CheckBox[items.length];
            for(int i=0;i<items.length;i++)
            {
                CheckBox c= new CheckBox(this);
                c.setText(items[i]);
                checkBoxes[i]=c;
            }*/
           String imageUrl= bundle.getString("imageurl");

            Glide.with(OnItemClic.this)

                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv);

        }

    }
}
