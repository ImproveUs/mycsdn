package improveus.com.mycsdn.presenter;

import android.support.annotation.NonNull;

import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.mvpview.MainMvpView;
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
                            myCsdnModels = responseBody2CsdnModels(responseBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return myCsdnModels;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将原始数据解析成模型列表
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @NonNull
    private ArrayList<MyCsdnModel> responseBody2CsdnModels(ResponseBody responseBody) throws IOException {
        KLog.i("运行线程" + Thread.currentThread().getName());
        ArrayList<MyCsdnModel> myCsdnModels = new ArrayList<MyCsdnModel>();
        Document parse = Jsoup.parse(responseBody.string());
        KLog.i(parse.html());
        Elements list_item_new = parse.getElementsByClass("list_item");//获取标题列表节点
        for (Element itemNew : list_item_new) {//遍历
            MyCsdnModel myCsdnModel = new MyCsdnModel();
            Elements article_title = itemNew.getElementsByClass("article_title");
            String aClass = article_title.get(0).child(0).attr("class");
            String[] split = aClass.split("_");
            myCsdnModel.setIconType(split[split.length - 1]);
            Element linkTitle = itemNew.getElementsByClass("link_title").get(0);//获取列表标题节点
            myCsdnModel.setTitle(linkTitle.text());
            myCsdnModel.setTitleUrl(linkTitle.getAllElements().get(1).attr("href"));
            myCsdnModel.setArticleDescription(itemNew.getElementsByClass("article_description").text());
            Element articleManage = itemNew.getElementsByClass("article_manage").get(0);//获取列表标题信息节点
            myCsdnModel.setPostdate(articleManage.getElementsByClass("link_postdate").text());
            myCsdnModel.setArticleView(articleManage.getElementsByClass("link_view").text());
            myCsdnModel.setArticleComments(articleManage.getElementsByClass("link_comments").text());
            myCsdnModels.add(myCsdnModel);
        }
        return myCsdnModels;
    }

}

