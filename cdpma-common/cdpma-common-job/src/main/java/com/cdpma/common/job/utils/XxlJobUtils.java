package com.cdpma.common.job.utils;

import com.cdpma.common.core.exception.ServiceException;
import com.cdpma.common.job.constant.XxlJobConstant;
import com.cdpma.common.job.domain.JobInfoResponse;
import com.cdpma.common.job.domain.XxlJobInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

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
    private static final String LIST_ID = "/jobinfo/getJobs";


    public String add(XxlJobInfo jobInfo){
        // 查询对应groupId:
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(param);
        String result = doPost(adminAddresses + GET_GROUP_ID, json);

        JSONObject jsonObject = JSON.parseObject(result);
        String groupId = jsonObject.getString("content");
        jobInfo.setJobGroup(Integer.parseInt(groupId));
        jobInfo.setTriggerStatus(0); // 设置触发状态为未触发
        String json2 = JSON.toJSONString(jobInfo);
        return doPost(adminAddresses + ADD_URL, json2);
    }

    public String update(XxlJobInfo jobInfo){
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(jobInfo);
        return doPost(adminAddresses + UPDATE_URL, json);
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

    public List<XxlJobInfo> getAllJobs(int pageNum, int pageSize){
        Map<String,Object> param = new HashMap<>();
        param.put("jobGroup", 4);
        param.put("start", pageNum * pageSize - pageSize); // 计算起始位置
        param.put("length", pageSize);
        return doGet(adminAddresses + LIST_ID, param);
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
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        if(stringResponseEntity.getBody() == null) {
            return "Error: Response body is null";
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = new HashMap<>();
        try{
            resultMap = mapper.readValue(stringResponseEntity.getBody(), Map.class);
        }catch(JsonProcessingException e){
            System.out.println("Error parsing JSON response: " + e.getMessage());
        }
        if(!Objects.equals(String.valueOf(resultMap.get("code")), "200")){
            throw new ServiceException("Error: " + resultMap.get("msg"));
        }
        return stringResponseEntity.getBody().toString();
    }

    public List<XxlJobInfo> doGet(String url, Map<String, Object> params) {
        // 拼接 URL 参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        String finalUrl = builder.toUriString();
        System.out.println("Request URL: " + finalUrl);

        // 发送 GET 请求
        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

        if (response.getBody() == null) {
            System.out.println("Error: Response body is null");
            throw new RuntimeException("Response body is null");
        }

        // 解析 JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            String raw = response.getBody();
            JsonNode root = mapper.readTree(raw);
            JsonNode dataNode = root.get("data");

            if (dataNode != null && dataNode.isArray()) {
                // 遍历数组，解析成 JobInfo 对象列表
                List<XxlJobInfo> jobs = new ArrayList<>();
                for (JsonNode jobNode : dataNode) {
                    XxlJobInfo job = mapper.treeToValue(jobNode, XxlJobInfo.class);
                    jobs.add(job);
                }

                System.out.println("Total Jobs: " + root.get("recordsTotal").asInt());
                for (XxlJobInfo job : jobs) {
                    System.out.println("Job: " + job.getJobDesc());
                }
                return jobs;
            } else {
                System.out.println("data 节点为空或不是数组");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }

        return null;
    }

}
