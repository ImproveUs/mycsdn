package improveus.com.mycsdn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.mvpview.SettingView;
import improveus.com.mycsdn.presenter.SettingPresenter;

/**
 * Created by pszh on 2017/2/27.
 * pengsizheng@qq.com
 */

public class SettingFragment extends BaseFragment implements SettingView, View.OnClickListener {


    private TextView shareReadApk;
    private TextView shareWeiXin;
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        shareReadApk = (TextView) contentView.findViewById(R.id.share_read_apk);
        shareReadApk.setOnClickListener(this);
        shareWeiXin = (TextView) contentView.findViewById(R.id.share_weixin);
        shareWeiXin.setOnClickListener(this);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.share_read_apk:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_apk));//分享的标题
                intent.putExtra(Intent.EXTRA_TEXT,"内容");
                startActivity(Intent.createChooser(intent,"标题"));//目标应用选择对话框的标题 
                break;
            case R.id.share_weixin:

                break;
        }
    }
}
