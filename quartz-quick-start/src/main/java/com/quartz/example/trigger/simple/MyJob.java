package com.quartz.example.trigger.simple;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：Job 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 12:03
 */
public class MyJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(MyJob.class);

    // 定时任务实际执行逻辑
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().toString();
        // 执行具体的业务逻辑
        LOG.info("Welcome To Quartz: {}", jobName);
    }
}
