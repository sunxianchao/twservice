/**
 * 
 */
package com.gamephone.pay.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.GameTO;
import com.gamephone.pay.dao.GameDAO;
import com.gamephone.pay.exception.YunPayException;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-14
 */
public class GameDAOImpl extends SqlMapClientDaoSupport implements GameDAO {

    @Override
    public List<GameTO> getAllGames() throws YunPayException {
        try {
            return this.getSqlMapClient().queryForList("GAME.getAllGames");
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }

    @Override
    public GameTO getGameTO(Long cpId, Integer seqNum) throws YunPayException {
        try {
            return (GameTO)this.getSqlMapClient().queryForObject("GAME.getGameTO");
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }



}
