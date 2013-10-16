package com.gamephone.acs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gamephone.common.to.GameTO;


public interface GameMapper {

    public List<GameTO> getAllGames();
    
    public GameTO getGameTOById(@Param(value="id") Integer id);
}
