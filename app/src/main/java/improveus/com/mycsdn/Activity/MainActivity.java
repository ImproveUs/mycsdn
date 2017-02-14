package improveus.com.mycsdn.activity;

import android.os.Bundle;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFragment();
    }

    protected void createFragment() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame);
        if (mainFragment == null) {
            mainFragment = MainFragment.getInstance();
            attachFragment(getSupportFragmentManager(), mainFragment, R.id.main_frame);
        }
    }
}
