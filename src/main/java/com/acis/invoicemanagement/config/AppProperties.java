package com.acis.invoicemanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app")
@Slf4j
public class AppProperties {
    @Autowired
    private Environment env;

    private String APP_NAME;

    private String INSERT_INVOICE;
    private String GET_INVOICE_BY_INVOICE_NUMBER;

}
