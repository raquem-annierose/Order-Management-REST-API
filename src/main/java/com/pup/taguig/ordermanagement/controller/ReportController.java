package com.pup.taguig.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pup.taguig.ordermanagement.dto.SellingReportResponseDTO;
import com.pup.taguig.ordermanagement.dto.TotalSalesReportResponseDTO;
import com.pup.taguig.ordermanagement.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/sales")
	public TotalSalesReportResponseDTO getTotalSales() {
        return reportService.getSalesReport();
    }
	
	@GetMapping("/top-products")
	public List<SellingReportResponseDTO> getTopProducts() {
        return reportService.getTopSellingProducts();
    }
	
}
