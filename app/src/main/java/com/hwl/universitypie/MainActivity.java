package com.hwl.universitypie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        library = GestureLibraries.fromRawResource(this, R.raw.gestures);
//        library.load();
        setContentView(R.layout.activity_main2);
        VideoTopBar bar = new VideoTopBar(this);
        bar.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        ViewGroup ll_root = (ViewGroup) findViewById(R.id.ll_root);
        ll_root.addView(bar, 0);
        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int height = 0;
//                for (int i =0;i< ll_root.getChildCount();i++){
//                    height+=ll_root.getChildAt(i).getHeight();
//                }
//                Bitmap b = Bitmap.createBitmap(ll_root.getWidth(),height, Bitmap.Config.ARGB_4444);
//                ll_root.draw(new Canvas(b));
//                try {
//                    b.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(new File(Environment.getExternalStorageDirectory(),"dasdada.png")));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                MyIntentService.startActionFoo(MainActivity.this,"","");
                startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
            }
        });
    }
}