package improveus.com.mycsdn.activity;

import android.support.v4.app.Fragment;

import improveus.com.mycsdn.fragment.SearchFragment;

/**
 * Created by pszh on 2017/2/23.
 * pengsizheng@qq.com
 */

public class SearchActivity extends BaseFragmentActivity{
    @Override
    public Fragment getFragment() {
        return new SearchFragment();
    }
}
