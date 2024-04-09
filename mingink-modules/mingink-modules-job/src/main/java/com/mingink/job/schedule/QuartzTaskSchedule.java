package com.mingink.job.schedule;

import com.mingink.job.task.DongAoJob;
import com.mingink.job.task.MyJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义任务描述和具体的执行时间
 * @Author: ZenSheep
 * @Date: 2024/3/31 10:22
 */
@Configuration
public class QuartzTaskSchedule {
    @Bean
    public JobDetail myJobDetail() {
        // 指定任务描述具体的实现类
        return JobBuilder.newJob(MyJob.class)
                // 指定任务名称
                .withIdentity("myJob")
                // 任务描述
                .withDescription("任务描述：自定义任务")
                // 使用 DataMap
                .usingJobData("count", 0)
                // 每次任务执行后进行存储
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myTrigger() {
        // 创建触发器
        return TriggerBuilder.newTrigger()
                // 指定触发器名称
                .withIdentity("myTrigger")
                // 绑定 JobDetail
                .forJob(myJobDetail())
                // 设置调度器
                // cron表达式：[秒][分][小时][日][月][周][年]
                // 每隔 1 分钟执行一次 job]
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobDetail() {
        // 指定任务描述具体的实现类
        return JobBuilder.newJob(DongAoJob.class)
                // 指定任务的名称
                .withIdentity("dongAoJob")
                // 任务描述
                .withDescription("任务描述：用于输出奥运欢迎语")
                .usingJobData("count", 0)
                // 每次任务执行后进行存储
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        // 创建触发器
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger")
                // 绑定工作任务
                .forJob(jobDetail())
                // cron表达式：[秒][分][小时][日][月][周][年]
                // 每隔 5 秒执行一次 job]
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                .build();
    }
}
