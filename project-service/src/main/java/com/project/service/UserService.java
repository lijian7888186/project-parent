package com.project.service;

import com.project.entity.User;
import com.project.mybatis.Pager;

import java.util.List;

/**
 * describe:
 *
 * @author a1
 */
public interface UserService {
    /**
     * 查找用户
     * @param pager
     * @return
     */
    List<User> findByPage(Pager pager);

    /**
     * 根据用户名和密码查找用户
     * @param userName
     * @param password
     * @return
     */
    User findByName(String userName, String password);
}
