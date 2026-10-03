package com.acis.invoicemanagement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.model.request.CreateInvoiceRq;
import com.acis.invoicemanagement.model.request.RequestInfo;
import com.acis.invoicemanagement.model.response.ResponseInfo;
import com.acis.invoicemanagement.usecase.InvoiceUsecase;
import com.acis.invoicemanagement.util.CommonUtils;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("invoice/v1")
@Validated
@Slf4j
public class InvoiceController {
        private final InvoiceUsecase invoiceUsecase;

        @Autowired
        public InvoiceController(InvoiceUsecase invoiceUsecase) {
                this.invoiceUsecase = invoiceUsecase;
        }

        @Operation(summary = "Create Invoice")
        @PostMapping("/create-invoice")
        public ResponseEntity<Object> createInvoice(
                        @RequestHeader(value = AppConstant.HEADER_ACX_REQUEST_ID) @NotBlank(message = AppConstant.HEADER_ACX_REQUEST_ID_BLANK_MESSAGE) String requestId,
                        @RequestHeader(value = AppConstant.HEADER_ACX_REQUEST_AT, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date requestAt,
                        @Valid @RequestBody CreateInvoiceRq createInvoiceRq,
                        HttpServletRequest httpServletRequest) {
                RequestInfo requestInfo = CommonUtils.constructRequestInfo(
                                "create-invoice",
                                requestId,
                                createInvoiceRq,
                                httpServletRequest);
                log.info(AppConstant.LOG_REQUEST_RECEIVED_FORMAT, requestInfo.getCorrelationId(),
                                requestInfo.getOperationName(), CommonUtils.gson.toJson(null));
                ResponseInfo<Object> responseInfo = invoiceUsecase.createInvoice(requestInfo,
                                createInvoiceRq);
                log.info(AppConstant.LOG_REQUEST_END_FORMAT, requestInfo.getCorrelationId(),
                                requestInfo.getOperationName(), responseInfo);
                return CommonUtils.buildJsonResponse(responseInfo);

        }

        @Operation(summary = "Get Invoice")
        @GetMapping("/get-invoice")
        public ResponseEntity<Object> getInvoice(
                        @RequestHeader(value = AppConstant.HEADER_ACX_REQUEST_ID) @NotBlank(message = AppConstant.HEADER_ACX_REQUEST_ID_BLANK_MESSAGE) String requestId,
                        @RequestHeader(value = AppConstant.HEADER_ACX_REQUEST_AT, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date requestAt,
                        @RequestParam @NotBlank(message = "Param 'invoice_number' is required") String invoiceNumber,
                        HttpServletRequest httpServletRequest) {
                RequestInfo requestInfo = CommonUtils.constructRequestInfo(
                                "get-invoice",
                                requestId,
                                invoiceNumber,
                                httpServletRequest);
                if (requestAt != null) {
                        requestInfo.setRequestAt(requestAt);
                }
                log.info(AppConstant.LOG_REQUEST_RECEIVED_FORMAT, requestInfo.getCorrelationId(),
                                requestInfo.getOperationName(), CommonUtils.gson.toJson(null));
                ResponseInfo<Object> responseInfo = invoiceUsecase.getInvoice(requestInfo, invoiceNumber);
                log.info(AppConstant.LOG_REQUEST_END_FORMAT, requestInfo.getCorrelationId(),
                                requestInfo.getOperationName(),
                                responseInfo);
                return CommonUtils.buildJsonResponse(responseInfo);
        }

}
