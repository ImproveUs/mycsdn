package improveus.com.mycsdn.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.presenter.BasePresenter;
import improveus.com.mycsdn.util.ProgressWheel;

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
        }
        if (mPresenter != null) {
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
    /**
     * 得到自定义的progressDialog ,按钮的网络交互LoadingDialog
     * @param context
     * @param msg 提示字
     * @param isCanClose 返回键时候可以消失 true不可以，false可以
     * @return
     */
    public Dialog createLoadingDialog(Context context, String msg, boolean isCanClose) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.herily_alertex_dialog_custom_frame_layout, null);// 得到加载view
        ProgressWheel spaceshipImage = (ProgressWheel) v.findViewById(R.id.customFrameLoadImg);
        spaceshipImage.stopSpinning();
        spaceshipImage.startSpinning();
        TextView tipTextView = (TextView) v.findViewById(R.id.customFrameMsg);// 提示文字
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(isCanClose);// 不可以用“返回键”取消
        loadingDialog.setCanceledOnTouchOutside(false);//点击外部是不取消dialog
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }
}
