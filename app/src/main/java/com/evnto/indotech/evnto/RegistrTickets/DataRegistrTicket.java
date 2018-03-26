package com.evnto.indotech.evnto.RegistrTickets;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by imakash on 9/8/2017.
 */


public class DataRegistrTicket {

    RgstrList rgstrList;

    public DataRegistrTicket(RgstrList rgstrList) {
        this.rgstrList = rgstrList;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer sb=new StringBuffer();

        try {
            jo.put("name",rgstrList.getName());
            jo.put("number",rgstrList.getNumber());
            jo.put("evntname",rgstrList.getEvntName());

            jo.put("tickets",rgstrList.getTickets());

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