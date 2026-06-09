package com.pup.taguig.ordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellingReportResponseDTO {
    private Long productId;
    private String name;
    private int totalSold;

}
