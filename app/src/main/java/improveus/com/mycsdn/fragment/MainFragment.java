package improveus.com.mycsdn.fragment;


import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.mvpview.MainMvpView;
import improveus.com.mycsdn.presenter.BasePresenter;
import improveus.com.mycsdn.presenter.MainPresenter;

/**
 * 作者：琉璃琥 on 2017/2/14 14:13
 * 邮箱: 511421121@qq.com
 */
public class MainFragment extends BaseFragment implements MainMvpView {

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    private MainPresenter mMainPresenter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView mainView = (TextView) getView().findViewById(R.id.main_text);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onDataCompleted(ListRefreshType type) {

    }

    @Override
    public void omDataError(ListRefreshType type) {

    }

    @Override
    public void onDataNext(ListRefreshType type, ArrayList<MyCsdnModel> response) {
        call2User(response.get(0).getTitle());
    }
}
