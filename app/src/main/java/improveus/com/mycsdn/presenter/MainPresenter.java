package improveus.com.mycsdn.presenter;

import com.socks.library.KLog;

import java.io.IOException;

import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.mvpview.MainMvpView;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：琉璃琥 on 2017/2/14 14:21
 * 邮箱: 511421121@qq.com
 */
public class MainPresenter implements BasePresenter {

    private MainMvpView mMainMvpView;

    public MainPresenter(MainMvpView mvpView) {
        this.mMainMvpView = mvpView;
    }

    @Override
    public void presenterStart() {
        getBlogList();
    }

    private void getBlogList() {
        Observable<ResponseBody> contents = RetrofitManage.getDefault().getBlogList("contents");
        contents.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        KLog.i("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.i("onError");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            KLog.i(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
