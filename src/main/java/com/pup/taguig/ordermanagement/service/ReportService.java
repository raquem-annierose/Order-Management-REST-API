package com.pup.taguig.ordermanagement.service;

import java.util.List;
import com.pup.taguig.ordermanagement.dto.SellingReportResponseDTO;
import com.pup.taguig.ordermanagement.dto.TotalSalesReportResponseDTO;

public interface ReportService {
    // Make sure this name matches exactly
    TotalSalesReportResponseDTO getSalesReport();
    List<SellingReportResponseDTO> getTopSellingProducts();
}