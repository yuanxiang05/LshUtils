package com.linsh.lshutils.utils.Basic;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Senh Linsh on 17/1/10.
 */
public class LshFileUtils {

    /**
     * 获取SD卡以app包名命名的文件夹路径
     */
    public static String getPackageDir() {
        return Environment.getExternalStorageDirectory() + "/" + LshApplicationUtils.getContext().getPackageName() + "/";
    }

    /**
     * 判断文件夹不存在则创建
     */
    public static boolean makeDirs(File dir) {
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 判断文件夹不存在则创建
     */
    public static boolean makeParentDirs(File file) {
        if (file == null) return false;

        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 读取文件, 默认编码UTF-8
     */
    public static StringBuilder readFile(File file) {
        return readFile(file, "UTF-8");
    }

    public static StringBuilder readFile(String filePath) {
        return readFile(new File(filePath), "UTF-8");
    }

    private static StringBuilder readFile(File file, String charsetName) {
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(reader);
        }
    }

    /**
     * 写入文件, 默认编码UTF-8, 默认覆盖源文件 (建议在子线程中执行写入文件)
     */
    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    public static boolean writeFile(String filePath, String content) {
        return writeFile(new File(filePath), content, false);
    }

    public static boolean writeFile(File file, String content, boolean append) {
        if (LshStringUtils.isEmpty(content) || file == null || file.isDirectory()) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            LshIOUtils.close(fileWriter);
        }
    }

    public static boolean delete(File file) {
        if (file != null && file.exists()) {
           return file.delete();
        }
        return false;
    }
}
