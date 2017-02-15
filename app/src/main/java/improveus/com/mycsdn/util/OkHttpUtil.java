package improveus.com.mycsdn.util;

import com.socks.library.KLog;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import improveus.com.mycsdn.MyCsdnApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *   OkHttp 的默认配置
 *
 * Created by pszh on 2017/2/15 14:56
 * pengsizheng@qq.coom
 */
public class OkHttpUtil {

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 ;

    public static OkHttpUtil getInstance(){
        return new OkHttpUtil();
    }

    public OkHttpClient getOkHttpClient(){
        //缓存路径
        Cache cache = new Cache(MyCsdnApplication.getAppContext().getExternalCacheDir(),
                1024 * 1024 * 10);//缓存文件为10MB
        return  new OkHttpClient.Builder()
                .cache(cache)
                //设置连接时间
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置读时间
                .readTimeout(10, TimeUnit.SECONDS)
                //设置写的时间
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
//                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(mLoggingInterceptor)
                .build();
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkAvailable()){
                //没网的时候就读缓存
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if(NetworkUtil.isNetworkAvailable()){
                //有网的时候读接口上的 @Headers 里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control",cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return originalResponse.newBuilder()
                        .header("Cache-Control","public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     *  拦截器，配置日志打印
     */
    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            KLog.i(String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            KLog.i(String.format(Locale.getDefault(),"Received response for %s in %.1fms%n%s",response.request().url(),(t2 - t1)/1e6d,response.headers()));
            return response;
        }
    };


}
