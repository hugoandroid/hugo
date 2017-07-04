package test.hugo.ui;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import test.hugo.R;
import test.hugo.photoView.OnViewTapListener;
import test.hugo.photoView.PhotoView;


@TargetApi(21)
public class ImgShowActivity21 extends AppCompatActivity {
    private ViewPager vp_data;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTransiton();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgshow_21);
//        setExitSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
//                findViewById(R.id.back).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
//            }
//        });
        imgUrl = "http://cdn.oss.gaokaopai.com//mobile/face/post/2016/04/21/2016042117460148181x.jpg";

        vp_data = (ViewPager) findViewById(R.id.vp_data);
        vp_data.setAdapter(new MyAdapter(this));
        getWindow().getDecorView().setVisibility(View.VISIBLE);
    }

//    private void setTransiton() {
//        ChangeColor changeColor = new ChangeColor();
//        changeColor.setDuration(500);
//        getWindow().setSharedElementReturnTransition(changeColor);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
    class MyAdapter extends PagerAdapter implements OnViewTapListener {
        private ImgShowActivity21 mActivity;

        public MyAdapter(ImgShowActivity21 activity) {
            mActivity = activity;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            PhotoView imageView = new PhotoView(container.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
            if(position == 0)
               imageView.setTransitionName("share");
            Glide.with(mActivity).load(imgUrl).into(imageView);
            imageView.setOnViewTapListener(this);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onViewTap(View view, float x, float y) {
            mActivity.onBackPressed();
        }
    }
}
