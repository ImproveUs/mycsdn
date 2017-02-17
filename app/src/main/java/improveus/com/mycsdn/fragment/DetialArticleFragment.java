package improveus.com.mycsdn.fragment;

import android.os.Bundle;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.mvpview.DetialArticleMvpView;
import improveus.com.mycsdn.presenter.BasePresenter;
import improveus.com.mycsdn.presenter.DetialArticlePresenter;

/**
 * 作者：琉璃琥 on 2017/2/17 16:32
 * 邮箱: 511421121@qq.com
 */
public class DetialArticleFragment extends BaseFragment<DetialArticlePresenter> implements DetialArticleMvpView {

    public static DetialArticleFragment getInstance() {
        return new DetialArticleFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_detial;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected DetialArticlePresenter createPresenter() {
        return new DetialArticlePresenter(this);
    }

}
