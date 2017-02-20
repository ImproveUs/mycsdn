package improveus.com.mycsdn.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.activity.DetailArticleActivity;
import improveus.com.mycsdn.mvpview.DetialArticleMvpView;
import improveus.com.mycsdn.presenter.DetialArticlePresenter;

/**
 * 作者：琉璃琥 on 2017/2/17 16:32
 * 邮箱: 511421121@qq.com
 */
public class DetialArticleFragment extends BaseFragment<DetialArticlePresenter> implements DetialArticleMvpView {

    private TextView newContent;
    private String mArticleUrl;

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
        mArticleUrl = getArguments().getString(DetailArticleActivity.ARTICLE_URL);
        newContent = (TextView) getView().findViewById(R.id.detial_article_text);
        //TODO 地址未处理完
    }

    @Override
    protected DetialArticlePresenter createPresenter() {
        return new DetialArticlePresenter(this);
    }

    @Override
    public void onNext(String article_content) {
        newContent.setText("-----------------------开始----------------------\n"
                + article_content
                + "\n-----------------------结束----------------------");
    }

}
