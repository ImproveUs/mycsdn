package improveus.com.mycsdn;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

/**
 * 作者：Administrator on 2017/2/14 09:05
 * 邮箱: 511421121@qq.com
 * 全局状态的基类
 */
public class MyCsdnApplication extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        KLog.init(BuildConfig.LOG_DEBUG);
    }

    /**
     * 用于全局获取上下文
     * @return
     */
    public static Context getAppContext() {
        return applicationContext;
    }
}
