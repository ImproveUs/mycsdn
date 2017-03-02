package improveus.com.mycsdn.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

import improveus.com.mycsdn.MyCsdnApplication;

/**
 * 各种混合的工具类
 * Created by pszh on 2017/3/1.
 * pengsizheng@qq.com
 *
 */

public class MixUtil {

    public static final File CACHE_FILE = MyCsdnApplication.getAppContext().getExternalCacheDir();
    /**
     * 删除文件.
     *File fileDirectory = new File(downloadRootDir);
     * @return true, if successful
     */
    public static boolean deleteFile(File file) {
        try {
            if (file == null||file.length()==0) {
                return false;
            }
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }else{
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *获取版本号
     *
     */
    public static int getVersion(Context context){
        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info.versionCode;
    }
    /**
     * 计算文件或者文件夹的大小 ，单位 MB
     * @param file 要计算的文件或者文件夹 ， 类型：java.io.File
     * @return 大小，单位：KB
     */
    public static double getFileSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
            if (!file.isFile()) {
                //获取文件大小
                File[] fl = file.listFiles();
                double ss = 0;
                for (File f : fl)
                    ss += getFileSize(f);
                return ss;
            } else {
                double ss = (double) file.length() / 1024;
                return ss;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }


}
