package com.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by David on 16/9/2.
 */
public class FileUtils {
    //此处应判断SD卡是否可用。
    public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 将获取到的bm图片，以picName为名称，保存到指定(formats)目录下。
     *
     * @param bm
     * @param picName
     * @return 图片路径。
     */
    public static String saveBitmap(Bitmap bm, String picName) {
        String path = "";
        Log.i("lyw", "保存图片开始");
        try {
            File f = new File(SDPATH + "formats/", picName);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            //50 是压缩率，表示压缩50%; 如果不压缩是100，表示压缩率为0
            bm.compress(Bitmap.CompressFormat.JPEG, 50, out);
            path = f.getAbsolutePath();
            out.flush();
            out.close();
            Log.i("lyw", "已经保存");
            return path;
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 文件由字节转换到 B、K、M、G
     *
     * @param fileS:文件字节大小
     * @return 文件的大小。
     */
    public static String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "0.00";
        if (fileS == 0) {

        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取文件大小
     *
     * @param file ：文件名（包括路径），或者路径。
     * @return
     */
    public static long getFileSize(String file) {
        return getFolderSize(getFile(file));
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return 文件字节大小
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            if (file.isDirectory()) {
                java.io.File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
                return size;
            } else {
                if (file != null && file.exists()) {
                    return file.length();
                }
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static File getFile(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return null;
        } else {
            return new File(dir);
        }
    }

    /**
     * 删除文件。
     *
     * @param file :路径或 名称（包含路径）。
     */
    public static void deleteFile(String file) {
        deleteFile(new File(file));
    }

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int index = 0; index < files.length; index++) {
                File f = files[index];
                if (f.exists()) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        deleteFile(f);
                    }
                }
            }
            if (file != null && file.listFiles().length == 0) {
                file.delete();
            }
        } else {
            file.delete();
        }
    }
}
