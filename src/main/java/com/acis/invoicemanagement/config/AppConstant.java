package com.acis.invoicemanagement.config;

import org.springframework.http.HttpStatus;

public class AppConstant {
    public static final String APP_NAME = "invoice-management";

    public static final String BEAN_APP_CONFIG = "app-config";

    public static final String BEAN_JDBC_MASTERDATA_POSTGRES = "jdbc-masterdata-postgres";
    public static final String BEAN_DS_MASTERDATA_POSTGRES = "datasource.masterdata-postgres";
    public static final String CONFIG_PROP_MASTERDATA_POSTGRES="datasource.masterdata-postgres";

    public static final String HEADER_ACX_REQUEST_ID = "acx-request-id";
    public static final String HEADER_ACX_REQUEST_ID_BLANK_MESSAGE = "Header 'acx-request-id' is required";
    public static final String HEADER_ACX_REQUEST_AT = "acx-request-at";

    public static final String LOG_START_FORMAT = "[{}][START][METHOD={}][REQUEST={}]";
    public static final String LOG_END_SUCCESS_FORMAT = "[{}][{} - {}][END SUCCESS][{}]";
    public static final String LOG_END_ERROR_FORMAT = "[{}][{}][ERROR][{}]";
    public static final String LOG_END_FORMAT = "[{}][END][METHOD={}][RESPONSE={}]";
    public static final String LOG_END_ERROR_USECASE_FORMAT = "[{}][END][ACTOR={}][STATUS=NOT SUCCESS][METHOD={}][CAUSE={}]";
    public static final String LOG_REQUEST_RECEIVED_FORMAT = "[{}][{}][REQUEST RECEIVED][{}]";
    public static final String LOG_REQUEST_END_FORMAT = "[{}][{}][REQUEST END][{}]";

    public static final String SWADB_INVOICE_NUMBER = "invoice_number";
    public static final String SWADB_TOTAL_AMOUNT = "total_amount";
    public static final String SWADB_ISSUE_DATE = "issue_date";

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_BUSINESS_ERROR = "businessError";
    public static final String STATUS_SYSTEM_ERROR = "systemError";

    public enum STATUS {
        OK,
        FAILED,
        ERROR
    }

    public enum COMPLETION_STATUS {
        SUCCESS,
        BUSINESS_ERROR,
        SYSTEM_ERROR
    }

    public enum RESPONSE {
        OK(HttpStatus.OK, "Ok", "00", STATUS_SUCCESS),

        // 400 Bad Request
        BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request", "01", STATUS_BUSINESS_ERROR),
        INVALID_FORMAT(HttpStatus.BAD_REQUEST, "Invalid Format", "02", STATUS_BUSINESS_ERROR),

        // 404 Not Found
        NOT_FOUND(HttpStatus.NOT_FOUND, "Resource Not Found", "01", STATUS_BUSINESS_ERROR),

        // 409 Conflict
        CONFLICT(HttpStatus.CONFLICT, "TransactionId %s already exists", "01", STATUS_BUSINESS_ERROR),

        // 500 Internal Server Error
        INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "01", STATUS_SYSTEM_ERROR);

        private final HttpStatus httpStatus;
        private final String status;
        private final String code;
        private final String message;

        private RESPONSE(HttpStatus httpStatus, String message, String code, String status) {
            this.httpStatus = httpStatus;
            this.message = message;
            this.code = code;
            this.status = status;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }

        public String getStatus() {
            return status;
        }
    }

}
