/**
 * $Id: AbstractCache.java,v 1.1 2012/09/25 06:07:36 xianchao.sun Exp $
 */
package com.gamephone.pay.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;

/**
 * 缓存处理
 * @author xianchao.sun@downjoy.com
 */
public abstract class AbstractCache implements Runnable {

    private static final Logger logger=Logger.getLogger(AbstractCache.class);

    private boolean running=false;

    private int interval=10 * 60 * 1000; // 10Minutes

    private Thread thread=null;

    @PostConstruct
    public void start() {
        if(null == thread) {
            thread=new Thread(this);
        }
        if(!this.running) {
            this.running=true;
            thread.start();
        }
    }

    @PreDestroy
    public void shutDown() {
        this.running=false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.running=false;
    }

    public void run() {
        while(running) {
            try {
                updateCache();
                System.out.println(this.getClass().getName() + " is running... ...");
            } catch(Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                Thread.sleep(interval);
            } catch(InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval=interval;
    }

    /**
     * 更新缓存
     */
    public abstract void updateCache();
}
