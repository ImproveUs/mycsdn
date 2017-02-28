package improveus.com.mycsdn.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.tv.TvView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.socks.library.klog.XmlLog;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import improveus.com.mycsdn.R;
import improveus.com.mycsdn.activity.DetailArticleActivity;
import improveus.com.mycsdn.mvpview.DetialArticleMvpView;
import improveus.com.mycsdn.presenter.DetialArticlePresenter;

/**
 * 作者：琉璃琥 on 2017/2/17 16:32
 * 邮箱: 511421121@qq.com
 */
public class DetialArticleFragment extends BaseFragment<DetialArticlePresenter> implements DetialArticleMvpView {

    private TextView newContent;
    private String mArticleUrl;
    private WebView webView;

    public static DetialArticleFragment getInstance(@NonNull String articleUrl) {
        DetialArticleFragment detialArticleFragment = new DetialArticleFragment();
        if (!TextUtils.isEmpty(articleUrl)) {//参数不为空则设置数据
            Bundle bundle = new Bundle();
            bundle.putString(DetailArticleActivity.ARTICLE_URL, articleUrl);
            detialArticleFragment.setArguments(bundle);
        }
        return detialArticleFragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_detial;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        try {
            mArticleUrl = getArguments().getString(DetailArticleActivity.ARTICLE_URL);
        } catch (Exception e) {
        }
        //这里暂时不适用富文本
        newContent = (TextView) getView().findViewById(R.id.detial_article_text);
        //TODO 地址未处理完
        webView = (WebView) getView().findViewById(R.id.webview);
    }

    @Override
    protected DetialArticlePresenter createPresenter() {
        return new DetialArticlePresenter(this, mArticleUrl);
    }

    @Override
    public void onNext(String articleContent) {
        newContent.setText(Html.fromHtml(articleContent, new MImageGetter(), new MTagHandler()));
    }

    /**
     * 处理富文本图片的帮助类
     */
    private class MImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            MTask mTask = new MTask();
            AsyncTask<String, Void, Drawable> execute = mTask.execute(source);
            try {
                return execute.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 由于要获取网络图片这里先开启异步任务
     */
    private class MTask extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... params) {
            Drawable drawableByNetWork = null;
            try {
                drawableByNetWork = getDrawableByNetWork(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return drawableByNetWork;
        }
    }

    /**
     * 根据地址返回一个drawable对象
     *
     * @param url
     * @return
     * @throws IOException
     */
    private Drawable getDrawableByNetWork(String url) throws IOException {
        Drawable drawable = null;
        URL uRl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) uRl.openConnection();
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);
        if (urlConnection.getResponseCode() == 200) {
            InputStream inputStream = urlConnection.getInputStream();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(inputStream);
                drawable = new BitmapDrawable(getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                inputStream.close();
                urlConnection.disconnect();
            } catch (Exception e) {
            }
        }
        return drawable;
    }

    /**
     * 处理富文本中不能识别的标签
     */
    private class MTagHandler implements Html.TagHandler {

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            //1 判断标签的开始
            if (tag.equalsIgnoreCase("pre") && opening) {
                //2 如果进来则说明当前文本匹配到了pre的开始,这里为其打上一个标记pre,这里开始的位置就是length第三个参数无效
                output.setSpan(new Pre(), output.length(), output.length(), 1);
                //3 判断标签的结束
            } else if (tag.equalsIgnoreCase("pre") && !opening) {
                //4 获取这个标记
                Pre[] spans = output.getSpans(0, output.length(), Pre.class);
                if (spans.length == 0) {
                    //5 标记没获取到  那么自动被忽略
                    output.append("\n----------代码提前结束----------\n");
                    return;
                } else {
                    //6 获取最新的一个标记Pre
                    Pre span = spans[spans.length - 1];
                    //7 获取标记的开始位置
                    int where = output.getSpanStart(span);
                    //8 移除标记
                    output.removeSpan(span);
                    //9 设置文子渲染
                    output.setSpan(new BackgroundColorSpan(0XFF0094FF), where, output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private class Pre {

    }
}
