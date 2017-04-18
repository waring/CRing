package com.example.glisseo.wifind;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WifiManager wifimanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifimanager = (WifiManager) getSystemService(WIFI_SERVICE);
        String[] LIST_MENU = { wifimanager.getConnectionInfo().getSSID(), wifimanager.getConnectionInfo().getMacAddress(),wifimanager.getConnectionInfo().getBSSID()};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);
        ListView listView = (ListView)findViewById(R.id.listviwe2);
        listView.setAdapter(adapter);

    }

}
