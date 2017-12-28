package com.kevin.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Start service provider
 */
public class DubboProvider {

    private static final Logger logger = LoggerFactory.getLogger(DubboProvider.class);

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            context.start();

            // press any key to exit
            System.in.read();
        } catch (Exception e) {
            logger.error("== DubboProvider context start error: ", e);
        }
    }
}
