package com.mingink.job.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定义任务类
 * @Author: ZenSheep
 * @Date: 2024/4/3 15:46
 */
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        jobDataMap.put("count", (int) jobDataMap.get("count") + 1);
        log.info("this is my job, count: " + jobDataMap.get("count"));
    }
}