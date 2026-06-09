package com.pup.taguig.ordermanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.taguig.ordermanagement.dto.SellingReportResponseDTO;
import com.pup.taguig.ordermanagement.dto.TotalSalesReportResponseDTO;
import com.pup.taguig.ordermanagement.repositoryM.ReportMapper;
import com.pup.taguig.ordermanagement.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private ReportMapper reportMapper;
	
	@Override
    public TotalSalesReportResponseDTO getSalesReport() {
        return reportMapper.getSalesReport();
    }

    @Override
    public List<SellingReportResponseDTO> getTopSellingProducts() {
        return reportMapper.getTopSellingProducts();
    }

}
