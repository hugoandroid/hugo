package test.hugo.ui;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import test.hugo.R;

import java.util.Random;

public class ShareViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        RecyclerView rv_data = (RecyclerView) findViewById(R.id.rv_data);
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        rv_data.setAdapter(new MyAdapter(this));
    }
    static class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {
        private ShareViewActivity mActivity;
        public MyAdapter(ShareViewActivity shareViewActivity) {
            mActivity = shareViewActivity;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_recyclerview, parent, false);
            return new Holder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ((Holder)holder).iv_head.setImageResource(R.mipmap.bg_zuowen_03);
            String imgUrl = "http://cdn.oss.gaokaopai.com//mobile/face/post/2016/04/21/2016042117460148181x.jpg";
            Glide.with(mActivity).load(imgUrl).into(((Holder)holder).iv_head);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            View viewById = v.findViewById(R.id.iv_head);
            boolean sdk_support = Build.VERSION.SDK_INT > 20;

            if(false){
                mActivity.startActivity(new Intent(mActivity, ImgShowActivity21.class),
                        ActivityOptions.makeSceneTransitionAnimation(mActivity, viewById, viewById.getTransitionName()).toBundle());
            }else {
                Intent intent = new Intent(mActivity, ImgShowActivity.class);
                intent.putExtra("loc", /* start values */ captureValues(viewById));

                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(0, 0);
            }
        }

        private Bundle captureValues(@NonNull View view) {
            Bundle b = new Bundle();
            int[] screenLocation = new int[2];
            view.getLocationOnScreen(screenLocation);
            b.putInt(ImgShowActivity.PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
            b.putInt(ImgShowActivity.PROPNAME_SCREENLOCATION_TOP, screenLocation[1]);
            b.putInt(ImgShowActivity.PROPNAME_WIDTH, view.getWidth());
            b.putInt(ImgShowActivity.PROPNAME_HEIGHT, view.getHeight());
            return b;
        }
    }

    static class Holder extends RecyclerView.ViewHolder{

        private ImageView iv_head;

        public Holder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
        }
    }
}
