package com.gamephone.acs.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gamephone.acs.model.User;


public interface UserMapper {

    public int createUser(User user);

    public User getUserByUserName(String userName);

    public User getUserByEmail(String email);

    public User checkLogin(Map<String, String> login);
    
    public User getThirdUser(@Param(value="userType") Integer userType,  @Param(value="tid") String tid);
    
    public int updateUserPassword(@Param(value="newPassword") String newPassword, @Param(value="id") Integer id);

    public User findPasword(@Param(value="userName") String userName, @Param(value="email") String email);
}
