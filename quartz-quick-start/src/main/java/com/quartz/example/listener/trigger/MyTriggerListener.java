package com.quartz.example.listener.trigger;

import com.quartz.example.listener.job.MyJobListener;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：TriggerListener 示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/7 22:33
 */
public class MyTriggerListener implements TriggerListener {
    private static final Logger LOG = LoggerFactory.getLogger(MyTriggerListener.class);

    @Override
    public String getName() {
        return "MyTriggerListener";
    }

    // 触发器触发时
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        String triggerName = trigger.getKey().toString();
        String jobName = context.getJobDetail().getKey().toString();

        Date startTime = trigger.getStartTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info("触发器 [{}] 已触发，关联任务: [{}], 触发时间: {}", triggerName, jobName, dateFormat.format(startTime));
    }

    // 执行否决权
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    // 处理错过触发
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    // 触发器执行完成
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
