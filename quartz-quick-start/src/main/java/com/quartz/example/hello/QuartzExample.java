package com.quartz.example.hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：Quartz 入门示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 12:10
 */
public class QuartzExample {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzExample.class);

    public static void main(String[] args) throws Exception {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务实例
        JobDetail jobDetail= JobBuilder.newJob(HelloJob.class)
                .withIdentity("helloJob", "task_group_1") // 任务名称/任务分组名称
                .usingJobData("name", "Lucy") // 设置任务参数
                .build();

        // 3. 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("helloTrigger", "trigger_group_1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .withRepeatCount(5)) // 重复执行5次
                .build();

        // 4. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail, trigger);

        // 5. 启动调度器
        scheduler.start();

        LOG.info("调度器启动成功，任务开始执行...");
    }
}
