package com.mingink.job.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/8 21:41
 */
@Slf4j
@Service
public class JobService implements IJobService {
    @Autowired
    private Scheduler scheduler;

    /**
     * 添加一个 job
     * @param jobClass 任务类
     * @param jobName 任务名称
     * @param jobGroupName 任务组
     * @param jobDescription 任务描述
     * @param cron cron表达式
     * @param jobData jobData map
     * @param endTime 任务终止时间
     * @throws SchedulerException
     */
    @Override
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName,
                       String jobDescription, String cron, Map jobData, Date endTime) throws SchedulerException {
        // 创建 jobDetail 实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass) // 指定任务描述具体的实现类
                .withIdentity(jobName, jobGroupName) // 指定任务名称和组构成任务 key
                .withDescription(jobDescription) // 任务描述
                .storeDurably() // 每次任务执行后进行存储
                .build();
        // 设置 jobData 参数
        if (jobData != null && jobData.size() > 0) {
            jobDetail.getJobDataMap().putAll(jobData);
        }
        // 定义调度触发规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroupName) // 指定任务描述和组构成触发器 key
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                // 使用 cronTrigger 规则
                // cron表达式：[秒][分][小时][日][月][周][年]
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .startNow()
                .build();
        // 设置任务结束时间
        if (endTime != null) {
            trigger.getTriggerBuilder().endAt(endTime);
        }
        // 把任务和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("添加新的Job任务<{}, {}>", jobName, jobGroupName);
    }

    /**
     * 通过任务名称和组获取触发器
     * @param jobName
     * @param jobGroupName
     * @return
     * @throws SchedulerException
     */
    @Override
    public Trigger getTrigger(String jobName, String jobGroupName) throws SchedulerException {
        return scheduler.getTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
    }

    /**
     * 删除一个 job 任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void deleteJob(String jobName, String jobGroupName) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        log.info("删除Job任务<{}, {}>", jobName, jobGroupName);
    }

    /**
     * 暂停一个 job
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.pauseJob(jobKey);
        log.info("暂停Job任务<{}, {}>", jobName, jobGroupName);
    }

    /**
     * 恢复一个 job
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.resumeJob(jobKey);
        log.info("恢复Job任务<{}, {}>", jobName, jobGroupName);
    }

    /**
     * 立即执行一个 job
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void runJobNow(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.triggerJob(jobKey);
        log.info("立即执行一个Job任务<{}, {}>", jobName, jobGroupName);
    }

    /**
     * 修改一个 Job 任务
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @param jobData
     * @throws SchedulerException
     */
    @Override
    public void updateJob(String jobName, String jobGroupName, String cron, Map jobData) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
        } else {
            throw new RuntimeException("格式转换错误");
        }
        // 设置 job 参数
        if (jobData != null && jobData.size() > 0) {
            trigger.getJobDataMap().putAll(jobData);
        }
        // 重启触发器
        scheduler.rescheduleJob(triggerKey, trigger);
        log.info("重启Job任务<{}, {}>", jobName, jobGroupName);
    }
}
