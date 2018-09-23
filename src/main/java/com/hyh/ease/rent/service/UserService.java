package com.hyh.ease.rent.service;

import com.hyh.ease.rent.dao.UserDao;
import com.hyh.ease.rent.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserDao userDao;

    public int insert(User record)
    {
        return userDao.insert(record);
    }


    @Cacheable("user")
    public User selectByPrimaryKey(Integer id)
    {
        logger.info("select user by id : {}" , id);
        return userDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(User record)
    {
        return userDao.updateByPrimaryKey(record);
    }
}
