package co.bxvip.ui.tocleanmvp.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import co.bxvip.skin.SkinManager;

/**
 * <pre>
 *     author: vic
 *     time  : 18-1-26
 *     desc  : 最底层BaseActivity
 * </pre>
 */

import com.qihoo360.replugin.loader.a.PluginFragmentActivity;
public abstract class BaseActivity extends PluginFragmentActivity implements IBaseAF {
//import android.support.v4.app.FragmentActivity;
//public abstract class BaseActivity extends FragmentActivity implements IBaseAF {
    protected String TAG = this.getClass().getName();
    protected Context mContext;

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (getSetChangeSkinUse()) {
            SkinManager.getInstance().register(this);
        }

        System.out.println(TAG + "****" + mContext.toString());
        initParams(savedInstanceState);

        View viewContent = getRootView();
        if (getRootView() == null) {
            int layotId = bindLayout();
            if (layotId > 0) {
                XmlResourceParser layout = mContext.getResources().getLayout(layotId);
                try {
                    viewContent = LayoutInflater.from(mContext).inflate(layout, null);
                } finally {
                    layout.close();
                }
            }
        }

        if (viewContent == null) {
            throw new NullPointerException("Activity view is null ,please set layoutid or setRootView ...");
        }
        setContentView(viewContent);
        setTranslucentStatus();
        initViewAfterSetView();
        initView(viewContent);
        initData();
        initEvent();
    }

    protected boolean getSetChangeSkinUse() {
        return false;
    }

    protected void initViewAfterSetView() {

    }

    protected View getRootView() {
        return null;
    }

    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {

        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void initParams(Bundle bundle) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getSetChangeSkinUse()) {
            SkinManager.getInstance().unregister(this);
        }
    }

    //    @VisibleForTesting
//    public IdlingResource getCountingIdlingResource() {
//        return EspressoIdlingResource.getIdlingResource();
//    }
}
