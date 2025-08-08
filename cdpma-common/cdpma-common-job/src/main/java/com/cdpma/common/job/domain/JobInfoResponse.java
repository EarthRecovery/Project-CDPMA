package com.cdpma.common.job.domain;

import java.util.List;

public class JobInfoResponse {
    private int recordsFiltered;
    private List<XxlJobInfo> data;
    private int recordsTotal;

    // Getter 和 Setter
    public int getRecordsFiltered() {
        return recordsFiltered;
    }
    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<XxlJobInfo> getData() {
        return data;
    }
    public void setData(List<XxlJobInfo> data) {
        this.data = data;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }
    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
}
