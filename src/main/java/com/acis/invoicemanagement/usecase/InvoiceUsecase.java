package com.acis.invoicemanagement.usecase;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.exception.CommonException;
import com.acis.invoicemanagement.model.db.Invoice;
import com.acis.invoicemanagement.model.request.CreateInvoiceRq;
import com.acis.invoicemanagement.model.request.RequestInfo;
import com.acis.invoicemanagement.model.response.CreateInvoiceRs;
import com.acis.invoicemanagement.model.response.ResponseInfo;
import com.acis.invoicemanagement.repository.InvoiceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceUsecase extends BaseUsecase {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public ResponseInfo<Object> createInvoice(RequestInfo requestInfo, CreateInvoiceRq createInvoiceRq) {
        log.info(AppConstant.LOG_START_FORMAT, requestInfo.getCorrelationId(),
                getClass().getSimpleName(), createInvoiceRq);
        ResponseInfo<Object> responseInfo = new ResponseInfo<>();
        CreateInvoiceRs createInvoiceRs = new CreateInvoiceRs();
        try {
            Invoice invoice = constructInvoiceEntity(requestInfo, createInvoiceRq);
            ResponseInfo<Void> insertRs = invoiceRepository.insert(requestInfo, invoice);
            if (insertRs.isError()) {
                throw new CommonException(AppConstant.COMPLETION_STATUS.SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
                        "99", "SystemError", "Failed Insert To Invoice",
                        "Failed Insert to Invoice");
            }
            createInvoiceRs.setInvoiceNumber(invoice.getInvoiceNumber());
            responseInfo.setSuccess(createInvoiceRs);
        } catch (Exception e) {
            responseInfo.setException(e);
        }
        log.info(AppConstant.LOG_END_FORMAT, requestInfo.getCorrelationId(), requestInfo.getOperationName(),
                getClass().getSimpleName(), responseInfo);
        return responseInfo;
    }

    public ResponseInfo<Object> getInvoice(RequestInfo requestInfo, String invoiceNumber) {
        log.info(AppConstant.LOG_START_FORMAT, requestInfo.getCorrelationId(), requestInfo.getOperationName(),
                getClass().getSimpleName(), "invoiceNumber= " + invoiceNumber);
        ResponseInfo<Object> responseInfo = new ResponseInfo<>();
        ResponseInfo<Invoice> selectByInvoiceNumber = new ResponseInfo<>();
        Invoice invoiceData = null;
        try {
            selectByInvoiceNumber = invoiceRepository.selectByInvoiceNumber(requestInfo, invoiceNumber);
            if (selectByInvoiceNumber.isError()) {
                throw new CommonException(AppConstant.COMPLETION_STATUS.SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
                        "99", "SystemError", "Failed To Get Data", "Failed To Get Data");
            }
            invoiceData = selectByInvoiceNumber.getBody().getData();
            responseInfo.setSuccess(invoiceData);
        } catch (Exception e) {
            responseInfo.setException(e);
        }
        return responseInfo;
    }

    private Invoice constructInvoiceEntity(RequestInfo requestInfo,
            CreateInvoiceRq createInvoiceRq) {
        return new Invoice()
                .setInvoiceNumber(UUID.randomUUID().toString())
                .setTotalAmount(createInvoiceRq.getTotalAmount())
                .setIssueAt(OffsetDateTime.now().toString());
    }

}
