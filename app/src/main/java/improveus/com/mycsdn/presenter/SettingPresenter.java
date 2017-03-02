package improveus.com.mycsdn.presenter;

import android.content.Context;

import java.text.DecimalFormat;

import improveus.com.mycsdn.mvpview.SettingView;
import improveus.com.mycsdn.util.MixUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static improveus.com.mycsdn.util.MixUtil.CACHE_FILE;

/**
 * Created by pszh on 2017/2/27.
 * pengsizheng@qq.com
 */

public class SettingPresenter implements BasePresenter {
    private SettingView settingView;
    private Context context;

    public SettingPresenter(SettingView settingView, Context context) {
        this.settingView = settingView;
        this.context = context;
    }

    @Override
    public void presenterStart() {
        //获取到版本号
        settingView.setVersionCode(MixUtil.getVersion(context) + "");
        //获取缓存
        settingView.setCacheFileSize(new DecimalFormat("##0.00").format(MixUtil.getFileSize(MixUtil.CACHE_FILE))+ "KB");
    }

    //删除缓存的文件夹
    public void deleteCancheFile() {
        Observable observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if(MixUtil.deleteFile(CACHE_FILE)){
                    subscriber.onNext(true);
                }else{
                    subscriber.onNext(false);
                }

            }
        });
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<Boolean>() {
                               @Override
                               public void onCompleted() {
                                   settingView.deleteFile(false);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   settingView.deleteFile(false);
                               }

                               @Override
                               public void onNext(Boolean o) {
                                   if(o){
                                        settingView.deleteFile(true);
                                       settingView.setCacheFileSize(new DecimalFormat("##0.00").format(MixUtil.getFileSize(MixUtil.CACHE_FILE))+ "KB");
                                   }else{
                                       settingView.deleteFile(false);
                                   }

                               }
                           }

                );
    }
}
