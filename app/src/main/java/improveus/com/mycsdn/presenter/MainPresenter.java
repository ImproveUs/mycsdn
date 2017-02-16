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

    //    <div class="list_item list_view">
//    <div class="article_title">
//    <span class="ico ico_type_Original"></span>
//    <h1> <span class="link_title"><a href="/u013424496/article/details/52091126"> 一步步教你如何为你的app接入支付宝 </a></span> </h1>
//    </div>
//    <div class="article_manage">
//    <span class="link_postdate">2016-08-02 09:51</span>
//    <span class="link_view" title="阅读次数"><a href="/u013424496/article/details/52091126" title="阅读次数">阅读</a>(3421)</span>
//    <span class="link_comments" title="评论次数"><a href="/u013424496/article/details/52091126#comments" title="评论次数" onclick="_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_pinglun'])">评论</a>(0)</span>
//    </div>
//    </div>
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
                            Elements list_item_new = parse.getElementsByClass("list_item");//获取标题节点
                            KLog.i(list_item_new.get(0).getElementsByClass("link_title").text());
                            Elements article_manage = list_item_new.get(0).getElementsByClass("article_manage");
                            KLog.i(article_manage.get(0).getElementsByClass("link_postdate").text());
                            KLog.i(article_manage.get(0).getElementsByClass("link_view").text());
                            KLog.i(article_manage.get(0).getElementsByClass("link_comments").text());
//                            for (Element title : link_title) {
//                                MyCsdnModel myCsdnModel = new MyCsdnModel();
//                                myCsdnModel.setTitle(title.text());//获取单个标题
//                                myCsdnModels.add(myCsdnModel);
//                            }
//                            Element element = list_item_new.get(0);
//                            element.getElementsByClass("")
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        KLog.i(myCsdnModels.toString());
                    }
                });
    }
}
