package com.acis.invoicemanagement.model.db;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Invoice {
    private String invoiceNumber;
    private Double totalAmount;
    private String issueAt;
}
