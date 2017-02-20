package improveus.com.mycsdn.util;

import android.support.annotation.NonNull;

import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import improveus.com.mycsdn.model.MyCsdnModel;
import okhttp3.ResponseBody;

/**
 * Created by pszh on 2017/2/16.
 * pengsizheng@qq.com
 */

public class JsoupForH5 {

    /**
     * 将原始数据解析成模型列表
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @NonNull
    public static ArrayList<MyCsdnModel> responseBody2CsdnModels(ResponseBody responseBody) throws IOException {
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
