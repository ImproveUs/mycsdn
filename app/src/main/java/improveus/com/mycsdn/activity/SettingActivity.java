package improveus.com.mycsdn.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import improveus.com.mycsdn.fragment.SettingFragment;

/**
 * Created by pszh on 2017/2/27.
 * pengsizheng@qq.com
 */

public class SettingActivity extends BaseFragmentActivity {

    public  static Intent getIntent(Context mContext){
        Intent intent = new Intent(mContext,SettingActivity.class);
        return intent;
    }
    @Override
    public Fragment getFragment() {
        return new SettingFragment();
    }
}
