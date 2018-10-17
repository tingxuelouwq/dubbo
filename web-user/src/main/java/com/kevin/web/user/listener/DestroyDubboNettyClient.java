package com.kevin.web.user.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @类名: DestroyDubboNettyClient
 * @包名：com.kevin.web.user.listener
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2018/10/17 23:11
 * @版本：1.0
 * @描述：
 */
@Component
public class DestroyDubboNettyClient {
    private static final Logger log = LoggerFactory.getLogger(DestroyDubboNettyClient.class);

    @PreDestroy
    public void shutdown() {
        log.info("destroy dubbo NettyClient...");
        try {
            final Class<?> clazz =
                    Class.forName("com.alibaba.dubbo.remoting.transport.netty.NettyClient");
            Field field = clazz.getDeclaredField("channelFactory");
            field.setAccessible(true);
            Object channelFactory = field.get(null);
            Method method = channelFactory.getClass().getMethod("releaseExternalResources");
            method.invoke(channelFactory);
        } catch (ClassNotFoundException | NoSuchFieldException
                | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            log.error("", e);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }
}
