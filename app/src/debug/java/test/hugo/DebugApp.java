package test.hugo;

import com.facebook.stetho.Stetho;

/**
 * Created by Hugo on 2017/7/4.
 */

public class DebugApp extends App{
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }
}
