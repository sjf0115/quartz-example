package com.quartz.example.trigger.simple;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 功能：SimpleTrigger 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/7 23:15
 */
public class SimpleTriggerExample1 {

    // 不为 Trigger 设置任务
    public static void triggerNoJob() throws SchedulerException {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务
        JobDetail jobDetail1 = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        // 3. 触发器 不指定任务
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .withRepeatCount(5)) // 重复执行5次
                .build();

        // 4. 需要将任务和触发器一起注册到调度器
        scheduler.scheduleJob(jobDetail1, trigger1);

        // 5. 启动调度器
        scheduler.start();
    }

    // 为 Trigger 设置任务： forJob
    public static void triggerForJob() throws SchedulerException {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务
        JobDetail jobDetail2 = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        // 3. 触发器
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .withRepeatCount(5)) // 重复执行5次
                // .forJob(jobDetail2)
                .forJob("job1", "group1")
                // .forJob(JobKey.jobKey("job1", "group1"))
                .build();

        // 4. 只需要将设置任务的触发器注册到调度器即可
        scheduler.scheduleJob(trigger2);

        // 5. 启动调度器
        scheduler.start();
    }

    public static void main(String[] args) throws Exception {
        // triggerNoJob();
        triggerForJob();
    }
}
