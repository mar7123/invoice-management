package com.acis.invoicemanagement.exception;

import com.acis.invoicemanagement.config.AppConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApiFault {
    private AppConstant.COMPLETION_STATUS status;
    private String code;
    private String type;
    private String message;
    private String detail;
    private Object trace;

    @JsonIgnore
    public String error() {
        return code + ":" + type + ":" + message + ":" + detail;
    }

}
