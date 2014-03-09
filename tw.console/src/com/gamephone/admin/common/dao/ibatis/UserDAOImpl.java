package com.gamephone.admin.common.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.admin.common.criteria.UserCriteriaTO;
import com.gamephone.admin.common.dao.UserDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.User;


public class UserDAOImpl  extends SqlMapClientDaoSupport implements UserDAO{

    public SearchPagerModel<User> getUsers(UserCriteriaTO criteriaTO) throws AdminException {
        SearchPagerModel<User> pager=criteriaTO.getPageModel();
        Integer count=getUsersCount(criteriaTO);
        if(null != count && count.intValue() > 0) {
            List<User> result=getSqlMapClientTemplate().queryForList("USER.getUsers", criteriaTO);
            pager.setResultList(result);
            pager.setTotal(count);
        }
        return pager;
    }

    public Integer getUsersCount(UserCriteriaTO criteriaTO) throws AdminException {
        return (Integer)getSqlMapClientTemplate().queryForObject("USER.getUsersCount", criteriaTO);
    }

}
