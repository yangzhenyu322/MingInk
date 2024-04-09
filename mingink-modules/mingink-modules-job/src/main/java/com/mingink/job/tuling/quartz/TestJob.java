package com.mingink.job.tuling.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * / @Author: ZenSheep
 * / @Date: 2024/4/1 20:38
 */
public class TestJob {
    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "job-group1") // 设置 job 的名字和组
                // userJobData 可以理解为给 Job 添加属性和响应的值
                .usingJobData("job", "jobDetail")  // 设置参数 JobDataMap
                // 如果 Job 原本存在属性name，则可以把name作为key，调用其setName(name)方法设置其对应值
                .usingJobData("name", "myJob")
                .usingJobData("count", 0)
                // job 可恢复：在执行的时候，scheduler 发生硬关闭（hard shutdown，比如运行的进程崩溃或者关机了），
                // 则当 scheduler 重新启动的时候，该 job 会被重新执行。此时该 job 的 JobExecutionContext.isRecovering() 返回 true
                .requestRecovery(true)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "trigger-group1") // 设置 trigger 的名字和组
                .usingJobData("trigger", "trigger")
                .usingJobData("count1", 0)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
