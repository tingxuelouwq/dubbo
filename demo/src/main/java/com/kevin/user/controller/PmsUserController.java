package com.kevin.user.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @类名：com.kevin.user
 * @包名：PmsUserController
 * @作者：kevin
 * @时间：2017/7/11 9:43
 * @版本：1.0
 * @描述：PmsUser控制层
 */
@Controller
public class PmsUserController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("ids") String ids,
                       @RequestParam("cids") String cids,
                       @RequestParam("channelType") String channelType) {
        System.out.println(ids);
        System.out.println(cids);
        System.out.println(channelType);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "true");
        return jsonObject.toJSONString();
    }
}
