package com.hwl.universitypie.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hwl.universitypie.R;

public class IntentServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOCAL_ACTION = "abc";
    private Button bt;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        initViews();
        bt.setOnClickListener(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter(LOCAL_ACTION));

    }

    private void initViews() {
        bt = (Button) findViewById(R.id.bt);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onClick(View v) {
        TestIntentService.startActionFoo(this,LOCAL_ACTION,null);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int index = intent.getIntExtra("index", 0);
            tv.setText(""+index);
        }
    };

}
