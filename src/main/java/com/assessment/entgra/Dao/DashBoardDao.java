package com.assessment.entgra.Dao;

import com.assessment.entgra.Dto.BuyerDto;
import com.assessment.entgra.Dto.DisplayItemsDto;
import com.assessment.entgra.Dto.ResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface DashBoardDao {
    public List<DisplayItemsDto> getDetailList(int number);

    public List<DisplayItemsDto> getSpecificDetail(String itemCode);

    public String updateState(String itemCode, String state, Double newPrice);

    public String addItem(String itemCode, String itemName, String material, String materialType, String buyer, String onHand, String userId, LocalDateTime today);

    public String addPrice(String itemCode, String pricePerItem, Double price);

    public String addNewBuyer(String buyerId, String buyer);

    public List<BuyerDto> getDropDown();
}
