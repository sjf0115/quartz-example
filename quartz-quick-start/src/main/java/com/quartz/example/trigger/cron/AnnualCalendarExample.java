package com.quartz.example.trigger.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.GregorianCalendar;

/**
 * 功能：基于日历的排除规则示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/17 23:20
 */
public class AnnualCalendarExample {
    private static final Logger LOG = LoggerFactory.getLogger(AnnualCalendarExample.class);

    public static void main(String[] args) throws Exception {
        // 1. 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 任务实例
        JobDetail jobDetail = JobBuilder.newJob(CronTriggerJob.class)
                .withIdentity("job1", "group1") // 任务名称/任务分组名称
                .build();

        // 3. 创建排除节假日的触发器
        AnnualCalendar holidays = new AnnualCalendar();
        GregorianCalendar fourthOfJuly = new GregorianCalendar(2025, 12, 16);
        holidays.setDayExcluded(fourthOfJuly, true);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", "group")
                .startNow() // 立即开始
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/10 * * * * ?")
                )
                .modifiedByCalendar("holidays") // 排除节假日
                .build();

        // 4. 将任务和触发器注册到调度器
        scheduler.scheduleJob(jobDetail, trigger);

        // 5. 启动调度器
        scheduler.start();

        LOG.info("调度器启动成功，任务开始执行...");
    }
}
