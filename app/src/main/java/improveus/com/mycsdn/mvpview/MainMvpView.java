package improveus.com.mycsdn.mvpview;

import java.util.ArrayList;
import java.util.List;

import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.model.PanelCategory;

/**
 * 作者：琉璃琥 on 2017/2/14 14:25
 * 邮箱: 511421121@qq.com
 */
public interface MainMvpView {

    void onDataCompleted(ListRefreshType type);

    void omDataError(ListRefreshType type);

    void onDataNext(ListRefreshType type, ArrayList<MyCsdnModel> response);

    void initPopwindow(List<PanelCategory> panelCategories);

    MyCsdnModel getFirstCsdnData();

    void showProgress();
    void hideProgress();

}
