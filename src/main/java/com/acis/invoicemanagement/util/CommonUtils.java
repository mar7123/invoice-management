package com.acis.invoicemanagement.util;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.model.request.RequestInfo;
import com.acis.invoicemanagement.model.response.ResponseInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {
    public static final Gson gson = new GsonBuilder().create();

    public static RequestInfo constructRequestInfo(String operation,
            String requestId,
            Object requestPayload,
            HttpServletRequest servletRequest) {
        if (servletRequest != null) {
            Map<String, String> headerMap = new HashMap<>();
            Enumeration<String> headerNames = servletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = servletRequest.getHeader(key);
                headerMap.put(key, value);
            }

            return RequestInfo.newBuilder()
                    .appName(AppConstant.APP_NAME)
                    .operationName(operation)
                    .uri(servletRequest.getRequestURI())
                    .method(servletRequest.getMethod())
                    .host(servletRequest.getRemoteAddr())
                    .requestId(requestId)
                    .requestAt(Calendar.getInstance().getTime())
                    .correlationId(UUID.randomUUID().toString())
                    .queryParam(servletRequest.getQueryString())
                    .requestHeaders(headerMap)
                    .requestPayload(requestPayload)
                    .build();
        } else {
            return RequestInfo.newBuilder()
                    .appName(AppConstant.APP_NAME)
                    .operationName(operation)
                    .requestId(requestId)
                    .requestAt(Calendar.getInstance().getTime())
                    .correlationId(UUID.randomUUID().toString())
                    .requestPayload(requestPayload)
                    .build();
        }
    }

    public static Object jsonBody(Object body) {
        if (body instanceof CharSequence s) {
            return Map.of("message", s.toString());
        }
        return body;
    }

    public static ResponseEntity<Object> buildJsonResponse(ResponseInfo<Object> responseInfo) {
        return ResponseEntity.status(responseInfo.getHttpStatus())
                .headers(responseInfo.getHttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonUtils.jsonBody(responseInfo.getBody()));
    }

    public static String toJsonCompact(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String raw) {
            String trimmed = raw.trim();
            if ((trimmed.startsWith("{") && trimmed.endsWith("}"))
                    || (trimmed.startsWith("[") && trimmed.endsWith("]"))) {
                return trimmed;
            }
        }
        return gson.toJson(obj);
    }

}
