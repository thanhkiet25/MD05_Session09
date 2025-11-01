package com.ra.service;

import com.ra.model.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice payInvoice(Long reservationId);

    List<Invoice> getInvoiceHistoryByCustomerId(Long customerId);
}
