/**
 * 
 */
package com.gamephone.pay.dao;

import java.util.List;


import com.gamephone.common.to.GameTO;
import com.gamephone.pay.exception.YunPayException;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-14
 */
public interface GameDAO {

    List<GameTO> getAllGames() throws YunPayException;

    GameTO getGameTO(Long cpId, Integer seqNum) throws YunPayException;

}
