package co.bxvip.ui.tocleanmvp.base;

/**
 * @author vic
 * @time 2017-12-15 23:09
 * @des MVP 中的 V
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);

    boolean isActive();
}
