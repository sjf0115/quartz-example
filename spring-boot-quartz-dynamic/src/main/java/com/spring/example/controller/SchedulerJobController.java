package com.spring.example.controller;

import com.spring.example.bean.SchedulerJob;
import com.spring.example.service.SchedulerJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：示例
 * 作者：@SmartSi
 * 博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/12/6 19:15
 */
@Slf4j
@RestController
@RequestMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
public class SchedulerJobController {

    @Autowired
    private SchedulerJobService jobService;

    @PostMapping(value = "/create")
    public String create(@RequestBody SchedulerJob job) {
        try {
            jobService.createJob(job);
            return "创建调度任务成功";
        } catch (Exception e) {
            return "创建调度任务失败：" + e.getMessage();
        }
    }

    @PostMapping(value = "/pause")
    public String pause(@RequestBody SchedulerJob job) {
        try {
            jobService.pauseJob(job);
            return "暂停调度任务成功";
        } catch (Exception e) {
            return "暂停调度任务失败：" + e.getMessage();
        }
    }

    @PostMapping(value = "/resume")
    public String resume(@RequestBody SchedulerJob job) {
        try {
            jobService.resumeJob(job);
            return "恢复调度任务成功";
        } catch (Exception e) {
            return "恢复调度任务失败：" + e.getMessage();
        }
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestBody SchedulerJob job) {
        try {
            jobService.deleteJob(job);
            return "删除调度任务成功";
        } catch (Exception e) {
            return "删除调度任务失败：" + e.getMessage();
        }
    }

    @PostMapping(value = "/reschedule")
    public String reschedule(@RequestBody SchedulerJob job) {
        try {
            jobService.rescheduleJob(job);
            return "更新调度任务成功";
        } catch (Exception e) {
            return "更新调度任务失败：" + e.getMessage();
        }
    }
}
