package com.acis.invoicemanagement.config.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.acis.invoicemanagement.config.AppConstant;
import com.acis.invoicemanagement.config.AppProperties;

@Configuration
@Profile("local")
public class LocalProfile {
    @Bean(name = AppConstant.BEAN_APP_CONFIG)
    public AppProperties loadAppConfig() {
        return new AppProperties();
    }
}
