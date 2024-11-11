package com.assessment.entgra.Dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DisplayItemsDto {
    private String itemCode;
    private String itemName;
    private String materialName;
    private String materialType;
    private String buyingPrice;
    private String finalPrice;
    private String buyerName;
    private String itemStatus;
}
