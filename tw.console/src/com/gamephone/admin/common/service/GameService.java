/**
 * $Id: GameService.java,v 1.2 2012/04/12 11:33:47 lili.meng Exp $
 */
package com.gamephone.admin.common.service;

import java.util.List;
import java.util.Map;

import com.gamephone.admin.common.criteria.GameCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.type.StatusType;

/**
 * @author lili.meng@downjoy.com
 */
public interface GameService {

    /**
     * 获得游戏列表
     * @param criteria
     * @param pageNo
     * @param pageSize
     * @return SearchPagerModel<GameTO>
     * @throws AdminException
     */
    SearchPagerModel<GameTO> getGameByCriteria(GameCriteriaTO criteria, int pageNo, int pageSize) throws AdminException;

    /**
     * 根据游戏id查询
     * @param id
     * @return GameTO
     * @throws AdminException
     */
    GameTO getGameById(Long id) throws AdminException;

    /**
     * 修改游戏
     * @param game
     * @throws AdminException
     */
    void saveGame(GameTO game) throws AdminException;

    /**
     * 更改游戏状态
     * @param id
     * @param status
     * @throws AdminException
     */
    void changeStatus(int id, int status) throws AdminException;
    
    /**
     * 获得所有游戏
     * @return List<GameTO>
     * @throws AdminException
     */
    List<GameTO> getAllGames() throws AdminException;
    
    List<GameTO> getGameByIds(String ids) throws AdminException;
    
    Map<String, GameTO> getAllGameasMap()throws AdminException;
}
