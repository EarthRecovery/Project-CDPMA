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

    @GetMapping("/getAllJobInfo")
    public AjaxResult getAllJobInfo(@RequestParam String pageNum,
                                    @RequestParam String pageSize) {
        return AjaxResult.success(xxlJobUtils.getAllJobs(Integer.parseInt(pageNum), Integer.parseInt(pageSize)));
    }
}
