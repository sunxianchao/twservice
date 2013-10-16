package com.gamephone.acs.cache;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.User;


public interface UserCache {
	public void writeLoginUser(String userName, User user) throws AcsException;
	public User readLoginUser(String userName)throws AcsException;
	public Long removeLoginUser(String... userName) throws AcsException;
	public User getUserByName(String userName) throws AcsException;
	public User getUserByEmail(String email) throws AcsException;
	public void writeUser(User user)throws AcsException;
}
