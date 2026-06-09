package com.pup.taguig.ordermanagement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellingReportResponseDTO {
    private Long productId;
    private String name;
    private int totalSold;
}