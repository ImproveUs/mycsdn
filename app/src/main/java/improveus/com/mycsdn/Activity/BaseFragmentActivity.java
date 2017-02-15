package improveus.com.mycsdn.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者：Administrator on 2017/2/14 10:41
 * 邮箱: 511421121@qq.com
 * 为activity抽取一个父类
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

    /**
     * 附着fragment  这里还是使用v4报的fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    protected void attachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}
