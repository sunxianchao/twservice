/**
 * $Id: GameServiceImpl.java,v 1.3 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.admin.common.criteria.GameCriteriaTO;
import com.gamephone.admin.common.dao.GameDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.GameService;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;

/**
 * @author lili.meng@downjoy.com
 */
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDAO gameDAO;

    public SearchPagerModel<GameTO> getGameByCriteria(GameCriteriaTO criteria, int pageNo, int pageSize) throws AdminException {
        return gameDAO.getGameByCriteria(criteria, pageNo, pageSize);
    }

    public GameTO getGameById(Long id) throws AdminException {
        return gameDAO.getGameById(id);
    }
    
    public List<GameTO> getGameByIds(String ids) throws AdminException {
        return gameDAO.getGameByIds(ids);
    }

    public void saveGame(GameTO game) throws AdminException {
        if(game.getId() != null) {
            gameDAO.updateGame(game);
        } else {
            gameDAO.insertGame(game);
        }
    }

    public void changeStatus(int id, int status) throws AdminException {
        GameTO game=new GameTO();
        game.setId(id);
        game.setStatus(status);
        gameDAO.updateGame(game);
    }

    public List<GameTO> getAllGames() throws AdminException {
        return gameDAO.getAllGames();
    }
    
    public Map<String, GameTO> getAllGameasMap()throws AdminException {
        List<GameTO> games=getAllGames();
        Map<String, GameTO> gameMap=new HashMap<String, GameTO>();
        for(GameTO game : games){
            gameMap.put(String.valueOf(game.getId()), game);
        }
        return gameMap;
    }

}
