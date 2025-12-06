package com.spring.example.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 功能：Job 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 12:03
 */
public class HelloJob extends QuartzJobBean {
    private static final Logger LOG = LoggerFactory.getLogger(HelloJob.class);

    // 定时任务实际执行逻辑
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 从 JobDataMap 获取用户名称
        String name = context.getJobDetail().getJobDataMap().getString("name");
        // 执行具体的业务逻辑
        LOG.info("Welcome {} to Quartz! ", name);
    }
}
