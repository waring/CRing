package com.example.glisseo.wifind;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.util.List;

public class MainActivity extends ListActivity {
    WifiManager wifimanager;

    /*
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
    */

    private WifiManager mWifiManager;
    private List<ScanResult> mScanResults;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWifiManager = (WifiManager)getSystemService(WIFI_SERVICE);

        setListAdapter(mListAdapter);

        getListView().setOnItemClickListener(mItemOnClick);
    }

    @Override
    public void onResume() {
        super.onResume();
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(mReceiver, filter);
        mWifiManager.startScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                mScanResults = mWifiManager.getScanResults();
                mListAdapter.notifyDataSetChanged();

                mWifiManager.startScan();
            }

        }
    };

    private BaseAdapter mListAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null || !(convertView instanceof TwoLineListItem)) {
                convertView = View.inflate(getApplicationContext(),
                        android.R.layout.simple_list_item_2, null);
            }

            final ScanResult result = mScanResults.get(position);
            ((TwoLineListItem)convertView).getText1().setText(result.SSID);
            ((TwoLineListItem)convertView).getText2().setText(
                    String.format("%s  %d", result.BSSID, result.level)
            );
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return mScanResults == null ? 0 : mScanResults.size();
        }
    };

    private AdapterView.OnItemClickListener mItemOnClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            final ScanResult result = mScanResults.get(position);
            launchWifiConnecter(MainActivity.this, result);
        }
    };

    /**
     * Try to launch Wifi Connecter with {@link #hostspot}. Prompt user to download if Wifi Connecter is not installed.
     * @param activity
     * @param hotspot
     */
    private static void launchWifiConnecter(final Activity activity, final ScanResult hotspot) {
        final Intent intent = new Intent("com.farproc.wifi.connecter.action.CONNECT_OR_EDIT");
        intent.putExtra("com.farproc.wifi.connecter.extra.HOTSPOT", hotspot);
        try {
            activity.startActivity(intent);
        } catch(ActivityNotFoundException e) {
            // Wifi Connecter Library is not installed.
            Toast.makeText(activity, "Wifi Connecter is not installed.", Toast.LENGTH_LONG).show();
            downloadWifiConnecter(activity);
        }
    }

    private static void downloadWifiConnecter(final Activity activity) {
        Intent downloadIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.farproc.wifi.connecter"));
        try {
            activity.startActivity(downloadIntent);
            Toast.makeText(activity, "Please install this app.", Toast.LENGTH_LONG).show();
        } catch (ActivityNotFoundException e) {
            // Market app is not available in this device.
            // Show download page of this project.
            try {
                downloadIntent.setData(Uri.parse("http://code.google.com/p/android-wifi-connecter/downloads/list"));
                activity.startActivity(downloadIntent);
                Toast.makeText(activity, "Please download the apk and install it manully.", Toast.LENGTH_LONG).show();
            } catch  (ActivityNotFoundException e2) {
                // Even the Browser app is not available!!!!!
                // Show a error message!
                Toast.makeText(activity, "Fatel error! No web browser app in your device!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
