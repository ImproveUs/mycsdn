package improveus.com.mycsdn.presenter;

import org.junit.Before;
import org.junit.Test;

import improveus.com.mycsdn.mvpview.MainMvpView;

import static org.junit.Assert.*;

/**
 * 作者：琉璃琥 on 2017/2/16 14:30
 * 邮箱: 511421121@qq.com
 */
public class MainPresenterTest {

    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        mainPresenter = new MainPresenter(null);
    }

    @Test
    public void presenterStart() throws Exception {
        mainPresenter.presenterStart();
    }

}