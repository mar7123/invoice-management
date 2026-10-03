package com.acis.invoicemanagement.usecase;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.config.AppProperties;

@Slf4j
@Component
public class BaseUsecase {
    @Autowired
    @Qualifier(AppConstant.BEAN_APP_CONFIG)
    private AppProperties appProperties;

}
