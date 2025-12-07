package com.spring.example.service;

import com.spring.example.bean.SchedulerJob;
import com.spring.example.job.HelloJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：调度任务服务类
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 14:15
 */
@Service
public class SchedulerJobService {
    @Autowired
    private Scheduler scheduler;

    /**
     * 创建调度任务
     * @param job
     * @throws Exception
     */
    public void createJob(SchedulerJob job) throws SchedulerException, ClassNotFoundException {
        // 1. 获取任务执行类
        Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(job.getJobClass());

        // 2. 任务实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(job.getJobName(), job.getJobGroup())  // 唯一标识
                .storeDurably()  // 持久化
                .build();

        // 3. 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName() + "_trigger", job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .build();

        // 4. 设置任务参数
        JobDataMap dataMap = jobDetail.getJobDataMap();
        dataMap.put("jobName", job.getJobName());

        // 5. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停调度任务
     * @param job
     */
    public void pauseJob(SchedulerJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复调度任务
     * @param job
     * @throws SchedulerException
     */
    public void resumeJob(SchedulerJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除调度任务
     */
    public void deleteJob(SchedulerJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 更新调度任务 -- Cron 表达式
     * @param job
     * @throws Exception
     */
    public void rescheduleJob(SchedulerJob job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName() + "_trigger", job.getJobGroup());
        // 旧触发器
        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (oldTrigger == null) {
            throw new RuntimeException("触发器不存在,无法更新");
        }

        // 新触发器
        CronTrigger newTrigger = oldTrigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .build();

        // 替换触发器
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }
}
