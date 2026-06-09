package com.pup.taguig.ordermanagement.repositoryM;

import com.pup.taguig.ordermanagement.dto.TotalSalesReportResponseDTO;
import com.pup.taguig.ordermanagement.dto.SellingReportResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReportMapper {
    TotalSalesReportResponseDTO getSalesReport();
    List<SellingReportResponseDTO> getTopSellingProducts();
}