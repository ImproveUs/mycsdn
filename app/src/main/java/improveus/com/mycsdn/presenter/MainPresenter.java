package improveus.com.mycsdn.presenter;

import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.model.MyCsdnModel;
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
                        ArrayList<MyCsdnModel> myCsdnModels = new ArrayList<MyCsdnModel>();
                        try {
                            Document parse = Jsoup.parse(responseBody.string());
                            Elements link_title = parse.getElementsByClass("link_title");//获取标题节点
                            for (Element title : link_title) {
                                MyCsdnModel myCsdnModel = new MyCsdnModel();
                                myCsdnModel.setTitle(title.text());//获取单个标题
                                myCsdnModels.add(myCsdnModel);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        KLog.i(myCsdnModels.toString());
                    }
                });
    }
}
