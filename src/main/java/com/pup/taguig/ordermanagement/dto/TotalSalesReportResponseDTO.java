package com.pup.taguig.ordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalSalesReportResponseDTO {
    private int totalOrders;
    private double totalRevenue;
}
