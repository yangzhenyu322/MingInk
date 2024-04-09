package com.mingink.job.tuling.timer;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * / @Author: ZenSheep
 * / @Date: 2024/4/1 19:23
 */
public class ScheduleThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 2; i++) {
            scheduledThreadPool.scheduleAtFixedRate(new Task("task-" + i), 0, 2, TimeUnit.SECONDS);
        }
    }
}

class Task implements Runnable {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("name = " + name + ", startTime = " + new Date());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("name = " + name + ", endTime = " + new Date());
    }
}
