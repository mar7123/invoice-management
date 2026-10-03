package com.acis.invoicemanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateInvoiceRq {
    @NotBlank(message = "total_amount is required")
    private Double totalAmount;
}
