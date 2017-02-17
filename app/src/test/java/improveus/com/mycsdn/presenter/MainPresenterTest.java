package improveus.com.mycsdn.presenter;

import org.junit.Test;
import org.mockito.Mockito;

import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.mvpview.MainMvpView;

/**
 * 作者：琉璃琥 on 2017/2/16 14:30
 * 邮箱: 511421121@qq.com
 */
public class MainPresenterTest {

    @Test
    public void getBlogList() throws Exception {
        MainMvpView mainMvpView = Mockito.mock(MainMvpView.class);//假想一个类
        MainPresenter mainPresenter = new MainPresenter(mainMvpView);//创建测试实例
        mainPresenter.getBlogList(ListRefreshType.RESRESH_TYPE);//测试方法
//        Mockito.verify(mainMvpView).onDataCompleted(ListRefreshType.RESRESH_TYPE);//测试该方法是否被调用
        //TODO 未实现
    }
}