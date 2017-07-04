package test.hugo.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import test.hugo.R;
import test.hugo.Second;

import java.util.Random;

public class ViewpagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ViewPager vp_data = (ViewPager) findViewById(R.id.vp_data);
        vp_data.setPageMargin(40);
        Integer[] mIntegers = new Integer[]{R.mipmap.bg_zuowen_01, R.mipmap.bg_zuowen_02,R.mipmap.bg_zuowen_03,R.mipmap.bg_zuowen_04};
        vp_data.setAdapter(new MyAdapter(this,mIntegers));
    }
    static class MyAdapter extends PagerAdapter {
        private Context mContext;
        private Integer[] mRes;
        private final int mCount;

        MyAdapter(Context context, Integer[] res){
            mCount = new Random().nextInt(50) + 20;
            mContext = context;
            mRes = res;
        }
        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(mRes[position % mRes.length]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.8f;
        }
    }
}
