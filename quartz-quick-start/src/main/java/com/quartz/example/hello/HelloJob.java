package com.quartz.example.hello;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：Job 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 12:03
 */
public class HelloJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(HelloJob.class);

    // 定时任务实际执行逻辑
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 从 JobDataMap 获取用户名称
        String name = context.getJobDetail().getJobDataMap().getString("name");
        // 执行具体的业务逻辑
        LOG.info("Welcome {} to Quartz! ", name);
    }
}
