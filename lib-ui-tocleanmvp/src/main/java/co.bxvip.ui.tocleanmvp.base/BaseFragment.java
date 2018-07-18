package co.bxvip.ui.tocleanmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jay on 2018/1/17.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    /**
     * Activity对象
     */
    protected Activity mActivity;

    /**
     * 根View
     */
    private View mRootView;

    // lazy
    private boolean isViewCreated; // view 创建
    private boolean isInitView; // view 初始
    private boolean isLoadDataCompleted;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mRootView) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater.inflate(getLayoutResouceId(), container, false);
            isViewCreated = true;
        }
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInitView && isViewCreated && !isLoadDataCompleted) {
            lazyLoad();
            isLoadDataCompleted = true;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInitView){
            isInitView = true;
            initView();
            justForInitPresenter();
            initDatas();
            initEvents();
        }
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            lazyLoad();
        }
    }

    /**
     * 获取资源布局ID
     *
     * @return
     */
    public abstract int getLayoutResouceId();


    /**
     * 初始化View的ID
     */
    public abstract void initView();

    /**
     * 初始化监听事件
     */
    protected void initDatas() {

    }

    /**
     * 初始化监听事件
     */
    protected void initEvents() {

    }

    protected void justForInitPresenter() {

    }

    /**
     * 获取数据
     */
    protected void lazyLoad() {

    }

    protected void performClick(View view) {

    }

    /**
     * 查找某一个View
     *
     * @param resId 资源ID
     * @return View
     */
    public <T extends View> T findView(int resId) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(resId);
    }

    /**
     * 设置监听
     *
     * @param resId 资源ID
     */
    public void setListener(int resId) {
        if (mRootView != null) {
            mRootView.findViewById(resId).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        performClick(v);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onFragmentPause();
        } else {
            onFragmentResume();
        }
    }

    /**
     * 当Fragment可见时调用
     */
    public void onFragmentResume() {

    }

    /**
     * 当Fragment不可见时调用
     */
    public void onFragmentPause() {

    }
}
