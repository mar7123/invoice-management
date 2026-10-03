package com.acis.invoicemanagement.config.client;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.acis.invoicemanagement.config.AppConstant;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class JdbcConfig {

    @Profile("!local")
    @Bean(AppConstant.BEAN_DS_MASTERDATA_POSTGRES)
    @ConfigurationProperties(AppConstant.CONFIG_PROP_MASTERDATA_POSTGRES)
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(AppConstant.BEAN_JDBC_MASTERDATA_POSTGRES)
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier(AppConstant.BEAN_DS_MASTERDATA_POSTGRES) DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


}

