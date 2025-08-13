import request from '@/utils/request'

export function addJob(params) {
    return request({
        url: `/job/job-info/addJob?jobDesc=${params.jobDesc}&scheduleConf=
        ${params.scheduleConf}&executorHandler=${params.executorHandler}&executorParam=${params.executorParam}`,
        method: 'get'
    })
}

export function getJobList(queryParams) {
    return request({
        url: `/job/job-info/getAllJobInfo?pageNum=${queryParams.pageNum}&pageSize=${queryParams.pageSize}`,
        method: 'get',
    })
}

export function deleteJobById(jobId) {
    return request({
        url: `/job/job-info?jobId=${jobId}`,
        method: 'delete'
    })
}

export function updateJob(params) {
    return request({
        url: `/job/job-info/update`,
        method: 'post',
        data: params
    })
}

export function startJob(jobId) {
    return request({
        url: `/job/job-info/start?jobId=${jobId}`,
        method: 'get'
    })
}

export function pauseJob(jobId) {
    return request({
        url: `/job/job-info/pause?jobId=${jobId}`,
        method: 'get'
    })
}
