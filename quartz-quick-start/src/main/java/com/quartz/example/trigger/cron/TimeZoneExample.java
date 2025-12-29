package com.quartz.example.trigger.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

/**
 * 功能：时区示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/17 23:03
 */
public class TimeZoneExample {
    private static final Logger LOG = LoggerFactory.getLogger(TimeZoneExample.class);

    public static void main(String[] args) throws Exception {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务实例
        JobDetail jobDetail= JobBuilder.newJob(CronTriggerJob.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        // 3. 触发器
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York"); // 特定时区
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", "group")
                .startNow() // 立即开始
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/10 0-2 * * * ?")
                        .inTimeZone(timeZone)
                ) // 每小时的0-2分钟每隔10秒执行一次
                .build();

        // 4. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail, trigger);

        // 5. 启动调度器
        scheduler.start();

        LOG.info("调度器启动成功，任务开始执行...");
    }
}
