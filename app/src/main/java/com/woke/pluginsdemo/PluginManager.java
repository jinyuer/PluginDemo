package com.woke.pluginsdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private Resources mResources;
    private DexClassLoader mDexClassLoader;
    private PackageInfo mPackageArchiveInfo;

    public Resources getResources() {
        return mResources;
    }

    public DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    public static PluginManager getOurInstance() {
        return ourInstance;
    }

    public PackageInfo getPackageArchiveInfo() {
        return mPackageArchiveInfo;
    }

    public void loadApk(String path) {
        File dexOutFile = mContext.getDir("dex", Context.MODE_PRIVATE);
        PackageManager packageManager = mContext.getPackageManager();

            mPackageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        mDexClassLoader = new DexClassLoader(path, dexOutFile.getAbsolutePath(), null, mContext.getClassLoader());
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            try {
                Method addAssetPath = AssetManager.class.getMethod("addAssetPath",String.class);
                try {
                    addAssetPath.invoke(assetManager, path);
                    mResources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());

                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static final PluginManager ourInstance = new PluginManager();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }
}
