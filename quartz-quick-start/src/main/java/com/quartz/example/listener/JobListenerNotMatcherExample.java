package com.quartz.example.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.matchers.NotMatcher;
import org.quartz.impl.matchers.OrMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：注册局部作业监听器 NotMatcher 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/7 08:40
 */
public class JobListenerNotMatcherExample {
    private static final Logger LOG = LoggerFactory.getLogger(JobListenerNotMatcherExample.class);

    public static void main(String[] args) throws SchedulerException {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务实例
        JobDetail jobDetail1 = JobBuilder.newJob(Job1.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        JobDetail jobDetail2 = JobBuilder.newJob(Job2.class)
                .withIdentity("job2", "group2") // 任务名称/任务分组名称
                .build();

        // 3. 触发器
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .withRepeatCount(3)) // 每10秒执行一次，重复执行3次
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group2") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder
                        .repeatSecondlyForTotalCount(3, 20)) // 每20秒执行一次，重复执行3次
                .build();

        // 4. 监听器
        MyJobListener myJobListener = new MyJobListener();
        scheduler.getListenerManager().addJobListener(
                myJobListener,
                NotMatcher.not(KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1")))
        );

        // 5. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail1, trigger1);
        scheduler.scheduleJob(jobDetail2, trigger2);

        // 6. 启动调度器
        scheduler.start();

        LOG.info("调度器启动成功，任务开始执行...");
    }
}
