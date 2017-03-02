package improveus.com.mycsdn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.mvpview.SettingView;
import improveus.com.mycsdn.presenter.SettingPresenter;

/**
 * Created by pszh on 2017/2/27.
 * pengsizheng@qq.com
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingView, View.OnClickListener {

    private LinearLayout updateLinear;
    private TextView updateText;
    private LinearLayout clearCache;
    private TextView clearCacheText;

    private Button back;
    private TextView shareReadApk;
    private TextView shareWeiXin;
    private TextView aboutOpeaner;
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        updateLinear = (LinearLayout) contentView.findViewById(R.id.update_linear);
        updateText = (TextView) contentView.findViewById(R.id.update_text);
        updateLinear.setOnClickListener(this);
        clearCache = (LinearLayout) contentView.findViewById(R.id.clear_cache);
        clearCacheText = (TextView) contentView.findViewById(R.id.clear_cache_text);
        clearCache.setOnClickListener(this);

        back = (Button) contentView.findViewById(R.id.back);
        back.setOnClickListener(this);
        shareReadApk = (TextView) contentView.findViewById(R.id.share_read_apk);
        shareReadApk.setOnClickListener(this);
        shareWeiXin = (TextView) contentView.findViewById(R.id.share_weixin);
        shareWeiXin.setOnClickListener(this);
        aboutOpeaner = (TextView) contentView.findViewById(R.id.about_opeaner);
        aboutOpeaner.setOnClickListener(this);

    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this,getActivity());
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.update_linear:
                Snackbar snackbar = Snackbar.make(contentView,"当前就是最新版本",1000);
                snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
                snackbar.show();
                break;
            case R.id.clear_cache://清空缓存
                mPresenter.deleteCancheFile();
                break;
            case R.id.share_read_apk:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_apk));//分享的标题
                intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_apk));
                startActivity(Intent.createChooser(intent,"分享"));//目标应用选择对话框的标题 
                break;
            case R.id.share_weixin:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_weixin));//分享的标题
                intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.setting_weixin));//分享类容
                startActivity(Intent.createChooser(intent,"分享"));//目标应用选择对话框的标题
                break;
            case R.id.about_opeaner:
                call2User("关于开发者");
                break;
            case R.id.back:
                getActivity().finish();
                break;
        }
    }
    //获取版本号
    @Override
    public void setVersionCode(String version) {
        updateText.setText(version);
    }
    //缓存的大小
    @Override
    public void setCacheFileSize(String CacheSize) {
        clearCacheText.setText(CacheSize);
    }

    @Override
    public void deleteFile(boolean success) {
        if(success){
           call2User("删除成功");
        }else {
            call2User("删除失败");
        }
    }
    //删除成功

}
