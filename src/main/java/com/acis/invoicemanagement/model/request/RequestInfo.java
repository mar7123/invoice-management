package com.acis.invoicemanagement.model.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {
    private String appName;
    private String operationName;
    private String uri;
    private String method;
    private String host;
    private String instanceId;
    private String requestId;
    private Date requestAt;
    private Date requestEnd;
    private String correlationId;
    private long duration;
    private String queryParam;
    private Object requestHeaders;
    private Object requestPayload;
    private Object responseHeaders;
    private Object responsePayload;
    private String traceId;

    @Builder(builderMethodName = "newBuilder")
    public RequestInfo(String appName, String operationName, String uri, String method, String host, String instanceId,
            String requestId, Date requestAt,
            Date requestEnd, String correlationId, long duration, String queryParam, Object requestHeaders,
            Object requestPayload,
            Object responseHeaders, Object responsePayload, Object miscellaneous, String statusCode,
            String traceId) {
        this.appName = appName;
        this.operationName = operationName;
        this.uri = uri;
        this.method = method;
        this.instanceId = instanceId;
        this.host = host;
        this.requestId = requestId;
        this.requestAt = requestAt;
        this.requestEnd = requestEnd;
        this.correlationId = correlationId;
        this.duration = duration;
        this.queryParam = queryParam;
        this.requestHeaders = requestHeaders;
        this.requestPayload = requestPayload;
        this.responseHeaders = responseHeaders;
        this.responsePayload = responsePayload;
        this.traceId = traceId;
    }
}
