package com.evnto.indotech.evnto.RegistrTickets;

/**
 * Created by imakash on 9/8/2017.
 */

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
 * Created by imakash on 9/8/2017.
 */
public class SenderR extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText nameTxt,numberTxt,evntnameTxt,ticketsTxt;

    RgstrList rgstrList;

    ProgressDialog pd;

    public SenderR(Context c, String urlAddress, EditText nameTxt, EditText numberTxt, EditText evntnameTxt, EditText ticketsTxt) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.nameTxt = nameTxt;
        this.numberTxt = numberTxt;
        this.evntnameTxt = evntnameTxt;
        this.ticketsTxt = ticketsTxt;

        rgstrList=new RgstrList();
        rgstrList.setName(nameTxt.getText().toString());
        rgstrList.setNumber(numberTxt.getText().toString());
        rgstrList.setEvntName(evntnameTxt.getText().toString());
        rgstrList.setTickets(ticketsTxt.getText().toString());
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
                Toast.makeText(c,"Successfully Submitted",Toast.LENGTH_SHORT).show();

                //CLEAR UI
                nameTxt.setText("");

                numberTxt.setText("");
                evntnameTxt.setText("");
                ticketsTxt.setText("");
            }
        }


    }

    private String send()
    {
        HttpURLConnection con= RgstrConnector.connect(urlAddress);
        if(con==null)
        {
            return null;
        }

        try {

            OutputStream os=con.getOutputStream();

            //WRITE
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(new DataRegistrTicket(rgstrList).packData());

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
