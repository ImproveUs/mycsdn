package improveus.com.mycsdn.presenter;

import com.socks.library.KLog;

import java.io.IOException;
import java.util.ArrayList;

import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.mvpview.MainMvpView;
import improveus.com.mycsdn.util.JsoupForH5;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：琉璃琥 on 2017/2/14 14:21
 * 邮箱: 511421121@qq.com
 */
public class MainPresenter implements BasePresenter {

    //    <div class="list_item list_view">
    //      <div class="article_title">
    //          <span class="ico ico_type_Original"></span>
    //          <h1> <span class="link_title"><a href="/u013424496/article/details/52091126"> 一步步教你如何为你的app接入支付宝 </a></span> </h1>
    //      </div>
    //      <div class="article_manage">
    //          <span class="link_postdate">2016-08-02 09:51</span>
    //          <span class="link_view" title="阅读次数"><a href="/u013424496/article/details/52091126" title="阅读次数">阅读</a>(3421)</span>
    //          <span class="link_comments" title="评论次数"><a href="/u013424496/article/details/52091126#comments" title="评论次数" onclick="_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_pinglun'])">评论</a>(0)</span>
    //      </div>
    //    </div>

    private MainMvpView mMainMvpView;
    private int mPage = 1;

    public MainPresenter(MainMvpView mvpView) {
        this.mMainMvpView = mvpView;
    }

    @Override
    public void presenterStart() {
        getBlogList(ListRefreshType.RESRESH_TYPE);
    }

    /**
     * 获取刷新数据
     */
    public void getBlogList(final ListRefreshType type) {
        Subscriber<ArrayList<MyCsdnModel>> subscriber = new Subscriber<ArrayList<MyCsdnModel>>() {
            @Override
            public void onCompleted() {
                KLog.i("onCompleted");
                mMainMvpView.onDataCompleted(type);
            }

            @Override
            public void onError(Throwable e) {
                //TODO 这里怎处理对应的异常?告诉用户是数据获取失败还是解析失败
                KLog.i("onError" + e.toString());
                mMainMvpView.omDataError(type);
            }

            @Override
            public void onNext(ArrayList<MyCsdnModel> response) {
                mMainMvpView.onDataNext(type, response);
            }
        };
        switch (type) {
            case RESRESH_TYPE:
                dealBlogList(subscriber, mPage = 1);
                break;
            case LOADING_TYPE:
                dealBlogList(subscriber, ++mPage);
                break;
        }
    }

    private void dealBlogList(Subscriber<ArrayList<MyCsdnModel>> subscriber, int list) {
        Observable<ResponseBody> contents = RetrofitManage.getDefault().getBlogList(list);
        contents.subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, ArrayList<MyCsdnModel>>() {
                    @Override
                    public ArrayList<MyCsdnModel> call(ResponseBody responseBody) {
                        ArrayList<MyCsdnModel> myCsdnModels = null;
                        try {//TODO 这里try--catch只能这么写?
                            myCsdnModels = JsoupForH5.responseBody2CsdnModels(responseBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return myCsdnModels;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



}

