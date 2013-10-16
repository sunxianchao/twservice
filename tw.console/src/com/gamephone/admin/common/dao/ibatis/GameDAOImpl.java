/**
 * $Id: GameDAOImpl.java,v 1.2 2012/04/12 11:33:47 lili.meng Exp $
 */
package com.gamephone.admin.common.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.admin.common.criteria.GameCriteriaTO;
import com.gamephone.admin.common.dao.GameDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;

/**
 * @author lili.meng@downjoy.com
 */
public class GameDAOImpl extends SqlMapClientDaoSupport implements GameDAO {

    @SuppressWarnings("unchecked")
    public SearchPagerModel<GameTO> getGameByCriteria(GameCriteriaTO criteria, int pageNo, int pageSize) throws AdminException {
        SearchPagerModel<GameTO> searchPagerModel=new SearchPagerModel<GameTO>(pageNo, pageSize);
        criteria.setSearchPagerModel(searchPagerModel);
        Integer count=getGameCntByCriteria(criteria);
        searchPagerModel.setTotal(count);
        if(count > 0) {
            List<GameTO> games=(List<GameTO>)getSqlMapClientTemplate().queryForList("GAME.getGameByCriteria", criteria);
            searchPagerModel.setResultList(games);
        }
        return searchPagerModel;
    }

    public GameTO getGameById(Long id) throws AdminException {
        GameTO game=(GameTO)getSqlMapClientTemplate().queryForObject("GAME.getGameById", id);
        return game;
    }

    public void updateGame(GameTO game) {
        getSqlMapClientTemplate().update("GAME.updateGame", game);
    }

    public void insertGame(GameTO game) throws AdminException {
        getSqlMapClientTemplate().insert("GAME.insertGame", game);

    }

    private Integer getGameCntByCriteria(GameCriteriaTO criteria) throws AdminException {
        return (Integer)getSqlMapClientTemplate().queryForObject("GAME.getGameCntByCriteria", criteria);
    }
    
    public List<GameTO> getAllGames() throws AdminException {
        return (List<GameTO>)getSqlMapClientTemplate().queryForList("GAME.getAllGames");
    }
    
    public List<GameTO> getGameByIds(String gameids) throws AdminException {
        Map<String, String[]> map=new HashMap<String, String[]>();
        if(StringUtils.isNotBlank(gameids)){
            map.put("gameids", gameids.split(","));
        }
        return (List<GameTO>)getSqlMapClientTemplate().queryForList("GAME.getGameByIds", map);
    }

}
