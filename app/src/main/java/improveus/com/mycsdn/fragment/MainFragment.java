package improveus.com.mycsdn.fragment;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.superrecycleview.superlibrary.recycleview.ProgressStyle;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;
import com.superrecycleview.superlibrary.recycleview.swipemenu.SuperSwipeMenuRecyclerView;

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

    private SuperSwipeMenuRecyclerView articleView;
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
        articleView = (SuperSwipeMenuRecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager =new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false);
        articleView.setLayoutManager(manager);
        articleView.addItemDecoration(new RecyclerViewDivider(getContext(),LinearLayoutManager.HORIZONTAL,1,ContextCompat.getColor(getContext(),R.color.divide_gray_color)));

        //刷新和加载
        articleView.setRefreshEnabled(true);
        articleView.setLoadMoreEnabled(true);
        //刷新的样式
        articleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        articleView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        //设置下拉箭头
        articleView.setArrowImageView(R.drawable.icon_loading);//设置下拉箭头
        articleView.setLoadingListener(new SuperRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mMainPresenter.getBlogList(ListRefreshType.RESRESH_TYPE);
             }

            @Override
            public void onLoadMore() {
                mMainPresenter.getBlogList(ListRefreshType.LOADING_TYPE);
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return mMainPresenter =new MainPresenter(this);
    }

    @Override
    public void onDataCompleted(ListRefreshType type) {
        if(type==ListRefreshType.RESRESH_TYPE){
            articleView.completeRefresh();
        }else{
            articleView.completeLoadMore();
        }
    }

    @Override
    public void omDataError(ListRefreshType type) {

    }





    private ArrayList<MyCsdnModel> data = new ArrayList<>();
    @Override
    public void onDataNext(ListRefreshType type, ArrayList<MyCsdnModel> response) {
        if (type==ListRefreshType.RESRESH_TYPE){//刷新
            data.clear();
            data.addAll(response);
            if(articleAdapter!=null){//考虑到第一次加载
                articleAdapter.notifyDataSetChanged();
            }else{
                articleAdapter = new ArticleAdapter(getContext(),data);
                articleView.setAdapter(articleAdapter);

            }
        }else{
            if(response==null||response.size()==0){
                articleView.setNoMore(true);
            }else {
                data.addAll(response);
                articleAdapter.notifyDataSetChanged();
            }
        }

    }
}
