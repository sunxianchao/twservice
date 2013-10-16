package com.gamephone.pay.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.gamephone.common.to.PayChannelTO;
import com.gamephone.pay.dao.PayChannelDAO;
import com.gamephone.pay.exception.YunPayException;


public class PayChannelCache extends AbstractCache{

    private static Map<Integer, PayChannelTO> paychannelMap=new LinkedHashMap<Integer, PayChannelTO>();
    
    private static Map<String, PayChannelTO> payServiceMap=new LinkedHashMap<String, PayChannelTO>();

    @Resource
    private PayChannelDAO payChannelDAO;
    
    @Override
    public void updateCache() {
        try {
            getPayChannels();
            getPayServices();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Map<Integer, PayChannelTO> getPayChannelList(){
        return paychannelMap;
    }
    
    public static Map<String, PayChannelTO> getPayServiceList(int pid, int gameid){
        Iterator<String> it=payServiceMap.keySet().iterator();
        Map<String, PayChannelTO> temppayService=new LinkedHashMap<String, PayChannelTO>();
        while(it.hasNext()){
            PayChannelTO channel=payServiceMap.get(it.next());
            if(channel.getPid()==pid && channel.getGameId()==gameid){
                temppayService.put(channel.getServerId(), channel);
            }
        }
        return temppayService;
    }

    private void getPayServices() throws YunPayException {
        List<PayChannelTO> servicelist=payChannelDAO.getPayServices();
        if(servicelist == null){
            return;
        }
        Map<String, PayChannelTO> temppayService=new LinkedHashMap<String, PayChannelTO>();
        for(PayChannelTO channel : servicelist){
            temppayService.put(channel.getServerId()+"_"+channel.getGameId(), channel);
        }
        payServiceMap=temppayService;
    }

    private void getPayChannels() throws YunPayException {
        List<PayChannelTO> paylist=payChannelDAO.getPayChannels();
        if(paylist == null){
            return;
        }
        Map<Integer, PayChannelTO> tmpPaychannels=new LinkedHashMap<Integer, PayChannelTO>();
        for(PayChannelTO channel : paylist){
            tmpPaychannels.put(channel.getId(), channel);
        }
        paychannelMap=tmpPaychannels;
    }
}
