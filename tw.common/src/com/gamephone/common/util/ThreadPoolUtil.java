package com.gamephone.common.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    private final static ThreadPoolExecutor threadPool=new ThreadPoolExecutor(10, 300, 30, TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void exexuteThread(Runnable runnable){
        threadPool.execute(runnable);
    }
}
