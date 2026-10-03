package com.acis.invoicemanagement.exception;

import org.springframework.http.HttpStatus;

import com.acis.invoicemanagement.config.AppConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends Exception {
    private AppConstant.COMPLETION_STATUS status;
    private String code;
    private String type;
    private String displayMessage;
    private HttpStatus httpStatus;

    public CommonException(AppConstant.COMPLETION_STATUS status, String code, String type, String displayMessage,
            String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.type = type;
        this.displayMessage = displayMessage;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CommonException(AppConstant.COMPLETION_STATUS status, HttpStatus httpStatus, String code, String type,
            String displayMessage, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.status = status;
        this.code = code;
        this.type = type;
        this.displayMessage = displayMessage;
    }

    public CommonException(Exception e) {
        super(e.getMessage());
        this.status = AppConstant.COMPLETION_STATUS.SYSTEM_ERROR;
        this.code = "99";
        this.type = e.getClass().getSimpleName();
        this.displayMessage = "Unknown Error: " + e.getClass().getSimpleName();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiFault getApiFault() {
        return ApiFault.builder()
                .status(status)
                .code(code)
                .type(type)
                .message(displayMessage)
                .detail(type + ":" + super.getMessage())
                .build();
    }
}
