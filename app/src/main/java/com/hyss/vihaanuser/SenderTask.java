package com.hyss.vihaanuser;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.hyss.vihaanuser.Pojos.Content;
import com.hyss.vihaanuser.Pojos.Sos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yash on 28/10/17.
 */

public class SenderTask extends AsyncTask<String,Void,Void> {
    public static final String TAG = "MYAPP";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected Void doInBackground(String... strings) {


        mAuth=FirebaseAuth.getInstance();

        Sos sos = new Sos(mAuth.getCurrentUser().getEmail().toString(),"123");
        if (strings[1]=="Fire")
            sos.type = "Fire";
        else if (strings[1] == "Earthquake")
            sos.type = "Earthquake";
        else if (strings[1] == "Flood")
            sos.type = "Flood";
        sos.latitude = Double.parseDouble(strings[2]);
        sos.longitude = Double.parseDouble(strings[3]);

        Log.d(TAG, "doInBackground: USer:- "+mAuth.getCurrentUser().getEmail().toString());
        /*

            JSONObject jsonObject = new JSONObject();

            try{

                jsonObject.put("type", sos.getType());
                jsonObject.put("latitude", sos.getLatitude());
                jsonObject.put("longitude", sos.getLongitude());
                jsonObject.put("username", sos.getUsername());
                jsonObject.put("password", sos.getPassword());
                jsonObject.put("time", sos.getTime());
                Log.d(TAG, "doInBackground: "+"data added" +jsonObject);
            }
            catch (JSONException e)
            {
                Log.d(TAG, "doInBackground: "+"data not set"+e.toString());

            }*/

            // Send POST data request
        try{
                URL url = new URL("https://hyss.herokuapp.com/sos_add");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json");
                conn.setDoOutput(true);
            Content content = new Content();
            content.createData(sos.getType(),sos.getLatitude().toString(),sos.getLongitude().toString(),sos.getTime().toString(),sos.getUsername(),sos.getPassword());


            ObjectMapper mapper= new ObjectMapper();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            mapper.writeValue(wr,content.getData());
            wr.flush();
            wr.close();
            Log.d(TAG, "doInBackground: "+conn.getResponseCode());
            Log.d(TAG, "doInBackground: "+"data send"+ content.getData());

        }
            catch (Exception e)
            {
                Log.d(TAG, "doInBackground: "+"data nt send"+e.toString());
            }


            return null;
        }


}




