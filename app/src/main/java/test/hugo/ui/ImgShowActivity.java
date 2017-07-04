/*
 * Copyright (C) 2015 takahirom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package test.hugo.ui;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import test.hugo.R;
import test.hugo.photoView.OnViewTapListener;
import test.hugo.photoView.PhotoView;
import test.hugo.progress.ProgressModelLoader;

import java.lang.ref.WeakReference;


public class ImgShowActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static String INITPOSITION = "index";
    public static String PROPNAME_SCREENLOCATION_LEFT = "left";
    public static String PROPNAME_SCREENLOCATION_TOP = "top";
    public static String PROPNAME_WIDTH = "width";
    public static String PROPNAME_HEIGHT = "height";
    private String[] imgUrl;
    private float mInit_width;
    private float mInit_height;
    private float mLocation_top;
    private float mLocation_left;
    private int initPosition;
    private boolean isFirstInit = true;
    private LinearInterpolator interpolator;
    private ViewPager vp_data;
    private Pair<Float, Float>[] pairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_sub);
        // start the information passed in the Bundle
        extractViewInfoFromBundle(getIntent());
        vp_data = (ViewPager) findViewById(R.id.vp_data);
        vp_data.setAdapter(new MyAdapter(this));
        initPosition = 1;
        vp_data.setCurrentItem(initPosition);
        vp_data.setOnPageChangeListener(this);
        interpolator = new LinearInterpolator();
    }

    private void extractViewInfoFromBundle(Intent intent) {
        Bundle extras = intent.getBundleExtra("loc");
        initPosition = extras.getInt(INITPOSITION);
        mLocation_left = extras.getInt(PROPNAME_SCREENLOCATION_LEFT);
        mLocation_top = extras.getInt(PROPNAME_SCREENLOCATION_TOP);
        mInit_width = extras.getInt(PROPNAME_WIDTH);
        mInit_height = extras.getInt(PROPNAME_HEIGHT);
        imgUrl = new String[]{"http://cdn.oss.gaokaopai.com//mobile/face/post/2017/06/29/2017062981591498705792498_and.jpg",
                "http://cdn.oss.gaokaopai.com//mobile/face/post/2016/04/21/2016042117460047498x.jpg",
                "http://cdn.oss.gaokaopai.com//mobile/face/post/2017/06/28/2017062818012299966x.jpg",
                "http://cdn.oss.gaokaopai.com//mobile/face/post/2017/06/29/2017062918891498706928153_and.webp",
                "http://cdn.oss.gaokaopai.com//mobile/face/post/2017/05/13/2017051310140545224x.jpg",
                "http://cdn.oss.gaokaopai.com//mobile/face/post/2017/05/13/2017051310140490168x.jpg"
        };
        pairs = new Pair[6];
    }

    private void prepareDrawable(GlideDrawable resource, PhotoView view, int position) {
        view.setImageDrawable(resource);
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float screenHeight = getResources().getDisplayMetrics().heightPixels;
        float intrinsicWidth = resource.getIntrinsicWidth();        //图片实际宽高
        float intrinsicHeight = resource.getIntrinsicHeight();
        float scale = Math.min(screenWidth / intrinsicWidth, screenHeight / intrinsicHeight);
        float endPicWidth = (intrinsicWidth * scale);
        float endPicHeight = (intrinsicHeight * scale);
        pairs[position] = new Pair<>(endPicWidth, endPicHeight);

    }

    private void prepareScene(GlideDrawable resource, PhotoView imageView, int position) {
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float screenHeight = getResources().getDisplayMetrics().heightPixels;
        float intrinsicWidth = resource.getIntrinsicWidth();        //图片实际宽高
        float intrinsicHeight = resource.getIntrinsicHeight();
        float scale = Math.min(screenWidth / intrinsicWidth, screenHeight / intrinsicHeight);
        float endPicWidth = (intrinsicWidth * scale);
        float endPicHeight = (intrinsicHeight * scale);
        pairs[position] = new Pair<>(endPicWidth, endPicHeight);
        // capture the end values in the destionation view

        // calculate the scale and positoin deltas
        float scaleX = mInit_width / endPicWidth;
        float scaleY = mInit_height / endPicHeight;
        float scaledPositionX = (screenWidth - mInit_width) / 2;/*+paddingwidth*/
        float scaledPositionY = (screenHeight + 25 * 1.5f - mInit_height) / 2;/*+paddingHeight*/
        imageView.setImageDrawable(resource);
        // scale and reposition the image
        imageView.setScaleX(scaleX);
        imageView.setScaleY(scaleY);

        imageView.setTranslationX(mLocation_left - scaledPositionX);
        imageView.setTranslationY(mLocation_top - scaledPositionY);
        isFirstInit = false;
        // We can now make it visible
        imageView.setVisibility(View.VISIBLE);
        // finally, run the animation
        imageView.animate()
                .setDuration(300)
                .setInterpolator(interpolator)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0)
                .start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //initPosition 可能是0 1 2， 同样 page change 大于2的 不做考虑
        if (position > 2) return;
        int offset = position - initPosition;
