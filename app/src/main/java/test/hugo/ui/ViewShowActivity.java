package test.hugo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import test.hugo.R;
import test.hugo.VideoTopBar;
import test.hugo.VideoTopBar2;
import test.hugo.VideoTopBar3;

public class ViewShowActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoTopBar mVtb1;
    private VideoTopBar2 mVtb2;
    private VideoTopBar3 mVtb3;
    private TextView mTv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_show);
        mVtb1 = (VideoTopBar) findViewById(R.id.vtb1);
        mVtb2 = (VideoTopBar2) findViewById(R.id.vtb2);
        mVtb3 = (VideoTopBar3) findViewById(R.id.vtb3);
        mTv_show = (TextView) findViewById(R.id.tv_show);
        mVtb1.setOnClickListener(this);
        mVtb2.setOnClickListener(this);
        mVtb3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vtb1:
                mTv_show.setText(mVtb1.settext());
                break;
            case R.id.vtb2:
                mTv_show.setText(mVtb2.settext());
                break;
            case R.id.vtb3:
                mTv_show.setText(mVtb3.settext());
                break;
        }
    }
}
