package com.project.service.impl;

import com.project.entity.User;
import com.project.mapper.UserMapper;
import com.project.mybatis.Pager;
import com.project.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * describe:
 *
 * @author
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    /**
     * 查找用户
     *
     * @param pager
     * @return
     */
    @Override
    public List<User> findByPage(Pager pager) {
        return userMapper.findByPage(pager);
    }

    /**
     * 根据用户名和密码查找用户
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public User findByName(String userName, String password) {
        return userMapper.findByName(userName, password);
    }
}
