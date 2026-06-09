package com.pup.taguig.ordermanagement.repositoryM;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pup.taguig.ordermanagement.dto.SellingReportResponseDTO;
import com.pup.taguig.ordermanagement.dto.TotalSalesReportResponseDTO;

@Mapper
public interface ReportMapper {
	TotalSalesReportResponseDTO getSalesReport();
	List<SellingReportResponseDTO>getTopSellingProducts();

}
