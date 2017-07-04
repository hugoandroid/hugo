package test.hugo;


import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Locale;
import java.util.Map;

public class VolleyUtil {

    private volatile static RequestQueue requestQueue;
    private volatile static DiskBasedCache cache;

    private static VolleyUtil util;
    private static int DEFAULT_LONG_RETRY = 60 * 60 * 1000;
    private DefaultRetryPolicy longRetry = new DefaultRetryPolicy(60 * 60 * 1000, 1, 1.0f);
    private DefaultRetryPolicy shortRetry = new DefaultRetryPolicy(60 * 1000, 1, 1.0f);

    /**
     * 返回RequestQueue单例
     **/
    public static RequestQueue getQueue() {

        if (requestQueue == null) {
            synchronized (VolleyUtil.class) {
                if (requestQueue == null) {
                    requestQueue =  Volley.newRequestQueue(App.getInstance());
                    requestQueue.start();
                }
            }
        }
        return requestQueue;
    }

    private VolleyUtil() {
    }

    public static VolleyUtil getInstance() {
        if (util == null) {
            synchronized (VolleyUtil.class) {
                if (util == null)
                    util = new VolleyUtil();
            }
        }
        return util;
    }


    public Request<String> post(String url, final Map<String, String> params, int retryPolicyTime, CallBack callBack) {
        StringRequest request = new StringRequest(Method.POST, url, callBack, callBack) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//					android.util.ArrayMap<String, String> headers = new android.util.ArrayMap<>();
//					headers.put("User-Agent", "android");
//					headers.put("GK-VERSION", AppUtils.getVersionCode() + "");
//					headers.put("GK-APPTYPE", "2");
//					headers.put("GK-UUID", AppUtils.getAndroidDeviceUUID());
//					return headers;
//				}else{
                ArrayMap<String, String> headers = new ArrayMap<>();
                headers.put("User-Agent", "android");
                headers.put("GK-VERSION", "430");
                headers.put("GK-APPTYPE", "2");
                return headers;
//				}
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        if (retryPolicyTime > 60) {
            longRetry = new DefaultRetryPolicy(retryPolicyTime, 1, 1.0f);
            request.setRetryPolicy(longRetry);
        } else {
            request.setRetryPolicy(shortRetry);
        }
        return getQueue().add(request);
    }

    public Request<String> post(String url, final Map<String, String> params, boolean longRetry, CallBack callBack) {
        return post(url, params, longRetry ? DEFAULT_LONG_RETRY : 0, callBack);
    }

    public Request<String> post(String url, final Map<String, String> params, CallBack callBack) {
        return post(url, params, 0, callBack);
    }

    public Request<String> get(String url, int retryTime, CallBack callBack) {
        StringRequest request = new StringRequest(url, callBack, callBack) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//					android.util.ArrayMap<String, String> headers = new android.util.ArrayMap<>();
//					headers.put("User-Agent", "android");
//					headers.put("GK-VERSION", AppUtils.getVersionCode() + "");
//					headers.put("GK-APPTYPE", "2");
//					headers.put("GK-UUID", AppUtils.getAndroidDeviceUUID());
//					return headers;
//				}else{
                ArrayMap<String, String> headers = new ArrayMap<>();
                headers.put("User-Agent", "android");
                headers.put("GK-VERSION", "430");
                headers.put("GK-APPTYPE", "2");
                return headers;
            }
//			}
        };
        if (retryTime > 60) {
            longRetry = new DefaultRetryPolicy(retryTime, 1, 1.0f);
            request.setRetryPolicy(longRetry);
        } else {
            request.setRetryPolicy(shortRetry);
        }

        return getQueue().add(request);
    }

    public Request<String> get(String url, CallBack callBack) {
        return get(url, 0, callBack);
    }

    public Request<String> get(String url, boolean longRetry, CallBack callBack) {
        return get(url, longRetry ? DEFAULT_LONG_RETRY : 0, callBack);
    }

    public static String format(String url, Object... args) {
        return format(Locale.CHINA, url, args);
    }

    public static String format(Locale locale, String url, Object... args) {
        if (TextUtils.isEmpty(url)) return "";
        return String.format(locale, url, args);
    }
}
