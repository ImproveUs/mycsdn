package improveus.com.mycsdn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


import improveus.com.mycsdn.MyCsdnApplication;


/**
 *
 * Created by pszh on 2017/2/15 14:45
 * pengsizheng@qq.coom
 */
public class NetworkUtil {

    /**
     *  检查当前网络是否可用
     * @return 是否连接到网络
     */
    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyCsdnApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 给用户弹出无网提示
     * @return
     */
    public static boolean isNetworkErrThenShowMsg(){
        if(!isNetworkAvailable()){
            Toast.makeText(MyCsdnApplication.getAppContext(),
                    "网络错误",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

}
