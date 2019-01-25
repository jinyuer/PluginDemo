package com.woke.pluginsdemo;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.woke.paystand.ActivityInterface;

import java.lang.reflect.Constructor;

public class ProxyActivity extends Activity {
    //com.woke.taopiaopiao.MainActivity
    private String className;
    private ActivityInterface mActivityInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");
        try {
            //插桩
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            mActivityInterface = (ActivityInterface) instance;
            //标准
            mActivityInterface.attach(this);
            //传递参数
            Bundle bundle = new Bundle();
            mActivityInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  mActivityInterface.onBackPressed();

    }



    @Override
    protected void onStart() {
        super.onStart();
        mActivityInterface.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityInterface.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityInterface.onDestory();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityInterface.onStop();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mActivityInterface.onSaveIntstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // mActivityInterface.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();

    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();

    }
}
