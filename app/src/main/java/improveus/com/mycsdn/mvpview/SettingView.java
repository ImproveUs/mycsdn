package improveus.com.mycsdn.mvpview;

/**
 * Created by pszh on 2017/2/27.
 * pengsizheng@qq.com
 */

public interface SettingView {
    //获取版本号，因为没有后台提供
    void setVersionCode(String version);
    //获取缓存文件大小
    void setCacheFileSize(String CacheSize);
    //删除缓存的情况
    void deleteFile(boolean success);
}
