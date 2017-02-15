package improveus.com.mycsdn.activity;

import android.support.v4.app.Fragment;

import improveus.com.mycsdn.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFragment() {
        return  MainFragment.getInstance();
    }
}
