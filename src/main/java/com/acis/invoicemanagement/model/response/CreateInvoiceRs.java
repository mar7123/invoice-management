package com.acis.invoicemanagement.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateInvoiceRs {
    private String invoiceNumber;
}
