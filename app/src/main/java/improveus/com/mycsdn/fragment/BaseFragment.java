package improveus.com.mycsdn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import improveus.com.mycsdn.presenter.BasePresenter;

/**
 * 作者：琉璃琥 on 2017/2/14 15:22
 * 邮箱: 511421121@qq.com
 * 这里为fragment构建一个基类视图创建时需要子类实现
 * @see #getFragmentLayout() 返回布局
 * @see #createPresenter() 返回所需要创建的代理类
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected P mPresenter;
    private Toast toast;

    public View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return contentView = inflater.inflate(getFragmentLayout(), container, false);
    }

    protected abstract int getFragmentLayout();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.presenterStart();
        }
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected P createPresenter() {
        return null;
    }

    /**
     * 提示用的toast
     *
     * @param tip
     */
    protected void call2User(String tip) {
        if (toast == null)
            toast = Toast.makeText(getContext(), tip, Toast.LENGTH_SHORT);
        toast.setText(tip);
        toast.show();
    }
}
