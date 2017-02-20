package improveus.com.mycsdn.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import improveus.com.mycsdn.fragment.DetialArticleFragment;

/**
 * Created by pszh on 2017/2/16.
 * pengsizheng@qq.com
 */

public class DetailArticleActivity extends BaseFragmentActivity {

    public static final String ARTICLE_URL = "ARTICLE_URL";

    /**
     * 文章详情页界面入口
     *
     * @param context
     * @param articleUrl
     * @return
     */
    public final Intent getIntent(Context context, @NonNull String articleUrl) {
        Intent intent = new Intent(context, this.getClass());
        if (!TextUtils.isEmpty(articleUrl))
            intent.putExtra(ARTICLE_URL, articleUrl);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        return DetialArticleFragment.getInstance(dealIntent());
    }

    private String dealIntent() {
        Intent intent = getIntent();
        String articleUrl = null;
        try {
            articleUrl = intent.getStringExtra("ARTICLE_URL");
        } catch (Exception e) {
            //TODO 解析异常
        }
        return articleUrl;
    }
}
