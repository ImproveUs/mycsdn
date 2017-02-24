package improveus.com.mycsdn.model;

/**
 * 作者：琉璃琥 on 2017/2/16 13:09
 * 邮箱: 511421121@qq.com
 */
public class MyCsdnModel {

    private String title;
    private String articleDescription;
    private String titleUrl;
    private String postdate;
    private String articleView;
    private String articleComments;
    private String iconType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
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

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    @Override
    public String toString() {
        return "MyCsdnModel{" +
                "\n title='" + title + '\'' +
                ", \n articleDescription='" + articleDescription + '\'' +
                ", \n titleUrl='" + titleUrl + '\'' +
                ", \n postdate='" + postdate + '\'' +
                ", \n articleView='" + articleView + '\'' +
                ", \n articleComments='" + articleComments + '\'' +
                ", \n iconType='" + iconType + '\'' +
                "\n }";
    }

    /**
     * 这里重写方法通过文章地址来判断是否是同一篇文章
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyCsdnModel) {
            MyCsdnModel csdnModel = (MyCsdnModel) obj;
            return this.titleUrl.equals(csdnModel.getTitleUrl());
        }
        return super.equals(obj);
    }
}
