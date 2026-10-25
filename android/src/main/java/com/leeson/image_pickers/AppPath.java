package com.leeson.image_pickers;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by lisen on 2019/5/16.
 *
 * @author lisen < 453354858@qq.com >
 */
@SuppressWarnings("all")
public class AppPath {
    private Context context;
    private String packageName;

    public AppPath(Context context) {
        this.context = context;
        packageName = context.getPackageName();
        packageName = packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public String getPackageName() {
        return packageName;
    }

    /**
     * 临时缓存根目录：context.getCacheDir()/pickers/
     */
    private String getTempRootPath() {
        File root = new File(context.getCacheDir(), "pickers");
        if (!root.exists()) {
            root.mkdirs();
        }
        return root.getAbsolutePath();
    }

    /**
     * 在临时根目录下创建（或获取）子目录
     */
    private String getTempSubDirPath(String subDirName) {
        File dir = new File(getTempRootPath(), subDirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    /**
     * @return 临时缓存根目录 -> /data/data/packageName/cache/pickers/
     */
    public String getAppDirPath() {
        return getTempRootPath();
    }

    /**
     * @return /data/data/packageName/cache/pickers/Download
     */
    public String getAppDownloadDirPath() {
        return getTempSubDirPath("Download");
    }

    /**
     * 保存的相机图片
     *
     * @return /data/data/packageName/cache/pickers/DCIM
     */
    public String getAppDCIMDirPath() {
        return getTempSubDirPath("DCIM");
    }

    /**
     * 保存的图片
     *
     * @return /data/data/packageName/cache/pickers/Pictures
     */
    public String getAppImgDirPath() {
        return getTempSubDirPath("Pictures");
    }

    /**
     * 保存的视频文件
     *
     * @return /data/data/packageName/cache/pickers/Movies
     */
    public String getAppVideoDirPath() {
        return getTempSubDirPath("Movies");
    }

    /**
     * 保存的录音文件
     *
     * @return /data/data/packageName/cache/pickers/Audiobooks
     */
    public String getAppAudioDirPath() {
        return getTempSubDirPath("Audiobooks");
    }

    /**
     * 保存的音乐文件
     *
     * @return /data/data/packageName/cache/pickers/Music
     */
    public String getAppMusicDirPath() {
        return getTempSubDirPath("Music");
    }

    /**
     * 保存的text文件
     *
     * @return /data/data/packageName/cache/pickers/Documents
     */
    public String getAppDocumentsDirPath() {
        return getTempSubDirPath("Documents");
    }

    /**
     * 保存的日志文件
     *
     * @return /data/data/packageName/cache/pickers/Documents/logs/
     */
    public String getAppLogDirPath() {
        return getAppDocumentsDirPath() + "/logs/";
    }
}
