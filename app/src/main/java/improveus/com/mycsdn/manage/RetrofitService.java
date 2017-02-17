package improveus.com.mycsdn.manage;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者：琉璃琥 on 2017/2/15 09:02
 * 邮箱: 511421121@qq.com
 * 网络请求接口
 */
public interface RetrofitService {

    //    http://blog.csdn.net/u013424496/article/list/2
    @GET("u013424496/article/list/{list}")
    Observable<ResponseBody> getBlogList(@Path("list") int list);
}
