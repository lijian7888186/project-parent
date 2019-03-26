package com.project.controller;

import com.project.commons.ResData;
import com.project.commons.STATUS;
import com.project.entity.User;
import com.project.mybatis.Pager;
import com.project.service.UserService;
import com.project.util.redis.RedisService;
import com.project.utils.StaticFields;
import com.project.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * describe:
 *
 * @author
 */
@Controller
@RequestMapping("login")
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;
    @RequestMapping("toLogin")
    @ResponseBody
    public ResData toLogin(String userName, String password, HttpServletResponse response) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return new ResData(STATUS.PARAM_ERROR);
        }
        User user = userService.findByName(userName, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (user == null) {
            return new ResData(STATUS.NO_USER);
        }
        String uuid = UUIDUtil.getUUID();
        Cookie cookie = new Cookie(StaticFields.EMPPREFIX, uuid);
        cookie.setPath("/");
        cookie.setDomain("/");
        response.addCookie(cookie);
        return new ResData(STATUS.SUCCESS);
    }
}
