/**
 * $Id: Main.java,v 1.2 2012/05/08 03:05:06 jiayu.qiu Exp $
 */
package com.gamephone.sender.ui.console;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gamephone.common.to.SendLogTO;
import com.gamephone.sender.common.service.SendQueueService;

public class Main {

    private static Logger logger=Logger.getLogger(Main.class);

    private static Thread[] sendThreds=null;

    private static final int LIMIT_TIMES=10;// 重发次数

    private static final Integer INTERVAL=5; // 5Minutes

    private static ApplicationContext applicationContext=null;

    private static SendQueueService sendQueueService=null;

    private static boolean running=false;

    public static void main(String[] args) {
        try {
            String[] tmp=new String[]{"spring-datasource.xml", "spring-daos.xml", "spring-service.xml"};
            applicationContext=new ClassPathXmlApplicationContext(tmp);
            sendQueueService=(SendQueueService)applicationContext.getBean("sendQueueService");
            running=true;
            Main sender=new Main();
            sender.startSend();
        } catch(RuntimeException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    public void startSend() {
        /**
         * 使用用10个线程来发送数据。 每个线程根据trheadInd从数据库中取数据，必须保证不能重复取数据。
         */
        sendThreds=new Thread[10];
        for(int i=0; i < sendThreds.length; i++) {
            Main.SendHandler handler=new Main.SendHandler(i);
            Thread thread=new Thread(handler);
            thread.start();
        }
        logger.error("System started");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        running=false;
        applicationContext=null;
    }

    class SendHandler implements Runnable {

        private int trheadInd;

        public SendHandler(int trheadInd) {
            this.trheadInd=trheadInd;
        }

        public void run() {
            while(running) {
                List<SendLogTO> orders=null;
                try {
                    orders=sendQueueService.getSendQueueOrders(INTERVAL, trheadInd);
                } catch(Exception ex) {
                    ex.printStackTrace();
                    logger.error("Error occurs while retrieving send queue: " + ex.toString());
                }
                if(orders != null && !orders.isEmpty()) {
                    for(final SendLogTO order: orders) {
                        logger.info("GET ORDER:" + order.getOrderId());
                        boolean isError=false;
                        try {
                            if(sendQueueService.sendSpendLog(order)){
                                continue;
                            }
                            order.setSendTimes(order.getSendTimes() + 1);
                        } catch(Exception e) {
                            logger.error("LOG_ID:"+order.getOrderId()+";"+ e.getMessage(), e);
                            order.setSendRes(e.getMessage());
                            isError=true;
                        }
                        // 如果有异常或超过尝试次数
                        if(isError ||  order.getSendTimes() >= LIMIT_TIMES) {
                            try {
                                logger.info("SEND"+LIMIT_TIMES+" TIMES AND THEN INSERT INTO BAD_QUEUE");
                                sendQueueService.addToBadQueue(order);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(500);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                    logger.error("Error occurs while sleeping:" + ex.toString(), ex);
                }
            }
        }
    }
}
