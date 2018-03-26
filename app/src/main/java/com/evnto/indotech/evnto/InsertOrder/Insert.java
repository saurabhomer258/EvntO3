package com.evnto.indotech.evnto.InsertOrder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evnto.indotech.evnto.R;


public class Insert extends AppCompatActivity {

SharedPreferences sp;
    SharedPreferences.Editor spe;
    String urlAddress="https://ceoindotech.000webhostapp.com/insert-db.php";
    EditText nameTxt,addressTxt,numberTxt,productTxt,quantityTxt;
    Button saveBtn,calln;
    String name,address, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        sp=getSharedPreferences("my_data",MODE_PRIVATE);
        spe=sp.edit();

        String name=sp.getString("name",null);
        String number=sp.getString("number",null);
        String address=sp.getString("address",null);
        nameTxt= (EditText) findViewById(R.id.editTextName);
        addressTxt= (EditText) findViewById(R.id.editTextAddress);
        numberTxt= (EditText) findViewById(R.id.editTextNumber);
        productTxt= (EditText) findViewById(R.id.editTextProduct);
        quantityTxt= (EditText) findViewById(R.id.editTextQuantity);
        saveBtn= (Button) findViewById(R.id.button);
        calln = (Button)findViewById(R.id.buttoncal);
        calln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                callIntent.setData(Uri.parse("tel://8305155116"));
                startActivity(callIntent);
            }
        });



     nameTxt.setText(name);
        addressTxt.setText(address);
        numberTxt.setText(number);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameTxt.getText().toString().trim();
                String address=addressTxt.getText().toString().trim();
                String number=numberTxt.getText().toString().trim();
                String product=productTxt.getText().toString().trim();
                String quantity=quantityTxt.getText().toString().trim();
                Sender s=new Sender(Insert.this,urlAddress,nameTxt,addressTxt,numberTxt,productTxt,quantityTxt);
                spe.putString("name",name);
                spe.putString("address",address);
                spe.putString("number",number);
                spe.commit();
                s.execute();
                ClickMe();


            }
        });

    }

    private void ClickMe() {

        intialize();
        if (!validate()){
            Toast.makeText(this, "Please Enter your Details", Toast.LENGTH_SHORT).show();
        }else {
            OnSignupSuccess();
        }
    }
    public void OnSignupSuccess(){

    }
    public boolean validate(){
        boolean Valid = true;
        if (name.isEmpty()||name.length()>32){
            nameTxt.setError("Please Enter Name");
            Valid = false;
        }else if (address.isEmpty()){
            addressTxt.setError("Please Enter Address");
            Valid = false;
        }else if (number.isEmpty()||number.length()<=9) {
            numberTxt.setError("Please Enter Valid No");
            Valid = false;
        }else if (number.isEmpty()||number.length()>10) {
            numberTxt.setError("Please Enter Valid No");
            Valid = false;
        }else {
            Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
        }
        return Valid;
    }
    public void intialize(){
        name = nameTxt.getText().toString().trim();
        number = numberTxt.getText().toString().trim();
        address = addressTxt.getText().toString().trim();
    }

}
