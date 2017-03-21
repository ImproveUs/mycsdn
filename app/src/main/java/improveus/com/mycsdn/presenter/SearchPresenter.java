package improveus.com.mycsdn.presenter;

import android.widget.SearchView;

/**
 * Created by pszh on 2017/3/3.
 * pengsizheng@qq.com
 */

public class SearchPresenter implements BasePresenter{
    private SearchView searchView;

    public  SearchPresenter(SearchView searchView){
        this.searchView = searchView;
    }
    @Override
    public void presenterStart() {

    }
}
