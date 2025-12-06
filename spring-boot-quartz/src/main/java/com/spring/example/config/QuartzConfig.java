package com.spring.example.config;

/**
 * 功能：Quartz 配置
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 13:11
 */

import com.spring.example.job.HelloJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(HelloJob.class)
                .storeDurably()
                .withIdentity("helloJob", "task_group_1") // 任务名称/任务分组名称
                .usingJobData("name", "Lucy") // 设置任务参数
                .build();
    }

    @Bean
    public Trigger trigger () {
        return TriggerBuilder.newTrigger()
                .withIdentity("helloTrigger", "trigger_group_1") // 触发器名称/触发器分组名称
                .startNow() // 立即开始
                .forJob(jobDetail())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // 每10秒执行一次
                        .withRepeatCount(5)) // 重复执行5次
                .build();
    }
}
