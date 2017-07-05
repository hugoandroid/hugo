package test.hugo.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import test.hugo.AnimView;
import test.hugo.CallBack;
import test.hugo.R;
import test.hugo.VolleyUtil;
import test.hugo.swipe.SlidToFinishActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        library = GestureLibraries.fromRawResource(this, R.raw.gestures);
//        library.load();
        setContentView(R.layout.activity_main2);
        findViewById(R.id.tv_viewpager).setOnClickListener(this);
        findViewById(R.id.tv_time_cost).setOnClickListener(this);
        findViewById(R.id.tv_view_show).setOnClickListener(this);
        findViewById(R.id.tv_view_inflate).setOnClickListener(this);
        findViewById(R.id.tv_view_share).setOnClickListener(this);
        findViewById(R.id.tv_date_select).setOnClickListener(this);
        findViewById(R.id.tv_intent_service).setOnClickListener(this);
        findViewById(R.id.tv_recycler).setOnClickListener(this);
        findViewById(R.id.tv_share_activity).setOnClickListener(this);
    }
    public void onc(View view){
        startActivity(new Intent(this, SlidToFinishActivity.class));
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
    }
    public void oncli(View view){
        ((AnimView)view).addAnim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_share_activity:
                startActivity(new Intent(this,BasicActivity.class));
                break;
            case R.id.tv_intent_service:
                startActivity(new Intent(this,IntentServiceActivity.class));
                break;
            case R.id.tv_recycler:
                startActivity(new Intent(this,RecyclerViewActivity.class));
                break;
            case R.id.tv_date_select:
                showDateDialog();
                Snackbar.make(view,"注意三星手机5.X 系统的日期选择器崩溃",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.tv_view_share:
                startActivity(new Intent(this, ShareViewActivity.class));
                break;
            case R.id.tv_view_inflate:
                startActivity(new Intent(this, ViewInflateActivity.class));
                break;
            case R.id.tv_viewpager:
                startActivity(new Intent(this, ViewpagerActivity.class));
                break;
            case R.id.tv_view_show:
                startActivity(new Intent(this, ViewShowActivity.class));
                break;
            case R.id.tv_time_cost:
                final long l = System.currentTimeMillis();
                VolleyUtil.getInstance().get("http://test.gaokaopai.com/edu-lesson-index?uid=471135&gkptoken=1dddd867fb8ac5315551663f7d6ccf70&lessonid=80", new CallBack() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,"耗时"+(System.currentTimeMillis() - l),Toast.LENGTH_LONG).show();
                    }
                });
                break;
            default:break;
        }
    }

    private void showDateDialog() {
        final Context context = MainActivity.this;
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (view.isShown()) { // 这里判断
                    Toast.makeText(context, String.format(Locale.CHINA, "(y, m, d) = (%d, %d, %d)", year, month, dayOfMonth), Toast.LENGTH_SHORT).show();
                }
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener, y, m, d);
        datePickerDialog.show();
    }
}