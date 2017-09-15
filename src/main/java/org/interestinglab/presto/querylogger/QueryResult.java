package org.interestinglab.presto.querylogger;

import java.time.Duration;


public class QueryResult {

    private String queryId;
    private String sql;

    private long queuedTimeMs;
    private long submissionTimeMs;
    private long completionTimeMs;
    private long elapsedTimeMs;

    private String catalog;
    private String schema;
    private String remoteClientAddress;
    private String serverAddress;

    private String state;
    private int errorCode;
    private String errorCodeName;
    private String errorType;
    private String errorMessage;
    private String errorTask;
    private String errorHost;
    private String errorJson;

    private long cpuTime; // Cpu Time in seconds.
    private long peakMemoryBytes;
    private long totalBytes; // Raw Input Data ?
    private long totalRows;
    private int completedSplits;


    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public long getElapsedTimeMs() {
        return elapsedTimeMs;
    }

    public void setElapsedTimeMs(long elapsedTimeMs) {
        this.elapsedTimeMs = elapsedTimeMs;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getPeakMemoryBytes() {
        return peakMemoryBytes;
    }

    public void setPeakMemoryBytes(long peakMemoryBytes) {
        this.peakMemoryBytes = peakMemoryBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public long getSubmissionTimeMs() {
        return submissionTimeMs;
    }

    public void setSubmissionTimeMs(long submissionTimeMs) {
        this.submissionTimeMs = submissionTimeMs;
    }

    public long getCompletionTimeMs() {
        return completionTimeMs;
    }

    public void setCompletionTimeMs(long completionTimeMs) {
        this.completionTimeMs = completionTimeMs;
    }

    public long getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(long cpuTime) {
        this.cpuTime = cpuTime;
    }

    public String getRemoteClientAddress() {
        return remoteClientAddress;
    }

    public void setRemoteClientAddress(String remoteClientAddress) {
        this.remoteClientAddress = remoteClientAddress;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public long getQueuedTimeMs() {
        return queuedTimeMs;
    }

    public void setQueuedTimeMs(long queuedTimeMs) {
        this.queuedTimeMs = queuedTimeMs;
    }

    public int getCompletedSplits() {
        return completedSplits;
    }

    public void setCompletedSplits(int completedSplits) {
        this.completedSplits = completedSplits;
    }

    public String getErrorCodeName() {
        return errorCodeName;
    }

    public void setErrorCodeName(String errorCodeName) {
        this.errorCodeName = errorCodeName;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorTask() {
        return errorTask;
    }

    public void setErrorTask(String errorTask) {
        this.errorTask = errorTask;
    }

    public String getErrorHost() {
        return errorHost;
    }

    public void setErrorHost(String errorHost) {
        this.errorHost = errorHost;
    }

    public String getErrorJson() {
        return errorJson;
    }

    public void setErrorJson(String errorJson) {
        this.errorJson = errorJson;
    }
}
