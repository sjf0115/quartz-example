package com.quartz.example.trigger.simple;

import com.quartz.example.hello.HelloJob;
import com.quartz.example.hello.QuartzExample;
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
public class SimpleTriggerExample {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzExample.class);

    public static void main(String[] args) throws Exception {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务实例
        JobDetail jobDetail= JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        // 3. 触发器
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .withRepeatCount(5)) // 重复执行5次
                .build();

        // 5分钟以后开始触发，仅执行一次：
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1") // 触发器名称/触发器分组名称
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE)) // 5分钟以后开始触发
                .build();

        // 立即触发，每个5分钟执行一次，直到22:00
        Trigger trigger3 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .repeatForever()) // 重复执行
                .endAt(DateBuilder.dateOf(22, 0, 0)) // 直到22:00
                .build();

        // 4. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail, trigger1);

        // 5. 启动调度器
        scheduler.start();

        LOG.info("调度器启动成功，任务开始执行...");
    }
}
