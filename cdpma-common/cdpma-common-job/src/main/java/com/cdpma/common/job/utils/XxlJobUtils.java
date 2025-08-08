package com.cdpma.common.job.utils;

import com.cdpma.common.job.constant.XxlJobConstant;
import com.cdpma.common.job.domain.XxlJobInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Component
public class XxlJobUtils {
    private String adminAddresses = XxlJobConstant.XXL_JOB_ADMIN_ADDRESS;

    private String appname = XxlJobConstant.XXL_JOB_APP_NAME;

    private RestTemplate restTemplate = new RestTemplate();

    private static final String ADD_URL = "/jobinfo/addJob";
    private static final String UPDATE_URL = "/jobinfo/updateJob";
    private static final String REMOVE_URL = "/jobinfo/removeJob";
    private static final String PAUSE_URL = "/jobinfo/pauseJob";
    private static final String START_URL = "/jobinfo/startJob";
    private static final String ADD_START_URL = "/jobinfo/addAndStart";
    private static final String GET_GROUP_ID = "/jobgroup/getGroupId";
    private static final String LIST_ID = "/jobinfo/pageList";


    public String add(XxlJobInfo jobInfo){
        // 查询对应groupId:
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(param);
        String result = doPost(adminAddresses + GET_GROUP_ID, json);

        JSONObject jsonObject = JSON.parseObject(result);
        String groupId = jsonObject.getString("content");
        jobInfo.setJobGroup(Integer.parseInt(groupId));
        String json2 = JSON.toJSONString(jobInfo);
        System.out.println(json2);
        return doPost(adminAddresses + ADD_URL, json2);
    }

    public String update(XxlJobInfo jobInfo){
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(param);
        String result = doPost(adminAddresses + GET_GROUP_ID, json);

        JSONObject jsonObject = JSON.parseObject(result);
        String groupId = jsonObject.getString("content");
        jobInfo.setJobGroup(Integer.parseInt(groupId));
        String json2 = JSON.toJSONString(jobInfo);
        return doPost(adminAddresses + UPDATE_URL, json2);
    }

    public String remove(int id){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + REMOVE_URL, json);
    }

    public String pause(int id){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + PAUSE_URL, json);
    }

    public String start(int id){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + START_URL, json);
    }

    public String getAllJobs(int pageNum, int pageSize){
        Map<String,Object> param = new HashMap<>();
        param.put("jobGroup", 4);
        param.put("triggerStatus", -1); // -1表示查询所有状态
        param.put("start", pageNum * pageSize - pageSize); // 计算起始位置
        param.put("length", pageSize);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + LIST_ID, json);
    }

    public String addAndStart(XxlJobInfo jobInfo){
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(param);
        String result = doPost(adminAddresses + GET_GROUP_ID, json);

        JSONObject jsonObject = JSON.parseObject(result);
        String groupId = jsonObject.getString("content");
        jobInfo.setJobGroup(Integer.parseInt(groupId));
        String json2 = JSON.toJSONString(jobInfo);

        return doPost(adminAddresses + ADD_START_URL, json2);
    }

    public String doPost(String url, String json){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json ,headers);
        System.out.println("Request URL: " + url);
        System.out.println("Request Body: " + json);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        if(stringResponseEntity.getBody() == null) {
            return "Error: Response body is null";
        }
        return stringResponseEntity.getBody().toString();
    }
}
