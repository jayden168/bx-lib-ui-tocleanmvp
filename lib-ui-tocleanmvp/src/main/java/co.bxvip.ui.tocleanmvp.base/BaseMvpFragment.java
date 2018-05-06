package co.bxvip.ui.tocleanmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * <pre>
 *     author: vic
 *     time  : 18-1-9
 *     desc  : co.bxvip.ui.tocleanmvp.base fragment
 * </pre>
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    public P presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        super.onActivityCreated(savedInstanceState);
        if (presenter == null) {
            throw new RuntimeException("Presenter Is Empty Please At initPresenter() Init");
        }
        presenter.start();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//
//    }

    protected abstract void initPresenter();
}
