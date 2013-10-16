/**
 * 
 */
package com.gamephone.sender.common.dao;

import java.util.List;


import com.gamephone.common.to.GameTO;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-14
 */
public interface GameDAO {

    List<GameTO> getAllGames() throws Exception;

    GameTO getGameTO(Long cpId, Integer seqNum) throws Exception;

}
