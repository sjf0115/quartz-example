package com.quartz.example.listener.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：任务1 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/7 19:44
 */
public class Job2 implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(Job2.class);

    @Override
    // 定时任务实际执行逻辑
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        String jobName = jobKey.getName();
        String jobGroup = jobKey.getGroup();
        // 执行具体的业务逻辑
        try {
            // 模拟执行耗时
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Welcome To Quartz: {}.{}", jobName, jobGroup);
    }
}
