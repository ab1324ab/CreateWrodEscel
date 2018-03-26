package com.myigou.clientService.response;

import java.util.List;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2018/3/25.
 */
public class DataSourceResponse {

    private List<String> dataSource;

    private String status;

    public List<String> getDataSource() {
        return dataSource;
    }

    public DataSourceResponse() {
    }

    public DataSourceResponse(List<String> dataSource) {
        this.dataSource = dataSource;
    }

    public DataSourceResponse(List<String> dataSource, String status) {
        this.dataSource = dataSource;
        this.status = status;
    }

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
