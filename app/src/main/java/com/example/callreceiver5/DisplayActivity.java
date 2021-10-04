package com.example.callreceiver5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telecom.TelecomManager;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//import static android.Manifest.permission.CALL_PHONE;
//import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
//import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
//import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;



public class DisplayActivity extends AppCompatActivity {
    TextView callinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //リスナー設定
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear Preferences
                clearHistories(view);


            }
        });

        TextView tv = (TextView) this.findViewById(R.id.callinfo);
        SharedPreferences pref = getSharedPreferences("CallReceiver", MODE_PRIVATE);
        tv.setText(pref.getString("text","nothing..Button"));


    }

    PhoneStateListener mListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String callNumber) {
            //Log.d(TAG, ":" + state+"-PhoneNumber:"+callNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:      //待ち受け（終了時）
                    Toast.makeText(DisplayActivity.this, "通話終了\nCALL_STATE_IDLE", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:   //着信*
                    if(callNumber==null){
                        callNumber="";
                    }
                    Toast.makeText(DisplayActivity.this, "着信中\nCALL_STATE_RINGING: " + callNumber, Toast.LENGTH_SHORT).show();
                    callinfo.setText("着信："+callNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:   //通話
                    Toast.makeText(DisplayActivity.this, "通話中\nCALL_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = (TextView) this.findViewById(R.id.callinfo);
        SharedPreferences pref = getSharedPreferences("CallReceiver", MODE_PRIVATE);
        tv.setText(pref.getString("text", "nothing..何もない.."));

   }


    private void clearHistories(View view){
        getSharedPreferences("CallReceiver", MODE_PRIVATE).edit().clear().commit();
        Snackbar.make(view, "histories cleared.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        TextView tv = (TextView) this.findViewById(R.id.callinfo);
        SharedPreferences pref = getSharedPreferences("CallReceiver", MODE_PRIVATE);
        tv.setText(pref.getString("text", "nothing..clearHistories"));
    }



    /*
     private void offerReplacingDefaultDialer() {
        TelecomManager telecomManager = (TelecomManager) getSystemService(TELECOM_SERVICE);


        if (!getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
            Intent intent = new Intent(ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        }
    }
    */

    //@Override
    //public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //    if (requestCode == REQUEST_PERMISSION && ArraysKt.contains(grantResults, PERMISSION_GRANTED)) {
    //        makeCall();
    //    }
    //}




}
