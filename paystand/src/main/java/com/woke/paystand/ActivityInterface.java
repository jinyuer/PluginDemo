package com.woke.paystand;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public interface ActivityInterface {
    /**
     * 注入上下文
     *
     * @param proxyActitity
     */
    public void attach(Activity proxyActitity);

    public void onCreate(Bundle savedInstanceState);
    public void onStart();
    public void onResume();
    public void onStop();
    public void onDestory();
    public  void onSaveIntstanceState(Bundle outState);
    public boolean onTouchEvent(MotionEvent event);
    public void onBackPressed();
}
