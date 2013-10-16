/**
 * $Id: GameDAO.java,v 1.2 2012/04/12 11:33:47 lili.meng Exp $
 */
package com.gamephone.admin.common.dao;

import java.util.List;

import com.gamephone.admin.common.criteria.GameCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;

/**
 * @author lili.meng@downjoy.com
 */
public interface GameDAO {


    /**
     * 获得游戏列表
     * @param criteria
     * @param pageNo
     * @param pageSize
     * @return
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
     * 根据多个游戏id获取游戏列表
     * @param id
     * @return GameTO
     * @throws AdminException
     */
    List<GameTO> getGameByIds(String ids) throws AdminException;

    /**
     * 更新游戏信息
     * @param game
     * @throws AdminException
     */
    void updateGame(GameTO game) throws AdminException;

    /**
     * 新增游戏
     * @param game
     * @throws AdminException
     */
    void insertGame(GameTO game) throws AdminException;
    
    /**
     * 获得所有游戏
     * @return List<GameTO>
     * @throws AdminException
     */
    List<GameTO> getAllGames() throws AdminException;

}
