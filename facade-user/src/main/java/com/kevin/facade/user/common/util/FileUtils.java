package com.kevin.facade.user.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @类名：FileUtils
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/11 17:07
 * @版本：1.0
 * @描述：文件操作工具类
 */
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
        // no constructor function
    }

    /**
     * @param fileName
     * @return java.lang.String
     * @throws
     * @方法名：normalizeFileName
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/7 9:02
     * @描述：将文件名标准化，格式为：ID+后缀，如果原文件名中没有后缀，则后缀名为文件类型
     */
    public static String normalizeFileName(String fileName, String fileType) {
        String extension = getExtension(fileName);
        if (extension == null) {
            extension = fileType;
        }
        return IdGeneratorUtils.next() + "." + extension;
    }

    /**
     * @方法名：getExtension
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/9/25 19:53
     * @描述：获取文件名的扩展名
     * @param fileName
     * @return java.lang.String
     * @exception
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
     * @param is               文件输入流
     * @param absoluteFilePath 文件存放目标绝对路径（包含文件）
     * @return 操作成功，则返回文件绝对路径名，否则返回null
     * @方法名：writeFile
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/12 9:24
     * @描述：写入文件到目标磁盘中
     */
    public static String writeFile(InputStream is, String absoluteFilePath) {
        if (is == null) {
            log.error("The InputStream is null!");
            return null;
        }
        if (StringUtils.isEmpty(absoluteFilePath)) {
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
     * @方法名：normalizeFilePath
     * @作者：kevin
     * @时间：2017/8/29 2:20
     * @描述：将文件路径标准化，格式为：resourcePath + resourceType + yyyyMMddHHmmss
     * @param resourcePath
     * @param resourceType
     * @param normalizedFileName
     * @return java.lang.String
     */
    public static String normalizeFilePath(String resourcePath, String resourceType, String normalizedFileName) {
        StringBuilder normalizedFilePath = new StringBuilder();
        normalizedFilePath.append(resourcePath)
                .append("/")
                .append(resourceType)
                .append("/")
                .append(TimeUtils.getCurrentDateTime())
                .append("/")
                .append(normalizedFileName);
        return normalizedFilePath.toString();
    }

    /**
     * @方法名：covertStringToBytes
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/10/31 15:53
     * @描述：将1GB、5mb类似的文字，转换成文件比较需要的字节数
     * @param sizeStr
     * @return long
     * @throws
     */
    public static long covertStringToBytes(String sizeStr) {
        if (StringUtils.isNotEmpty(sizeStr)) {
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

    /**
     * @方法名：containsFormat
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/10/31 16:07
     * @描述：判断格式是否允许
     * @param allowedFormat
     * @param format
     * @return boolean
     * @throws
     */
    public static boolean containsFormat(String allowedFormat, String format) {
        return allowedFormat.toLowerCase().contains(format.toLowerCase());
    }
}
