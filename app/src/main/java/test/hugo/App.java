package test.hugo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Administrator on 2017/5/17.
 */

public class App extends Application {

    private static App mSApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mSApp = this;
        //hehehhhehe
    }
    public static App getInstance(){
        return mSApp;
    }
}
