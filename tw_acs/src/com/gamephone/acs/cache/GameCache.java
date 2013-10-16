package com.gamephone.acs.cache;

import java.util.List;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.common.to.GameTO;


public interface GameCache{

    public List<GameTO> getAllGames() throws AcsException;
    
    public GameTO getGameById(Integer id) throws AcsException;
}
