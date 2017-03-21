package improveus.com.mycsdn.presenter;

import com.socks.library.KLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.model.PanelCategory;
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

    private MainMvpView mMainMvpView;
    private int mPage = 1;
    private String url = "/u013424496/article/list";



    public void setUrl(String url) {
        this.url = url;
    }

    public MainPresenter(MainMvpView mvpView) {
        this.mMainMvpView = mvpView;
    }

    @Override
    public void presenterStart() {
        mMainMvpView.showProgress();
        getBlogList(ListRefreshType.RESRESH_TYPE);
        getPanelCategory();
    }

    /**
     * 获取刷新数据
     */
    public void getBlogList(final ListRefreshType type) {
        Subscriber<ArrayList<MyCsdnModel>> subscriber = new Subscriber<ArrayList<MyCsdnModel>>() {
            @Override
            public void onCompleted() {
                mMainMvpView.onDataCompleted(type);
            }

            @Override
            public void onError(Throwable e) {
                //TODO 这里怎处理对应的异常?告诉用户是数据获取失败还是解析失败
                mMainMvpView.omDataError(type);
                mMainMvpView.hideProgress();
            }

            @Override
            public void onNext(ArrayList<MyCsdnModel> response) {
                mMainMvpView.onDataNext(type, response);
                mMainMvpView.hideProgress();
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

    /**
     *
     * @param subscriber 发射器
     * @param list 页数
     */
    private void dealBlogList(Subscriber<ArrayList<MyCsdnModel>> subscriber, int list) {
        Observable<ResponseBody> contents = RetrofitManage.getDefault().getBlogList(url,list);
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
//                        if (myCsdnModels != null && myCsdnModels.get(0).equals(mMainMvpView.getFirstCsdnData())) {
//                            return null;//如果获取的数据跟第一条数据一样则判定为加载失败
//                        }
                        return myCsdnModels;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void getPanelCategory() {
        Subscriber<List<PanelCategory>> subscriber = new Subscriber<List<PanelCategory>>() {
            @Override
            public void onCompleted() {
                KLog.i("getPanelCategory--onCoompleted");
            }

            @Override
            public void onError(Throwable e) {
                KLog.i(e);
            }

            @Override
            public void onNext(List<PanelCategory> panelCategories) {
                mMainMvpView.initPopwindow(panelCategories);
            }
        };
        Observable<ResponseBody> observable = RetrofitManage.getDefault().getBlogList("/u013424496/article/list",1);
        observable.subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, List<PanelCategory>>() {
                    @Override
                    public List<PanelCategory> call(ResponseBody responseBody) {
                        List<PanelCategory> pcList = new ArrayList<PanelCategory>();
                        try{
                            pcList = JsoupForH5.responseBody2PanelCategory(responseBody);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                     return pcList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}

