package improveus.com.mycsdn.util;

import org.jsoup.nodes.Element;

import improveus.com.mycsdn.model.MyCsdnModel;

/**
 * Created by pszh on 2017/2/16.
 * pengsizheng@qq.com
 */

public class JsoupForH5 {
    public static MyCsdnModel getMyCsdnModelByh5(Element itemNew) {
        MyCsdnModel myCsdnModel = new MyCsdnModel();
        Element linkTitle = itemNew.getElementsByClass("link_title").get(0);//获取列表标题节点
        myCsdnModel.setTitle(linkTitle.text());
        myCsdnModel.setTitleUrl(linkTitle.getAllElements().select("a").attr("href"));
        Element articleManage = itemNew.getElementsByClass("article_manage").get(0);//获取列表标题信息节点
        myCsdnModel.setPostdate(articleManage.getElementsByClass("link_postdate").text());
        myCsdnModel.setArticleView(articleManage.getElementsByClass("link_view").text());
        myCsdnModel.setArticleComments(articleManage.getElementsByClass("link_comments").text());
        return myCsdnModel;
    }
}
