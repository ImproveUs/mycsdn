package improveus.com.mycsdn.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import improveus.com.mycsdn.R;

/**
 * 作者：Administrator on 2017/2/14 10:41
 * 邮箱: 511421121@qq.com
 * 为activity抽取一个父类
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basemain);
        attachFragment(getFragment());
    }

    /**
     * 附着fragment  这里还是使用v4报的fragment
     * @param fragment
     */
    protected void attachFragment(@NonNull Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_frame, fragment);
        transaction.commit();
    }

    public abstract Fragment getFragment();

}
