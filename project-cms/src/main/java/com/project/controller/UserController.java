package com.project.controller;

import com.project.entity.User;
import com.project.mybatis.Esort;
import com.project.mybatis.Pager;
import com.project.mybatis.SqlSort;
import com.project.service.UserService;
import com.project.util.redis.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Stream;

/**
 * describe:
 *
 * @author
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;
    @RequestMapping("findByPage")
    @ResponseBody
    public String findByPage() {
        Pager pager = new Pager(10, 1);
        pager.setSorts(new SqlSort[]{new SqlSort("id", Esort.DESC)});
        List<User> byPage = userService.findByPage(pager);
        System.out.println(byPage);
        return "success";
    }
}
