package com.example.batterylow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//private Ringtone ringtone;
    private static final int REQUEST_CALL=1;



    private final BroadcastReceiver Batterynot=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //get battery level
            int level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            //check if the battery is low
            if (level <=20){
                makeCall();
            }

        }
    };

    private void makeCall(){
        String number="9545089825";
        //if call permission is not granted;hence we give permission
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            String dial="tel:"+number;
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);

        }
        finish();
    }

//if call permission is already granted at the first place
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(this.Batterynot,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

}