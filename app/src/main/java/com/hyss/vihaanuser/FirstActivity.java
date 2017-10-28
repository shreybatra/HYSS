package com.hyss.vihaanuser;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hyss.vihaanuser.Pojos.Sos;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.*;

import static android.R.attr.x;
import static android.R.attr.y;

public class FirstActivity extends AppCompatActivity {
    public static final String TAG = "FirstA";
    LocationManager locationManager;
    LocationListener locationListener;

    Button ibFire,ibEarthquake,ibFlood;
    String url = "";
    String lat="",lon="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ibFire = (Button) findViewById(R.id.ibFire);
        ibEarthquake = (Button) findViewById(R.id.ibEarthquake);
        ibFlood = (Button) findViewById(R.id.ibFlood);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

      /*  SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new ShakeListener(),accelSensor,SensorManager.SENSOR_DELAY_GAME);*/
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                Log.d(TAG, "onLocationChanged: "+location.getLatitude());

                lat = location.getLatitude()+"";
                lon = location.getLongitude()+"";


                Toast.makeText(FirstActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    20000, 100, locationListener);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "Ask for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.i("TAG", "Permission given");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        20000, 100, locationListener);
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

            }
        }



        ibFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SenderTask senderTask = new SenderTask();
                senderTask.execute(url,"Fire",lat,lon);
                //----------------------------------------------------------
            }
        });

       ibEarthquake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderTask senderTask = new SenderTask();
                senderTask.execute(url,"Earthquake",lat,lon);
            }
        });
        ibFlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderTask senderTask = new SenderTask();
                senderTask.execute(url,"Flood",lat,lon);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("TAG","sabse -Upar");
        Log.i("TAG","LEN:" +grantResults);
        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }

}
