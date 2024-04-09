package com.mingink.job.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定义任务类
 * @Author: ZenSheep
 * @Date: 2024/3/31 10:20
 */
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DongAoJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        jobDataMap.put("count", (int)jobDataMap.get("count") + 1);
        log.info("幼年是盼盼，青年是晶晶，中年是冰墩墩，生活见好逐渐发福, count: {}", jobDataMap.get("count"));
    }
}