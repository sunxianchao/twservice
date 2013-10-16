/**
 * $Id: GameCache.java,v 1.1 2012/09/25 06:07:36 xianchao.sun Exp $
 */
package com.gamephone.pay.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;


import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.GameTO;
import com.gamephone.pay.dao.GameDAO;
import com.gamephone.pay.exception.YunPayException;

/**
 * 缓存所有游戏
 * @author xianchao.sun@downjoy.com
 */
public class GameCache extends AbstractCache {

    private static final Logger logger=Logger.getLogger(GameCache.class);

    private static Map<Integer, GameTO> games=new ConcurrentHashMap<Integer, GameTO>();

    private static Map<String, GameTO> cpGames=new ConcurrentHashMap<String, GameTO>();

    @Resource
    private GameDAO gameDAO;

    /**
     * 获得所有游戏
     * @return
     */
    public static Map<Integer, GameTO> getGames() {
        return games;
    }

    /**
     * 根据游戏ID获得GameTO
     * @param id
     * @return
     */
    public static GameTO getGameTO(Long id) {
        return games.get(id);
    }

    /**
     * 返回所有游戏
     * @return
     */
    public static Collection<GameTO> getAllGames() {
        return games.values();
    }

    @Override
    public void updateCache() {
        try {
            updateGameCache();
        } catch(YunPayException e) {
            logger.error(e.getMessage(), e);
        }
    }
    /**
     * 根据厂商编号和游戏序号获得GameTO
     * @param cpId
     * @param seqNum
     * @return
     */
    public static GameTO getGameTO(Integer cpId, Integer seqNum) throws YunPayException {
        String key=String.valueOf(cpId) + "_" + String.valueOf(seqNum);
        GameTO to=cpGames.get(key);
        if(null == to) {
            throw new YunPayException(ErrorCode.GAME_NOT_EXIST);
        }
        return to;
    }
    
    /**
     * 更新游戏缓存
     * @throws YunPayException
     */
    private void updateGameCache() throws YunPayException {
        List<GameTO> tmpList=gameDAO.getAllGames();
        if(null == tmpList) {
            return;
        }
        Map<Integer, GameTO> tmpGames=new ConcurrentHashMap<Integer, GameTO>();
        Map<String, GameTO> tmpCPGames=new ConcurrentHashMap<String, GameTO>();
        for(GameTO to: tmpList) {
            tmpGames.put(to.getId(), to);
            tmpCPGames.put(String.valueOf(to.getCpId()) + "_" + String.valueOf(to.getSeqNum()), to);
        }
        games=tmpGames;
        cpGames=tmpCPGames;
    }

}
