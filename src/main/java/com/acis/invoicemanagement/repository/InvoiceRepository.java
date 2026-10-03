package com.acis.invoicemanagement.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.config.AppProperties;
import com.acis.invoicemanagement.model.db.Invoice;
import com.acis.invoicemanagement.model.request.RequestInfo;
import com.acis.invoicemanagement.model.response.ResponseInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class InvoiceRepository {
    @Autowired
    @Qualifier(AppConstant.BEAN_JDBC_MASTERDATA_POSTGRES)
    protected NamedParameterJdbcTemplate invoiceTrx;

    @Autowired
    private AppProperties appProperties;

    public ResponseInfo<Void> insert(RequestInfo requestInfo, Invoice data) {
        log.info(AppConstant.LOG_START_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(), data);
        ResponseInfo<Void> responseInfo = new ResponseInfo<>();
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(AppConstant.SWADB_INVOICE_NUMBER, data.getInvoiceNumber());
            parameterSource.addValue(AppConstant.SWADB_TOTAL_AMOUNT, data.getTotalAmount());
            parameterSource.addValue(AppConstant.SWADB_ISSUE_DATE, data.getIssueAt());

            invoiceTrx.update(appProperties.getINSERT_INVOICE(), parameterSource);
            responseInfo.setSuccess();
            log.info(AppConstant.LOG_END_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(),
                    responseInfo);
        } catch (DataIntegrityViolationException e) {
            log.info(AppConstant.LOG_END_ERROR_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(),
                    e.getMessage());
            responseInfo.setException(AppConstant.RESPONSE.CONFLICT);
        } catch (Exception e) {
            log.info(AppConstant.LOG_END_ERROR_FORMAT, requestInfo.getCorrelationId(),
                    getClass().getSimpleName(), e.getMessage());
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    public ResponseInfo<Invoice> selectByInvoiceNumber(RequestInfo requestInfo, String invoiceNumber) {
        log.info(AppConstant.LOG_START_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(),
                invoiceNumber);
        ResponseInfo<Invoice> responseInfo = new ResponseInfo<>();
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(AppConstant.SWADB_INVOICE_NUMBER, invoiceNumber);

            Invoice result = invoiceTrx.queryForObject(
                    appProperties.getGET_INVOICE_BY_INVOICE_NUMBER(),
                    parameterSource,
                    (rs, rowNum) -> {
                        Invoice trx = new Invoice();
                        trx.setInvoiceNumber(rs.getString(AppConstant.SWADB_INVOICE_NUMBER));
                        trx.setTotalAmount(rs.getDouble(AppConstant.SWADB_TOTAL_AMOUNT));
                        trx.setIssueAt(rs.getString(AppConstant.SWADB_ISSUE_DATE));
                        return trx;
                    });
            responseInfo.setSuccess(result);
            log.info(AppConstant.LOG_END_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(),
                    responseInfo);
        } catch (EmptyResultDataAccessException e) {
            log.info(AppConstant.LOG_END_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(),
                    "Data not found");
            responseInfo.setSuccess(null);
        } catch (Exception e) {
            log.info(AppConstant.LOG_END_ERROR_FORMAT, requestInfo.getCorrelationId(), getClass().getSimpleName(),
                    e.getMessage());
            responseInfo.setException(e);
        }
        return responseInfo;
    }

}
