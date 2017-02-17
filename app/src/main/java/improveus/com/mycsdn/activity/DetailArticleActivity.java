package improveus.com.mycsdn.activity;

import android.support.v4.app.Fragment;

import improveus.com.mycsdn.fragment.DetialArticleFragment;

/**
 * Created by pszh on 2017/2/16.
 * pengsizheng@qq.com
 */

public class DetailArticleActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFragment() {
        return DetialArticleFragment.getInstance();
    }
}
