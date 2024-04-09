package com.mingink.job.controller;

import com.mingink.job.service.IJobService;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 任务控制器
 * @Author: ZenSheep
 * @Date: 2024/4/8 22:29
 */
@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJobService jobService;

    /**
     * 添加一个 Job 任务
     * @param jobClass
     * @param jobName
     * @param jobGroupName
     * @param jobDescription
     * @param cron
     * @param jobData
     * @param endTime
     * @throws SchedulerException
     */
    @PostMapping
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName,
                       String jobDescription, String cron, Map jobData, Date endTime) throws SchedulerException {
        jobService.addJob(jobClass, jobName, jobGroupName, jobDescription, cron, jobData, endTime);
    }

    /**
     * 修改一个 Job 任务
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @param jobData
     * @throws SchedulerException
     */
    @PutMapping
    public void updateJob(String jobName, String jobGroupName, String cron, Map jobData) throws SchedulerException {
        jobService.updateJob(jobName, jobGroupName, cron, jobData);
    }

    /**
     * 获取一个触发器信息
     * @param jobName
     * @param jobGroupName
     * @return
     * @throws SchedulerException
     */
    @GetMapping("/jobName/{jobName}/jobGroupName/{jobGroupName}")
    public Trigger getTrigger(@PathVariable("jobName") String jobName,
                              @PathVariable("jobGroupName") String jobGroupName) throws SchedulerException {
        return jobService.getTrigger(jobName, jobGroupName);
    }

    /**
     * 删除一个 Job 任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @DeleteMapping("/jobName/{jobName}/jobGroupName/{jobGroupName}")
    public void deleteJob(@PathVariable("jobName") String jobName,
                          @PathVariable("jobGroupName") String jobGroupName) throws SchedulerException {
        jobService.deleteJob(jobName, jobGroupName);
    }

    /**
     * 暂停一个 Job 任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @PutMapping("/jobName/{jobName}/jobGroupName/{jobGroupName}")
    public void pauseJob(@PathVariable("jobName") String jobName,
                         @PathVariable("jobGroupName") String jobGroupName) throws SchedulerException {
        jobService.pauseJob(jobName, jobGroupName);
    }

    /**
     * 恢复一个 Job 任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    public void resumeJob(@PathVariable("jobName") String jobName,
                          @PathVariable("jobGroupName") String jobGroupName) throws SchedulerException {
        jobService.resumeJob(jobName, jobGroupName);
    }

    /**
     * 立即执行一个 Job 任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    public void runJobNow(@PathVariable("jobName") String jobName,
                          @PathVariable("jobGroupName") String jobGroupName) throws SchedulerException {
        jobService.runJobNow(jobName, jobGroupName);
    }
}
