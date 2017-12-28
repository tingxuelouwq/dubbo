package com.kevin.dubbo;

import com.kevin.facade.user.entity.PmsUser;
import com.kevin.facade.user.service.PmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Run service consumer
 */
public class DubboConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DubboConsumer.class);

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/dubbo-consumer.xml");
            context.start();

            PmsUserService pmsUserService = (PmsUserService) context.getBean("pmsUserService");
            PmsUser pmsUser = pmsUserService.getByUsername("admin");
            System.out.println(pmsUser);
        } catch (Exception e) {
            logger.error("== DubboConsumer context start error: ", e);
        }
    }
}
