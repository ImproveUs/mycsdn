package improveus.com.mycsdn.presenter;

import improveus.com.mycsdn.mvpview.MainMvpView;

/**
 * 作者：琉璃琥 on 2017/2/14 14:21
 * 邮箱: 511421121@qq.com
 */
public class MainPresenter implements BasePresenter {

    private MainMvpView mMainMvpView;

    public MainPresenter(MainMvpView mvpView) {
        this.mMainMvpView = mvpView;
    }

    @Override
    public void presenterStart() {
        mMainMvpView.onPresenterStart();
    }

}
