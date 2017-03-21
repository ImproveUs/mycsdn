package improveus.com.mycsdn.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.mvpview.SearchView;
import improveus.com.mycsdn.presenter.SearchPresenter;

/**
 * Created by pszh on 2017/2/23.
 * pengsizheng@qq.com
 */

public class SearchFragment extends BaseFragment<SearchPresenter> implements SearchView {
    private Button chose_type;
    private RecyclerView search_rc;
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        chose_type  = (Button) contentView.findViewById(R.id.chose_type);
        search_rc = (RecyclerView) contentView.findViewById(R.id.search_rc);
        chose_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

}
