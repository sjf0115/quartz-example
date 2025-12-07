package com.quartz.example.listener.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：JobListener 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/7 19:45
 */
public class MyJobListener implements JobListener {
    private static final Logger LOG = LoggerFactory.getLogger(MyJobListener.class);

    @Override
    public String getName() {
        // 监听器名称
        return "MyJobListener";
    }

    // 任务即将执行时调用（在任务执行前）
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().toString();
        Date fireTime = context.getFireTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info("任务 [{}] 开始执行，触发时间: {}", jobName, dateFormat.format(fireTime));
        // 记录开始时间用于计算执行时长
        context.put("startTime", System.currentTimeMillis());
    }

    // 任务执行被否决时调用（当 TriggerListener 否决任务执行时）
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().toString();
        LOG.warn("任务 [{}] 执行被否决", jobName);
    }

    // 任务执行完成后调用（无论成功或失败）
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().toString();
        // 任务开始执行时间
        Long startTime = (Long) context.get("startTime");
        // 任务执行时长
        Long executionTime = startTime != null ? System.currentTimeMillis() - startTime : null;

        if (jobException != null) {
            // 执行失败
            LOG.error("任务 [{}] 执行失败，耗时: {}ms，异常: {}", jobName, executionTime, jobException.getMessage(), jobException);
            // 失败重试逻辑
            // handleRetry(context, jobException);
            // 发送失败告警
            // sendAlert("任务执行失败", context, jobException);
        } else {
            // 执行成功
            LOG.info("任务 [{}] 执行成功，耗时: {}ms", jobName, executionTime);
            // 记录成功执行的统计信息
            // recordSuccessStatistics(context);
        }
    }
}
