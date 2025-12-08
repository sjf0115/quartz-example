package com.quartz.example.trigger.simple;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：SimpleTrigger 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/7 23:15
 */
public class SimpleTrigger4Example {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleTrigger4Example.class);

    public static void main(String[] args) throws Exception {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务实例
        JobDetail jobDetail= JobBuilder.newJob(SimpleTriggerJob.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        // 3. 触发器
        // 立即触发，每10秒执行一次 无限次重复执行直到15:45
        Trigger trigger4 = TriggerBuilder.newTrigger()
                .withIdentity("trigger4", "group1")
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .repeatForever()) // 重复执行
                .endAt(DateBuilder.dateOf(15, 45, 0)) // 直到 15:45
                .build();

        // 4. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail, trigger4);

        // 5. 启动调度器
        scheduler.start();

        LOG.info("调度器启动成功，任务开始执行...");
    }
}
