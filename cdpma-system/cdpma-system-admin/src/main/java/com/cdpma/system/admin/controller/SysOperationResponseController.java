package com.cdpma.system.admin.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperationResponse;
import com.cdpma.system.admin.service.ISysOperationResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation-response")
public class SysOperationResponseController extends BaseController {
    @Autowired
    private ISysOperationResponseService responseService;

    @GetMapping("/{responseId}")
    public AjaxResult getResponse(@PathVariable Long responseId) {
        return AjaxResult.success(responseService.selectResponseById(responseId));
    }

    @PostMapping("/list")
    public AjaxResult list(@RequestBody SysOperationResponse response) {
        List<SysOperationResponse> list = responseService.selectResponseList(response);
        return AjaxResult.success(list);
    }

    @GetMapping("/all")
    public AjaxResult getAllResponses() {
        List<SysOperationResponse> list = responseService.selectAllResponses();
        System.out.println("Fetching all operation responses: " + list);
        return AjaxResult.success(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysOperationResponse response) {
        responseService.insertResponse(response);
        return AjaxResult.success("新增操作响应成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysOperationResponse response) {
        responseService.updateResponse(response);
        return AjaxResult.success("修改操作响应成功");
    }

    @DeleteMapping("/{responseIds}")
    public AjaxResult remove(@PathVariable Long[] responseIds) {
        responseService.deleteResponseByIds(responseIds);
        return AjaxResult.success("删除操作响应成功");
    }
}
