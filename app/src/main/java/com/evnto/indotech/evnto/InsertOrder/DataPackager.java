package com.evnto.indotech.evnto.InsertOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by imakash on 9/5/2017.
 */

public class DataPackager {

    InsertList insertList;

    public DataPackager(InsertList insertList) {
        this.insertList = insertList;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer sb=new StringBuffer();

        try {
            jo.put("name",insertList.getName());
            jo.put("address",insertList.getAdd());
            jo.put("contactnumber",insertList.getNumber());
            jo.put("productname",insertList.getProduct());
            jo.put("quantity",insertList.getQuantity());

            Boolean firstvalue=true;
            Iterator it=jo.keys();

            do {
                String key=it.next().toString();
                String value=jo.get(key).toString();

                if(firstvalue)
                {
                    firstvalue=false;
                }else
                {
                    sb.append("&");
                }

                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));

            }while (it.hasNext());

            return sb.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}