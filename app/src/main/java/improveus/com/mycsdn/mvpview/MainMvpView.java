package improveus.com.mycsdn.mvpview;

import java.util.ArrayList;

import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.model.MyCsdnModel;

/**
 * 作者：琉璃琥 on 2017/2/14 14:25
 * 邮箱: 511421121@qq.com
 */
public interface MainMvpView {

    void onDataCompleted(ListRefreshType type);

    void omDataError(ListRefreshType type);

    void onDataNext(ListRefreshType type, ArrayList<MyCsdnModel> response);
}
