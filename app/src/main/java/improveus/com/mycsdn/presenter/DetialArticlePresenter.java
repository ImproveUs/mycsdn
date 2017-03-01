package improveus.com.mycsdn.presenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.List;

import improveus.com.mycsdn.manage.RetrofitManage;
import improveus.com.mycsdn.mvpview.DetialArticleMvpView;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：琉璃琥 on 2017/2/17 16:40
 * 邮箱: 511421121@qq.com
 */
public class DetialArticlePresenter implements BasePresenter {

    private DetialArticleMvpView mDetialArticleMvpView;
    private String mArticleUrl;

    public DetialArticlePresenter(DetialArticleMvpView detialArticleMvpView, String articleUrl) {
        this.mDetialArticleMvpView = detialArticleMvpView;
        this.mArticleUrl = articleUrl;
    }

    @Override
    public void presenterStart() {
        Observable<ResponseBody> blogDetial = RetrofitManage.getDefault().getBlogDetial(mArticleUrl);
        blogDetial.subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String replace = "";
                        try {
                            Element articleContent = Jsoup.parse(responseBody.string()).getElementById("article_content");
                            List<Node> nodes = articleContent.childNodes();//将元素作为节点处理
                            for (Node node : nodes) {//遍历节点
                                if (node.childNodes().size() != 0) {//如果有内容则节点存在子节点(内容)
//                                    Document parse = Jsoup.parse(node.toString());//将节点转为元素
//                                    Elements p = null;
//                                    String src = null;
//                                    String pre = null;
//                                    if ((p = parse.getElementsByTag("p")).hasText())//找到p节点
//                                        stringBuilder.append("\n#1#\n" + p.text());
//                                    else if (!TextUtils.isEmpty(src = parse.getElementsByTag("img").attr("src"))) {//找到img节点
//                                        stringBuilder.append("\n#2#\n" + src);
//                                    } else if (!TextUtils.isEmpty(pre = parse.getElementsByTag("pre").text())) {//找到pre节点
//                                        stringBuilder.append("\n#3#\n" + pre);
//                                    }
                                    stringBuilder.append(node.toString());
//                                    StringBuilder sb = new StringBuilder();
//                                    try {
//                                        InputStream in = MyCsdnApplication.getAppContext().getResources().getAssets().open("detialmodel.html");
//                                        String content = "";
//                                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                                        while ((content = reader.readLine()) != null) {
//                                            sb.append(content);
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    replace = sb.toString().replace("{#body#}", stringBuilder.toString());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return stringBuilder.toString();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String responseBody) {
                mDetialArticleMvpView.onNext(responseBody);
            }
        });
    }

}
