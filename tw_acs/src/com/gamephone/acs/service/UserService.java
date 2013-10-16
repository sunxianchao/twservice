package com.gamephone.acs.service;

import java.util.Map;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.User;
import com.gamephone.common.to.OnlineUser;


public interface UserService {

    public int createUser(User user, Integer currentUserGameId) throws AcsException;

    public User getUserByUserName(String userName) throws AcsException;
    
    public User getUserByEmail(String email) throws AcsException;

    public User checkLogin(Map<String, String> login) throws AcsException;
    
    public User getThirdUser(Integer userType, String tid) throws AcsException;
    
    public OnlineUser login(User user, String ip) throws AcsException;
    
    public User findPasword(String userName, String email)  throws AcsException;
    
    public int updateUserPassword(Integer id, String userName, String newPassword)  throws AcsException;

}
