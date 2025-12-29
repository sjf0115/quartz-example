package com.spring.example.config;

/**
 * 功能：Quartz 配置
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 13:11
 */

import com.spring.example.job.StoreJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(StoreJob.class)
                .storeDurably()
                .withIdentity("store-Job", "store-group") // 任务名称/任务分组名称
                .usingJobData("count", 1) // 执行次数
                .build();
    }

    @Bean
    public Trigger trigger () {
        return TriggerBuilder.newTrigger()
                .withIdentity("store-trigger", "store-group") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .forJob(jobDetail())
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .build();
    }
}
