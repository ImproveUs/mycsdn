package improveus.com.mycsdn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;

import improveus.com.mycsdn.model.MyCsdnModel;

/**
 * Created by pszh on 2017/2/16.
 * pengsizheng@qq.com
 */

public class Junt {
    @Test
    public void addition_isCorrect() throws Exception {
        ArrayList<MyCsdnModel> list = new ArrayList<>();
        Document document = Jsoup.connect("http://blog.csdn.net/u013424496").get();
        Elements list_item_new = document.getElementsByClass("list_item");//获取标题列表节点

        for (Element itemNew : list_item_new) {//遍历
            MyCsdnModel myCsdnModel = new MyCsdnModel();
            Element linkTitle = itemNew.getElementsByClass("link_title").get(0);//获取列表标题节点
            myCsdnModel.setTitle(linkTitle.text());
            myCsdnModel.setTitleUrl(linkTitle.getAllElements().select("a").attr("href"));
            Element articleManage = itemNew.getElementsByClass("article_manage").get(0);//获取列表标题信息节点
            myCsdnModel.setPostdate(articleManage.getElementsByClass("link_postdate").text());
            myCsdnModel.setArticleView(articleManage.getElementsByClass("link_view").text());
            myCsdnModel.setArticleComments(articleManage.getElementsByClass("link_comments").text());
            list.add(myCsdnModel);
        }
        System.out.print(list.size());
    }
}
