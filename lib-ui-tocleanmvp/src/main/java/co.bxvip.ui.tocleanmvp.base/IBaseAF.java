package co.bxvip.ui.tocleanmvp.base;

import android.os.Bundle;
import android.view.View;

/**
 * <pre>
 *     author: vic
 *     time  : 18-1-10
 *     desc  : 抽象接口
 * </pre>
 */

public interface IBaseAF {
    /**
     * 初始化Bundle参数
     */
    void initParams(Bundle bundle);

    /**
     * 绑定布局
     */
    int bindLayout();

    /**
     * 初始化view
     */
    void initView(View rootView);

    /**
     * 初始参数数据
     */
    void initData();

    /**
     * 设置监听
     */
    void initEvent();
}
