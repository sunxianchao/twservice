/**
 * 
 */
package com.gamephone.sender.common.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.to.GameTO;
import com.gamephone.sender.common.dao.GameDAO;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-14
 */
public class GameDAOImpl extends SqlMapClientDaoSupport implements GameDAO {

    @Override
    public List<GameTO> getAllGames() throws Exception {
        try {
            return this.getSqlMapClient().queryForList("GAME.getAllGames");
        } catch(SQLException e) {
            throw new Exception("db error");
        }
    }

    @Override
    public GameTO getGameTO(Long cpId, Integer seqNum) throws Exception {
        try {
            return (GameTO)this.getSqlMapClient().queryForObject("GAME.getGameTO");
        } catch(SQLException e) {
            throw new Exception("db error");
        }
    }



}
