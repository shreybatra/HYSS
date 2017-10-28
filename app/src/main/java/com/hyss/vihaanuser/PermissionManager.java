package com.hyss.vihaanuser;

/**
 * Created by yash on 27/10/17.
 */
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

public class PermissionManager {

    public static final String TAG = "PermissionManager";
    static ArrayList<OnPermissionResultListener> listenerArrayList = new ArrayList<>();

    interface OnPermissionResultListener{
        void onGranted(String permission);
        void onDenied(String permission);
    }
    static void askForPermission(Activity act, String[] permissions,OnPermissionResultListener oprl){
        int reqCode = listenerArrayList.size();
        listenerArrayList.add(reqCode,oprl);

        ActivityCompat.requestPermissions(act,permissions,reqCode);
    }

    static void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        try {
            OnPermissionResultListener oprl = listenerArrayList.get(requestCode);

            for (int i = 0; i < permissions.length; i++) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    oprl.onGranted(permissions[i]);
                }
                else {
                    oprl.onDenied(permissions[i]);
                }
            }

        } catch (IndexOutOfBoundsException ioobe) {
            Log.d(TAG, "onRequestPermissionsResult: No Such listener registered");
        }


    }


}
