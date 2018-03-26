package com.evnto.indotech.evnto.InsertOrder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by imakash on 9/5/2017.
 */
public class Sender extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText nameTxt;
    EditText addressTxt;
    EditText numberTxt;
    EditText productTxt;
    EditText quantityTxt;

    InsertList insertList;

    ProgressDialog pd;

    public Sender(Context c, String urlAddress, EditText nameTxt, EditText addressTxt, EditText numberTxt, EditText productTxt,EditText quantityTxt) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.nameTxt = nameTxt;
        this.addressTxt = addressTxt;
        this.numberTxt = numberTxt;
        this.productTxt = productTxt;
        this.quantityTxt = quantityTxt;

        insertList=new InsertList();
        insertList.setName(nameTxt.getText().toString());
        insertList.setAdd(addressTxt.getText().toString());
        insertList.setNumber(numberTxt.getText().toString());
        insertList.setProduct(productTxt.getText().toString());
        insertList.setQuantity(quantityTxt.getText().toString());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending...Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();

        if(s==null)
        {
            Toast.makeText(c,"Unsuccessful,Null returned",Toast.LENGTH_SHORT).show();
        }else
        {
            if(s=="Bad Response")
            {
                Toast.makeText(c,"Unsuccessful,Bad Response returned",Toast.LENGTH_SHORT).show();

            }else
            {

                //CLEAR UI
                nameTxt.setText("");
                addressTxt.setText("");
                numberTxt.setText("");
                productTxt.setText("");
                quantityTxt.setText("");
            }
        }


    }

    private String send()
    {
        HttpURLConnection con=InsertConnector.connect(urlAddress);
        if(con==null)
        {
            return null;
        }

        try {

            OutputStream os=con.getOutputStream();

            //WRITE
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(new DataPackager(insertList).packData());

            bw.flush();
            //RELEASE
            bw.close();
            os.close();

            //SUCCESS OR NOT??
            int responseCode=con.getResponseCode();
            if(responseCode==con.HTTP_OK)
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response=new StringBuffer();

                String line;
                while ((line=br.readLine()) != null)
                {
                    response.append(line);
                }

                br.close();
                return response.toString();
            }else {
                return "Bad Response";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
