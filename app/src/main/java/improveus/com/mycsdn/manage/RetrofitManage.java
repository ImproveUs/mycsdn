package improveus.com.mycsdn.manage;

import improveus.com.mycsdn.util.OkHttpUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：琉璃琥 on 2017/2/15 08:45
 * 邮箱: 511421121@qq.com
 * 单例构建一个retrofit网络请求管理器
 * {@link RetrofitService}是其对应的请求配置接口
 */
public class RetrofitManage {

    private static RetrofitManage retrofitManage;

    private Retrofit retrofitClient;
    //    private OkHttpClient.Builder okhttpClient;
    private RetrofitService retrofitService;
//    private static final int TIMEOUT = 10000;//超时时间10秒

    //volatile
    private static volatile OkHttpClient mOkHttpClient;

    private RetrofitManage() {
//      okhttpClient = new OkHttpClient.Builder().readTimeout(TIMEOUT, TimeUnit.SECONDS);
        retrofitClient = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl("http://blog.csdn.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofitService = retrofitClient.create(RetrofitService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManage.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = OkHttpUtil.getInstance().getOkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    private static RetrofitManage getInstance() {
        if (retrofitManage == null) {
            synchronized (RetrofitManage.class) {
                if (retrofitManage == null) {
                    retrofitManage = new RetrofitManage();
                }
            }
        }
        return retrofitManage;
    }


    public static RetrofitService getDefault() {
        return getInstance().retrofitService;
    }

}
