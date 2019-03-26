package com.project.mapper;

import com.project.entity.User;
import com.project.mybatis.Pager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * describe:
 *
 * @author a1
 */
public interface UserMapper {
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
    User findByName(@Param("userName") String userName, @Param("password") String password);
}
