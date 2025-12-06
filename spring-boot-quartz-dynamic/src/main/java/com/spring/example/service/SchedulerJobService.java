package com.spring.example.service;

import org.quartz.Scheduler;

/**
 * 功能：示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 14:15
 */
public class SchedulerJobService {

    public void createJob() {
    }

    public void resumeJob(Scheduler scheduler, String jobName) {
        Job
        scheduler.resumeJob(jobName);
    }

}
