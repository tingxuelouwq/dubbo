package com.kevin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @类名: ProcessUtil
 * @包名：com.kevin.common.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/10/25 15:14
 * @版本：1.0
 * @描述：进程工具类
 */
public class ProcessUtil {

    private static final Logger log = LoggerFactory.getLogger(ProcessUtil.class);

    private ProcessUtil() {
        // no constructor function
    }

    /**
     * 执行简单的shell命令
     * @param command
     * @throws IOException
     */
    public static void executeShell(String command) throws IOException {
        log.info("执行shell命令: {}", command);
        Runtime.getRuntime().exec(command);
    }

    /**
     * 执行复杂的shell命令
     * @param commands
     * @throws IOException
     */
    public static void executeShell(String[] commands) throws IOException {
        log.info("执行shell命令: {}", StringUtil.parseToString(commands, " "));
        Runtime.getRuntime().exec(commands);
    }
}
