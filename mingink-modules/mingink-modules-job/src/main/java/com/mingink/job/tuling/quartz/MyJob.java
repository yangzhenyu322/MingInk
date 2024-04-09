package com.mingink.job.tuling.quartz;

import lombok.Data;
import org.quartz.*;

import java.util.Date;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/1 20:36
 */
@Data
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob implements Job {
    private String name;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("MyJob execute-" + new Date());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JobDataMap jobDetailMap = context.getJobDetail().getJobDataMap();
        JobDataMap triggerMap = context.getTrigger().getJobDataMap();
        // context.getMergedJobDataMap()：合并的map，如果存在相同的 key，则会进行覆盖
        JobDataMap mergedMap = context.getMergedJobDataMap();

        jobDetailMap.put("count", (int) jobDetailMap.get("count") + 1);
        triggerMap.put("count1", (int) triggerMap.get("count1") + 1);

        System.out.println("jobDetailMap: "  + jobDetailMap.getString("job"));
        System.out.println("triggerMap: " + triggerMap.getString("trigger"));
        System.out.println("mergedMap: " + mergedMap.getString("trigger"));
        System.out.println("name: " + name);
        System.out.println("count: " + jobDetailMap.get("count"));
        System.out.println("count1: " + triggerMap.get("count1"));
    }
}