//        mLocation_left = (int) (mLocation_left + offset * (mInit_width + 20));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float screenHeight = getResources().getDisplayMetrics().heightPixels;
        float endX = (screenWidth - mInit_width) / 2;
        float endY = (screenHeight - mInit_height) / 2;

        int currentItem = vp_data.getCurrentItem();
        Pair<Float, Float> pair = pairs[currentItem];
        ViewPropertyAnimator animator = vp_data.animate()
                .setDuration(300)
                .setInterpolator(interpolator)
                .scaleX(mInit_width / pair.first)
                .scaleY(mInit_height / pair.second)
                .translationX(mLocation_left - endX)
                .translationY(mLocation_top - endY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            animator.withEndAction(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(0, 0);
                }
            }).start();
        } else {
            animator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    finish();
                    overridePendingTransition(0, 0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    private static class MyAdapter extends PagerAdapter implements OnViewTapListener {
        private ImgShowActivity mActivity;

        public MyAdapter(ImgShowActivity activity) {
            mActivity = activity;
        }

        @Override
        public int getCount() {
            return mActivity.imgUrl.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.pager_photo, container, false);
            final PhotoView imageView = (PhotoView) view.findViewById(R.id.pv_img);
            final ContentLoadingProgressBar pb_loading = (ContentLoadingProgressBar) view.findViewById(R.id.pb_loading);
            final TextView tv_loadprogress = (TextView) view.findViewById(R.id.tv_loadprogress);
            Glide.with(mActivity)
                    .using(new ProgressModelLoader(new ProgressHandler(mActivity, pb_loading,tv_loadprogress)))
                    .load(mActivity.imgUrl[position])
//                    .thumbnail(new DrawableRequestBuilder<Drawable>(){})
                    .into(new ViewTarget<PhotoView, GlideDrawable>(imageView) {

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                            view.setOnViewTapListener(MyAdapter.this);
                            pb_loading.setVisibility(View.GONE);
                            tv_loadprogress.setVisibility(View.GONE);
                            boolean needAnim = mActivity.initPosition == position && mActivity.isFirstInit;
                            if (needAnim)
                                mActivity.prepareScene(resource, this.view, position);
                            else
                                mActivity.prepareDrawable(resource, this.view, position);
                        }
                    });
            container.addView(view);
            return view;
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

    private static class ProgressHandler extends Handler {

        private final WeakReference<Activity> mActivity;
        private final ProgressBar mProgressImageView;
        private TextView mTextView;

        public ProgressHandler(Activity activity, ProgressBar progressBar, TextView textView) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            mProgressImageView = progressBar;
            mTextView = textView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivity.get();
            if (activity == null) return;
            int percent = msg.arg1 * 100 / msg.arg2;
            mProgressImageView.setProgress(percent);
            mTextView.setText(percent + "%");
            if(percent>99){
                mProgressImageView.setVisibility(View.GONE);
                mTextView.setVisibility(View.GONE);
            }
        }
    }
}
