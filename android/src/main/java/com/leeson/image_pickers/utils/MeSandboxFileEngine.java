package com.leeson.image_pickers.utils;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by robot on 2022/3/30 13:50.
 *
 * @author robot < robot >
 *
 * 说明：将相册选中的 Uri 复制到应用临时缓存目录（context.getCacheDir()/pickers/），
 * 替代原来 PictureSelector 默认的 SandboxTransformUtils.copyPathToSandbox(...)。
 */

public class MeSandboxFileEngine implements UriToFileTransformEngine {

    @Override
    public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
        if (call == null) {
            return;
        }

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            Uri uri = Uri.parse(srcPath);
            inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                call.onCallback(srcPath, null);
                return;
            }

            // 解析后缀
            String ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mineType);
            if (ext == null || ext.length() == 0) {
                ext = parseExtFromPath(srcPath);
            }
            if (ext == null || ext.length() == 0) {
                ext = "tmp";
            }

            // 临时缓存目录：context.getCacheDir()/pickers/
            File dir = new File(context.getCacheDir(), "pickers");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File outFile = new File(dir, System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + ext);
            outputStream = new FileOutputStream(outFile, false);

            byte[] buffer = new byte[1024 * 8];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();

            call.onCallback(srcPath, outFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            call.onCallback(srcPath, null);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    private String parseExtFromPath(String path) {
        if (path == null) {
            return null;
        }
        int lastDot = path.lastIndexOf(".");
        int lastSlash = path.lastIndexOf("/");
        if (lastDot > lastSlash && lastDot < path.length() - 1) {
            return path.substring(lastDot + 1);
        }
        return null;
    }
}
