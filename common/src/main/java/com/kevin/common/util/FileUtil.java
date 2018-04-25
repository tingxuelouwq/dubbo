package com.kevin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @类名：FileUtil
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/11 17:07
 * @版本：1.0
 * @描述：文件操作工具类
 */
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        // no constructor function
    }

    /**
     * 标准化文件名，格式为：ID+后缀，如果原文件名中没有后缀，则后缀名为文件类型
     * @param fileName
     * @param fileType
     * @return
     */
    public static String normalizeFileName(String fileName, String fileType) {
        String extension = getExtension(fileName);
        if (extension == null) {
            extension = fileType;
        }
        return IdGeneratorUtil.next() + "." + extension;
    }

    /**
     * 获取文件名的扩展名
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        String extension = null;
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }
        return extension == null ? null : extension.toLowerCase();
    }

    /**
     * 写入文件到目标磁盘中
     * @param is 文件输入流
     * @param absoluteFilePath 目标磁盘的绝对路径
     * @return 操作成功，则返回文件绝对路径名，否则返回null
     */
    public static String writeFile(InputStream is, String absoluteFilePath) {
        if (is == null) {
            log.error("The InputStream is null!");
            return null;
        }
        if (StringUtil.isEmpty(absoluteFilePath)) {
            log.error("The absoluteFilePath is null!");
            return null;
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String result = null;
        try {
            File file = new File(absoluteFilePath);
            if (file.getParentFile() != null &&
                    !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            } else if (!file.exists()) {
                file.createNewFile();
            }

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[4096];
            int len = 0;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            bos.flush();

            result = file.getAbsolutePath();
            log.info("文件保存成功: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * @方法：writeFile
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2018/1/31 19:07
     * @描述：向指定文件写入文本内容
     * @param content
     * @param absoluteFilePath
     * @return void
     * @throws
     */
    public static void writeFile(String content, String absoluteFilePath) {
        if (content == null) {
            log.error("The InputStream is null!");
            return;
        }
        if (StringUtil.isEmpty(absoluteFilePath)) {
            log.error("The absoluteFilePath is null!");
            return;
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File file = new File(absoluteFilePath);
            if (file.getParentFile() != null &&
                    !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            } else if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将1GB、5mb类似的文字，转换成文件比较需要的字节数
     * @param sizeStr
     * @return
     */
    public static long covertStringToBytes(String sizeStr) {
        if (StringUtil.isNotEmpty(sizeStr)) {
            long kb = 1024;
            long mb = kb * 1024;
            long gb = mb * 1024;
            sizeStr = sizeStr.toUpperCase();
            Long sizeDigit = null;

            if (sizeStr.indexOf("GB") > 0 || sizeStr.indexOf("G") > 0) {
                sizeDigit = Long.valueOf(sizeStr.substring(0, (sizeStr.indexOf("GB") > 0 ? sizeStr.indexOf("GB") : sizeStr.indexOf("G"))));
                if (sizeDigit != null && sizeDigit > 0) {
                    return sizeDigit * gb;
                }
            } else if (sizeStr.indexOf("MB") > 0 || sizeStr.indexOf("M") > 0) {
                sizeDigit = Long.valueOf(sizeStr.substring(0, (sizeStr.indexOf("MB") > 0 ? sizeStr.indexOf("MB") : sizeStr.indexOf("M"))));
                if (sizeDigit != null && sizeDigit > 0) {
                    return sizeDigit * mb;
                }
            } else if (sizeStr.indexOf("KB") > 0 || sizeStr.indexOf("K") > 0) {
                sizeDigit = Long.valueOf(sizeStr.substring(0, (sizeStr.indexOf("KB") > 0 ? sizeStr.indexOf("KB") : sizeStr.indexOf("K"))));
                if (sizeDigit != null && sizeDigit > 0) {
                    return sizeDigit * kb;
                }
            } else if (sizeStr.indexOf("B") > 0) {
                return Long.valueOf(sizeStr.substring(0, sizeStr.indexOf("B")));
            }
        }

        return 0L;
    }
}
