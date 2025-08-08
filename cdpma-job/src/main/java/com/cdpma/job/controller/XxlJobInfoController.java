package com.cdpma.job.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.job.domain.XxlJobInfo;
import com.cdpma.common.job.utils.XxlJobUtils;
import com.cdpma.job.service.impl.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-info")
public class XxlJobInfoController extends BaseController {

    @Autowired
    private XxlJobUtils xxlJobUtils;

    @Autowired
    private JobInfoService jobInfoService;

    @PostMapping("/add")
    public AjaxResult addJobInfo(@RequestBody XxlJobInfo xxlJobInfo) {
        xxlJobUtils.add(xxlJobInfo);
        return AjaxResult.success("新增任务成功");
    }

    @GetMapping("/addJob")
    public AjaxResult addJob(@RequestParam String jobDesc,
                             @RequestParam String scheduleConf,
                             @RequestParam String executorHandler) {
        jobInfoService.addJob(jobDesc, scheduleConf, executorHandler);
        return AjaxResult.success("新增任务成功");
    }

    @DeleteMapping
    public AjaxResult deleteJobInfo(@RequestParam int jobId) {
        xxlJobUtils.remove(jobId);
        return AjaxResult.success("删除任务成功");
    }

    @GetMapping("/getAllJobInfo")
    public AjaxResult getAllJobInfo(@RequestParam String pageNum,
                                    @RequestParam String pageSize) {
        return AjaxResult.success(xxlJobUtils.getAllJobs(Integer.parseInt(pageNum), Integer.parseInt(pageSize)));
    }

    @PostMapping("/update")
    public AjaxResult updateJobInfo(@RequestBody XxlJobInfo xxlJobInfo) {
        xxlJobUtils.update(xxlJobInfo);
        return AjaxResult.success("更新任务成功");
    }

    @GetMapping("/start")
    public AjaxResult startJob(@RequestParam int jobId) {
        xxlJobUtils.start(jobId);
        return AjaxResult.success("任务启动成功");
    }

    @GetMapping("/pause")
    public AjaxResult pauseJob(@RequestParam int jobId) {
        xxlJobUtils.pause(jobId);
        return AjaxResult.success("任务暂停成功");
    }
}
