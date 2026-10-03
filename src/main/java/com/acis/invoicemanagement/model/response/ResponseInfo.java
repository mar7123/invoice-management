package com.acis.invoicemanagement.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.exception.ApiFault;
import com.acis.invoicemanagement.exception.CommonException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo<T> {
    private String correlationId;
    private final ResponseBody<T> body = new ResponseBody<>();
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private List<ApiFault> faults;

    /**
     * set success without set data
     */
    public void setSuccess() {
        this.httpStatus = HttpStatus.OK;
        body.setCode(AppConstant.RESPONSE.OK.getCode());
        body.setMessage(AppConstant.RESPONSE.OK.getMessage());
        body.setStatus(AppConstant.RESPONSE.OK.getStatus());
    }

    public void setSuccessAsync() {
        this.httpStatus = HttpStatus.ACCEPTED;
        body.setCode(AppConstant.RESPONSE.OK.getCode());
        body.setMessage(AppConstant.RESPONSE.OK.getMessage());
        body.setStatus(AppConstant.RESPONSE.OK.getStatus());
    }

    public void setSuccessMsg(String message) {
        this.httpStatus = HttpStatus.OK;
        body.setCode(AppConstant.RESPONSE.OK.getCode());
        body.setMessage(message);
        body.setStatus(AppConstant.RESPONSE.OK.getStatus());
    }

    public void setMessage(String message) {
        body.setMessage(message);
    }

    /**
     * set success with data
     * 
     * @param data {@link T} (generic)
     */
    public void setSuccess(T data) {
        body.setData(data);
        setSuccess();
    }

    /**
     * set exception using enum
     * 
     * @param errorDescription error message
     */
    public void setException(AppConstant.RESPONSE errorDescription) {
        setCommonException(new Exception(errorDescription.getMessage()), errorDescription);
    }

    public void setCommonException(Exception e, AppConstant.RESPONSE response) {
        this.httpStatus = response.getHttpStatus();
        String status = response.getStatus();
        body.setStatus(status);
        body.setCode(response.getCode());
        body.setMessage(response.getMessage());
        addException(e);
    }

    /**
     * set response based on common exception
     * 
     * @param e {@link CommonException}
     */
    public void setCommonException(CommonException e) {
        this.httpStatus = e.getStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        String status = e.getStatus().equals(AppConstant.COMPLETION_STATUS.SYSTEM_ERROR)
                ? AppConstant.STATUS_SYSTEM_ERROR
                : AppConstant.STATUS_BUSINESS_ERROR;
        body.setStatus(status);
        body.setCode(e.getCode());
        body.setMessage(e.getDisplayMessage());
        addException(e);
    }

    /**
     * set exception using error message
     * 
     * @param errorDescription error message
     */
    public void setException(String errorDescription) {
        setException(new Exception(errorDescription));
    }

    /**
     * set exception using java exception
     * 
     * @param e {@link Exception}
     */
    public void setException(Exception e) {
        if (e instanceof CommonException) {
            setCommonException((CommonException) e);
        } else {
            setCommonException(new CommonException(e));
        }
    }

    /**
     * add exception into list of common exception
     * 
     * @param e {@link Exception}
     */
    public void addException(Exception e) {
        addException(new CommonException(e));
    }

    /**
     * add common exception
     * 
     * @param e {@link CommonException}
     */
    public void addException(CommonException e) {
        if (this.faults == null) {
            this.faults = new ArrayList<>();
        }
        this.faults.add(e.getApiFault());
    }

    /**
     * get list of exception message
     * 
     * @return {@link List<String>}
     */
    public List<String> getExceptionMessages() {
        if (faults == null) {
            return new ArrayList<>();
        }
        return faults.stream()
                .map(ApiFault::getDetail)
                .collect(Collectors.toList());
    }

    /**
     * check is error
     * 
     * @return true/false
     */
    public boolean isError() {
        return faults != null && !faults.isEmpty() && !httpStatus.is2xxSuccessful();
    }

    /**
     * add header
     * 
     * @param param param
     * @param value value
     */
    public void addHeader(String param, String value) {
        if (this.httpHeaders == null) {
            this.httpHeaders = new HttpHeaders();
        }
        this.httpHeaders.add(param, value);
    }

    public void addApiFaults(ApiFault fault) {
        if (this.faults == null) {
            this.faults = new ArrayList<>();
        }
        this.faults.add(fault);
    }

}
