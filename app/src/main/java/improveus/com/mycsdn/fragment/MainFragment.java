package improveus.com.mycsdn.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.superrecycleview.superlibrary.recycleview.ProgressStyle;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;
import com.superrecycleview.superlibrary.recycleview.swipemenu.SuperSwipeMenuRecyclerView;

import java.util.ArrayList;

import improveus.com.mycsdn.Adapter.ArticleAdapter;
import improveus.com.mycsdn.R;
import improveus.com.mycsdn.activity.SearchActivity;
import improveus.com.mycsdn.activity.SettingActivity;
import improveus.com.mycsdn.manage.ListRefreshType;
import improveus.com.mycsdn.model.MyCsdnModel;
import improveus.com.mycsdn.mvpview.MainMvpView;
import improveus.com.mycsdn.presenter.MainPresenter;
import improveus.com.mycsdn.util.RecyclerViewDivider;

/**
 * 作者：琉璃琥 on 2017/2/14 14:13
 * 邮箱: 511421121@qq.com
 */
public class MainFragment extends BaseFragment<MainPresenter> implements MainMvpView {

    private SuperSwipeMenuRecyclerView articleView;
    private ArticleAdapter articleAdapter;
    private ImageButton setting;
    private RelativeLayout search_relativeLayout;
    private ImageButton chose_type;
    private Button chose_type_cancle;

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
        setting = (ImageButton) contentView.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = SettingActivity.getIntent(getActivity());
                startActivity(intent);

            }
        });
        search_relativeLayout = (RelativeLayout) contentView.findViewById(R.id.search_relativeLayout);
        chose_type = (ImageButton) contentView.findViewById(R.id.chose_type);
        chose_type_cancle = (Button) contentView.findViewById(R.id.chose_type_cancle);
        search_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chose_type_cancle.setVisibility(View.VISIBLE);
                chose_type.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), search_relativeLayout, "mysear_linear").toBundle());
                } else {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                            search_relativeLayout,
                            0,
                            0,
                            0,
                            0);
                    ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                }
            }
        });


        articleView = (SuperSwipeMenuRecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        articleView.setLayoutManager(manager);
        articleView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.divide_gray_color)));

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
    protected MainPresenter createPresenter() {
        return mMainPresenter = new MainPresenter(this);
    }

    @Override
    public void onDataCompleted(ListRefreshType type) {
        if (type == ListRefreshType.RESRESH_TYPE) {
            articleView.completeRefresh();
        } else {
            articleView.completeLoadMore();
        }
    }

    @Override
    public void omDataError(ListRefreshType type) {
        if (type == ListRefreshType.RESRESH_TYPE) {
            articleView.completeRefresh();
        } else {
            articleView.completeLoadMore();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        chose_type_cancle.setVisibility(View.GONE);
        chose_type.setVisibility(View.VISIBLE);
    }

    private ArrayList<MyCsdnModel> data = new ArrayList<>();

    @Override
    public void onDataNext(ListRefreshType type, ArrayList<MyCsdnModel> response) {
        if (type == ListRefreshType.RESRESH_TYPE) {//刷新
            data.clear();
            data.addAll(response);
            if (articleAdapter != null) {//考虑到第一次加载
                articleAdapter.notifyDataSetChanged();
            } else {
                articleAdapter = new ArticleAdapter(getContext(), data);
                articleView.setAdapter(articleAdapter);
            }
        } else {
            if (response == null || response.size() == 0) {
                articleView.setNoMore(true);
            } else {
                data.addAll(response);
                articleAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public MyCsdnModel getFirstCsdnData() {
        if (articleAdapter != null && articleAdapter.getItemCount() > 0)
            return articleAdapter.getDataInList(0);
        return null;
    }
}
