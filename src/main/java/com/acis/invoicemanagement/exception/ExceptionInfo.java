package com.acis.invoicemanagement.exception;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ExceptionInfo {
    @SerializedName("http_code")
    private int httpCode;
    private String status;
    private String code;
    private String message;
}
