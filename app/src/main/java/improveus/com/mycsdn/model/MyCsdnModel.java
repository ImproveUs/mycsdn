package improveus.com.mycsdn.model;

/**
 * 作者：琉璃琥 on 2017/2/16 13:09
 * 邮箱: 511421121@qq.com
 */
public class MyCsdnModel {

    private String title;
    private String titleUrl;
    private String postdate;
    private String articleView;
    private String articleComments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getArticleView() {
        return articleView;
    }

    public void setArticleView(String articleView) {
        this.articleView = articleView;
    }

    public String getArticleComments() {
        return articleComments;
    }

    public void setArticleComments(String articleComments) {
        this.articleComments = articleComments;
    }

    @Override
    public String toString() {
        return "MyCsdnModel{" +
                "title='" + title + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", postdate='" + postdate + '\'' +
                ", articleView='" + articleView + '\'' +
                ", articleComments='" + articleComments + '\'' +
                '}';
    }
}
