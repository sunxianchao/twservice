/**
 * $Id: SendQueueServiceImpl.java,v 1.7 2012/05/18 06:45:11 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OrderTO;
import com.gamephone.common.to.SendLogTO;
import com.gamephone.common.util.HTTPUtil;
import com.gamephone.sender.common.cache.GameCache;
import com.gamephone.sender.common.dao.BadQueueDAO;
import com.gamephone.sender.common.dao.GameDAO;
import com.gamephone.sender.common.dao.OrderDAO;
import com.gamephone.sender.common.dao.SendQueueDAO;
import com.gamephone.sender.common.util.SenderUtil;

public class SendQueueServiceImpl implements SendQueueService {

    private static final Logger logger=Logger.getLogger(SendQueueServiceImpl.class);

    private SendQueueDAO sendQueueDAO;

    private BadQueueDAO badQueueDAO;

    private GameDAO gameDAO;
    
    private OrderDAO orderDAO;

    public List<SendLogTO> getSendQueueOrders(Integer timeOut, int threadInd) throws Exception {
        return sendQueueDAO.getSendQueueOrders(timeOut, threadInd);
    }

    public void addToBadQueue(SendLogTO sendQueueOrder) throws Exception {
        logger.error("BAD LOG_ID:" + sendQueueOrder.getOrderId());
        badQueueDAO.addBadQueue(sendQueueOrder);
        sendQueueDAO.removeFromSendQueue(sendQueueOrder.getId());
    }

    public boolean sendSpendLog(SendLogTO sendQueue) throws Exception {
        String urlStr=sendQueue.getPostUrl();
        
        String orderId=null;
        Integer gameServerId=null;        
        Integer amount=0;
        String userId="";
        String extInfo="";
        String remark="";
        OrderTO otherOrder=null;
        GameTO game=null;
        if(sendQueue.getOrderFrom().intValue() == 1){
        	 otherOrder = orderDAO.getOrderById(sendQueue.getOrderId());
        	 if(null == otherOrder) {
                 throw new Exception("OtherOrderTO:" + sendQueue.getOrderId() + "数据不存在");
             }
        	 if(otherOrder.getPaySuccess()==1){
        		 amount=otherOrder.getAmount();
        	 }else{
        		 amount=0;
        	 }
        	 gameServerId=otherOrder.getGameServerId();
             userId=otherOrder.getUserId();
             extInfo=otherOrder.getExtInfo();
             remark=otherOrder.getResultMsg();
             orderId=otherOrder.getOrderId();
             game=GameCache.getGameTO(otherOrder.getGameId());
        }
        if(null == urlStr || urlStr.length() == 0) {
            urlStr=game.getNotifyUrl();
        }
        if(null == urlStr || urlStr.length() == 0) {
            throw new Exception("SERVER_ID:" + gameServerId + "没有通知地址");
        }
        String secretKey=game.getNotifyKey();
        if(null == secretKey || secretKey.length() == 0) {
            throw new Exception("CP_ID:没有密钥");
        }
        String params=SenderUtil.builderReturnParams(orderId, game.getCpId(), game.getSeqNum(), otherOrder.getGameServerId(), floatTrans(amount), userId, extInfo, remark, secretKey);
        sendQueue.setParams(params);
        sendQueue.setPostUrl(urlStr);
        String result=HTTPUtil.httpPost(urlStr, params, "UTF-8");
        logger.info("CP RETURN LOG_ID:" + orderId + ";POST URL:" + urlStr + ";PARAMS:" + params + ";RETURN RESULT:" + result);
        sendQueue.setSendRes(result);
        boolean isSuc=false;
        if(null != result) {
            isSuc=SenderUtil.isSuccessed(result);
        }
        int status=0;
        if(isSuc) {
            status=1;
            this.sendQueueDAO.removeFromSendQueue(sendQueue.getId());
            logger.info("NOTIFY SUCCESS REMOVE LOG_ID:" + orderId +";RETURN RESULT:" + result);
        } else {
            status=2;
            this.sendQueueDAO.updateSendQueueOrder(sendQueue);
            logger.info("NOTIFY BAD LOG_ID:" + orderId + ";RETURN URL:" + urlStr + ";PARAMS:" + params + ";URL RETURN:" + result);
        }
        if(sendQueue.getOrderFrom().intValue() == 1) {
        	 this.orderDAO.updateNoticeStatus(String.valueOf(sendQueue.getOrderId()), status);
        }
        return isSuc;
    }
    
    public static String floatTrans(float d) {
        d=d/100;// 分转成元
        if(Math.round(d) - d == 0) {// 整数不带小数点
            return String.valueOf((long)d);
        }
        return String.valueOf(d);
    }

    
    public SendQueueDAO getSendQueueDAO() {
        return sendQueueDAO;
    }

    
    public void setSendQueueDAO(SendQueueDAO sendQueueDAO) {
        this.sendQueueDAO=sendQueueDAO;
    }

    
    public BadQueueDAO getBadQueueDAO() {
        return badQueueDAO;
    }

    
    public void setBadQueueDAO(BadQueueDAO badQueueDAO) {
        this.badQueueDAO=badQueueDAO;
    }

    
    public GameDAO getGameDAO() {
        return gameDAO;
    }

    
    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO=gameDAO;
    }

    
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO=orderDAO;
    }
    
    

}
