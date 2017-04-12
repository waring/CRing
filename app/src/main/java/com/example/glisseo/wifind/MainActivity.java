package com.example.glisseo.wifind;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update();
    }

    private void update(){
        /*
        lv = (ListView)findViewById(R.id.myList);
        cr = getContentResolver();
        Cursor c = cr.query(Uri.parse(URI), null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.custum_layout,
                c, new String[] {"_id","name", "score"}, new int[] {R.id.textView6, R.id.textView9, R.id.textView8});
        lv.setAdapter(adapter);
        */
    }
}
