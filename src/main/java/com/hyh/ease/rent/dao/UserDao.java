package com.hyh.ease.rent.dao;

import com.hyh.ease.rent.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);
}
