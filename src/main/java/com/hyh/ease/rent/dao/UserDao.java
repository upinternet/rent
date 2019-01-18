package com.hyh.ease.rent.dao;

import com.hyh.ease.rent.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    int insert(User record);

    User selectByPrimaryKey(@Param("userType") String userType , @Param("id") String id , String hello);

    int updateByPrimaryKey(User record);
}
