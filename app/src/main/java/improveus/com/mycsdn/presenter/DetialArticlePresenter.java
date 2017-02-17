package improveus.com.mycsdn.presenter;

import com.socks.library.KLog;

import org.jsoup.Jsoup;

import java.io.IOException;

import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.mvpview.DetialArticleMvpView;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：琉璃琥 on 2017/2/17 16:40
 * 邮箱: 511421121@qq.com
 */
public class DetialArticlePresenter implements BasePresenter {

    private DetialArticleMvpView mDetialArticleMvpView;

    public DetialArticlePresenter(DetialArticleMvpView detialArticleMvpView) {
        this.mDetialArticleMvpView = detialArticleMvpView;
    }

    @Override
    public void presenterStart() {
        Observable<ResponseBody> blogDetial = RetrofitManage.getDefault().getBlogDetial("55104125");
        blogDetial.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    KLog.i(Jsoup.parse(responseBody.string()).getElementById("article_content").html());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
