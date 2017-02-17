package improveus.com.mycsdn.fragment;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import improveus.com.mycsdn.Adapter.ArticleAdapter;
import improveus.com.mycsdn.R;
import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.mvpview.MainMvpView;
import improveus.com.mycsdn.presenter.BasePresenter;
import improveus.com.mycsdn.presenter.MainPresenter;
import improveus.com.mycsdn.util.RecyclerViewDivider;

/**
 * 作者：琉璃琥 on 2017/2/14 14:13
 * 邮箱: 511421121@qq.com
 */
public class MainFragment extends BaseFragment implements MainMvpView {

    private RecyclerView articleView;
    private ArticleAdapter articleAdapter;
    public static MainFragment getInstance() {
        return new MainFragment();
    }

    private MainPresenter mMainPresenter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        articleView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager =new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false);
        articleView.setLayoutManager(manager);
        articleView.addItemDecoration(new RecyclerViewDivider(getContext(),LinearLayoutManager.VERTICAL,1,ContextCompat.getColor(getContext(),R.color.divide_gray_color)));


    }

    @Override
    protected BasePresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onDataCompleted(ListRefreshType type) {

    }

    @Override
    public void omDataError(ListRefreshType type) {

    }


    private ArrayList<MyCsdnModel> data = new ArrayList<>();
    @Override
    public void onDataNext(ListRefreshType type, ArrayList<MyCsdnModel> response) {
        if (type==ListRefreshType.RESRESH_TYPE){//刷新
            data = response ;
            if(articleAdapter!=null){//考虑到第一次加载
                articleAdapter.notifyDataSetChanged();
            }else{
                articleAdapter = new ArticleAdapter(getContext(),data);
                articleView.setAdapter(articleAdapter);
            }
        }else{
            data.addAll(response);
            articleAdapter.notifyDataSetChanged();
        }

    }
}
