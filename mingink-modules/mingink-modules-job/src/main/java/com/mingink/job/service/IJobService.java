package com.mingink.job.service;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.Date;
import java.util.Map;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/8 22:31
 */
public interface IJobService {
    void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName,
           String jobDescription, String cron, Map jobData, Date endTime) throws SchedulerException;


    Trigger getTrigger(String jobName, String jobGroupName) throws SchedulerException;

    void deleteJob(String jobName, String jobGroupName) throws SchedulerException;

    void pauseJob(String jobName, String jobGroupName) throws SchedulerException;

    void resumeJob(String jobName, String jobGroupName) throws SchedulerException;

    void runJobNow(String jobName, String jobGroupName) throws SchedulerException;

    void updateJob(String jobName, String jobGroupName, String cron, Map jobData) throws SchedulerException;
}
