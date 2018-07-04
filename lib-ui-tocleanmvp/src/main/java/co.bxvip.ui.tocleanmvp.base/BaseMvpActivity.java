package co.bxvip.ui.tocleanmvp.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * <pre>
 *     author: vic
 *     time  : 18-1-9
 *     desc  : co.bxvip.ui.tocleanmvp.base activity
 * </pre>
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if (mPresenter == null) {
            throw new RuntimeException("Presenter Is Empty Please At initPresenter() Init");
        }
        mPresenter.start();
    }

    protected abstract void initPresenter();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
            mPresenter = null;
        }
    }
}
