package co.bxvip.ui.tocleanmvp.base;

/**
 * Created by jay on 2018/1/18.
 */

public abstract class BaseLazyFragment extends BaseFragment {

    protected boolean mIsVisible = false;
    protected boolean mIsLoad = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     *
     * @author 漆可
     * @date 2016-5-26 下午4:09:39
     */
    protected void onVisibleToUser() {
        if (mIsVisible && !mIsLoad) {
            onLazyLoad();
        }
    }

    @Override
    public void getData() {

    }

    public abstract void onLazyLoad();
}
