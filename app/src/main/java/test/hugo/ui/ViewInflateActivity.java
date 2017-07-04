package test.hugo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import test.hugo.R;

public class ViewInflateActivity extends AppCompatActivity {

    private TextView tv_1;
    private LinearLayout ll_root;
    private TextView tv_2;
    private LinearLayout ll_root2;
    private StringBuilder mStringBuilder;
    private StringBuilder mStringBuilder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inflate);
        mStringBuilder = new StringBuilder();
        mStringBuilder2 = new StringBuilder();
        initViews();
    }

    private void initViews() {
        tv_1 = (TextView) findViewById(R.id.tv_1);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        ll_root2 = (LinearLayout) findViewById(R.id.ll_root2);
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_demo, ll_root, true);
//        ll_root.addView(inflate);
        View tv_demo = inflate.findViewById(R.id.tv_demo);
        tv_1.setText(getFather(tv_demo));
        View inflate2 = View.inflate(this,R.layout.item_demo, null);
        ll_root2.addView(inflate2);
        View tv_demo2 = inflate2.findViewById(R.id.tv_demo);
        tv_2.setText(getFather2(tv_demo2));
    }

    private String getFather(View view) {
        ViewParent parent = view.getParent();
        if(parent instanceof ScrollView){
            mStringBuilder.append("ScrollView");
        }else{
            mStringBuilder.append(parent.toString()+"  \n");
            getFather((View) parent);
        }
        return mStringBuilder.toString();
    }
    private String getFather2(View view) {
        ViewParent parent = view.getParent();
        if(parent instanceof ScrollView){
            mStringBuilder2.append("ScrollView");
        }else{
            mStringBuilder2.append(parent.toString()+"  \n");
            getFather2((View) parent);
        }
        return mStringBuilder2.toString();
    }
}
