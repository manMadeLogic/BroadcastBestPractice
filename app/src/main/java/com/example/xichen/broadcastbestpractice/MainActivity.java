package com.example.xichen.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter = new IntentFilter();
    private LocalReceiver localReceiver = new LocalReceiver();
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.button1);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcastbestpractice.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter.addAction("com.example.broadcastbestpractice.LOCAL_BROADCAST");
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        intentFilter.addAction("com.example.broadcastbestpractice.MY_BROADCAST");
        localBroadcastManager.registerReceiver(myBroadcastReceiver, intentFilter);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcastbestpractice.MY_BROADCAST");
                sendOrderedBroadcast(intent, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
        localBroadcastManager.unregisterReceiver(myBroadcastReceiver);
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}
