package com.acis.invoicemanagement;

import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@EnableScheduling
@SpringBootApplication
public class InvoicemanagementApplication {

	private static final Logger log = LoggerFactory.getLogger(InvoicemanagementApplication.class);

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7:00"));
		log.info("Running in GMT+7 timezone : {}", new Date());
	}

	public static void main(String[] args) {
		SpringApplication.run(InvoicemanagementApplication.class, args);
	}

}
