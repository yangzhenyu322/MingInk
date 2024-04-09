package com.mingink.job.tuling.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/1 18:45
 */
public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        for (int i = 0; i < 2; i++) {
            TimerTask task = new FooTimerTask("foo" + i);
            // 任务超时（执行时间超过预设的间隔时间）：schedule(导致 丢任务/少执行任务)、scheduleAtFixedRate(导致 任务执行时间混乱)，这是因为timer是以单线程方式执行task的
            // 因此需要严格保证执行执行时间不超过延迟时间 或者 以线程池的方式执行timer
            timer.schedule(task, new Date(), 2000);
//            timer.scheduleAtFixedRate(task, new Date(), 2000);
        }
    }
}

class FooTimerTask extends TimerTask {
    private String name;

    public FooTimerTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("name = " + name + ", startTime = " + new Date());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("name = " + name + ", endTime = " + new Date());
    }
}