import request from '@/utils/request'

export function addJob(params) {
    return request({
        url: `/job/job-info/addJob?jobDesc=${params.jobDesc}&scheduleConf=${params.scheduleConf}&executorHandler=${params.executorHandler}`,
        method: 'get'
    })
}

export function getJobList(queryParams) {
    return request({
        url: `/job/job-info/getAllJobInfo?pageNum=${queryParams.pageNum}&pageSize=${queryParams.pageSize}`,
        method: 'get',
    })
}