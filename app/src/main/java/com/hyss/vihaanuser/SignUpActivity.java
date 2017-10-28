package com.hyss.vihaanuser;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    int verified=0;
    Button btnCreate,btnVerify;
    EditText etUsername,etPassword,etConfirmPassword,etMobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnVerify = (Button) findViewById(R.id.btnVerify);

        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                String confirmPass = etConfirmPassword.getText().toString();
                final String mobileNumber = etMobileNumber.getText().toString();

                if (etUsername.getText().toString().trim().equals(""))
                {
                    etUsername.setError( "User name is required!" );
                }
                 if (pass.length()<5)
                {
                   etPassword.setError("Incorrect Password Format! ");
                 }
                if (!confirmPass.equals(pass))
                {
                    etPassword.setError(" Password doesn't match! ");
                }
                if (mobileNumber.length()<10)
                {
                    etMobileNumber.setError("Incorrect Mobile Number");
                }
                /*PermissionManager.askForPermission(this,
                        new String[]{
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.RECEIVE_SMS
                        }, new PermissionManager.OnPermissionResultListener() {
                            @Override
                            public void onGranted(String permission) {
                                if (permission.equals(Manifest.permission.SEND_SMS)){

                                    verify.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String msg= etMsg.getText().toString();
                                            String number = mobileNumber;
                                            try{
                                                String SENT = "Message sent";
                                                String DELIEVERED = "Message delievered";

                                                PendingIntent sentPI = PendingIntent.getBroadcast(MainActivity.this,0,new Intent(SENT),0);
                                                PendingIntent delieveredPI = PendingIntent.getBroadcast(MainActivity.this,0, new Intent(DELIEVERED),0);

                                                SmsManager smsManager = SmsManager.getDefault();
                                                smsManager.sendTextMessage(number,null,msg,sentPI,delieveredPI);

                                                Toast.makeText(SignUpActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                                            }catch (Exception e){
                                                Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onDenied(String permission) {
                                Toast.makeText(SignUpActivity.this, "Need Permission to do this.", Toast.LENGTH_SHORT).show();
                            }
                        });
*/

            }
        });

    }
}
