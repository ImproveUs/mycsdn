package improveus.com.mycsdn.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.activity.DetailArticleActivity;
import improveus.com.mycsdn.mvpview.DetialArticleMvpView;
import improveus.com.mycsdn.presenter.DetialArticlePresenter;

/**
 * 作者：琉璃琥 on 2017/2/17 16:32
 * 邮箱: 511421121@qq.com
 */
public class DetialArticleFragment extends BaseFragment<DetialArticlePresenter> implements DetialArticleMvpView {

    //    private TextView newContent;
    private String mArticleUrl;
    private WebView webView;

    public static DetialArticleFragment getInstance(@NonNull String articleUrl) {
        DetialArticleFragment detialArticleFragment = new DetialArticleFragment();
        if (!TextUtils.isEmpty(articleUrl)) {//参数不为空则设置数据
            Bundle bundle = new Bundle();
            bundle.putString(DetailArticleActivity.ARTICLE_URL, articleUrl);
            detialArticleFragment.setArguments(bundle);
        }
        return detialArticleFragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_detial;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        try {
            mArticleUrl = getArguments().getString(DetailArticleActivity.ARTICLE_URL);
        } catch (Exception e) {
        }
        //这里暂时不适用富文本
//        newContent = (TextView) getView().findViewById(R.id.detial_article_text);
        //TODO 地址未处理完
        webView = (WebView) getView().findViewById(R.id.webview);
    }

    @Override
    protected DetialArticlePresenter createPresenter() {
        return new DetialArticlePresenter(this,mArticleUrl);
    }

    @Override
    public void onNext(String articleContent) {
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.loadData(articleContent, "text/html; charset=UTF-8", null);
    }

}
