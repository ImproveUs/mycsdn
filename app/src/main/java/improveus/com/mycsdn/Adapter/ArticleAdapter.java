package improveus.com.mycsdn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.activity.DetailArticleActivity;
import improveus.com.mycsdn.model.MyCsdnModel;

/**
 * Created by pszh on 2017/2/17.
 * pengsizheng@qq.com
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{
    private Context context;
    private List<MyCsdnModel> data;
    public ArticleAdapter (Context context,@NonNull List<MyCsdnModel> data ){
        this.context = context;
        this.data = data;
    }

    //当viewholder创建的时候回调 加载xml布局
    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_main_recycle,null);
        return new ArticleViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder holder, int position) {
        final MyCsdnModel myCsdnModel = data.get(position);
        holder.title_name.setText(myCsdnModel.getTitle());
        if (myCsdnModel.getIconType()!=null){
            if("Original".equals(myCsdnModel.getIconType())){//原创

            }else if ("Repost".equals(myCsdnModel.getIconType())){//转载

            }else {//翻译

            }
        }

        holder.title_content.setText(myCsdnModel.getArticleDescription());
        holder.main_time.setText(myCsdnModel.getPostdate());
        holder.read_number.setText(myCsdnModel.getArticleView());
        holder.comment_num.setText(myCsdnModel.getArticleComments());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailArticleActivity.class);
                intent.putExtra("TITLEURL",myCsdnModel.getTitleUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(data!=null&&data.size()!=0){
            return data.size();
        }else{
            return 0;
        }
    }

    class  ArticleViewHolder extends  RecyclerView.ViewHolder{
        private TextView title_name;//标题
        private ImageView ic_original;//是否原创
        private TextView title_content;//简要内容
        private TextView main_time;//创建日期
        private TextView read_number;//阅读条数
        private TextView comment_num;//评论条数
        public ArticleViewHolder(View itemView) {
            super(itemView);
            title_name = (TextView) itemView.findViewById(R.id.title_name);
            ic_original = (ImageView) itemView.findViewById(R.id.ic_original);
            title_content = (TextView) itemView.findViewById(R.id.title_content);
            main_time = (TextView) itemView.findViewById(R.id.main_time);
            read_number = (TextView) itemView.findViewById(R.id.read_number);
            comment_num = (TextView) itemView.findViewById(R.id.comment_num);

        }
    }

}
