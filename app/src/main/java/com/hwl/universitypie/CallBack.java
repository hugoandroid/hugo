package com.hwl.universitypie;


import android.support.annotation.CallSuper;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public abstract class CallBack implements Listener<String>, ErrorListener {
    @Override
    @CallSuper
    public void onErrorResponse(VolleyError error) {
//		UIUtils.showToast(R.string.connect_server_fail);

    }

    @Override
    public abstract void onResponse(String response);
}
