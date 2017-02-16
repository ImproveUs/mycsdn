package improveus.com.mycsdn.model;

/**
 * 作者：琉璃琥 on 2017/2/16 13:09
 * 邮箱: 511421121@qq.com
 */
public class MyCsdnModel {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MyCsdnModel{" +
                "title='" + title + '\'' +
                '}';
    }
}
