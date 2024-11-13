package com.assessment.entgra.Service;

import com.assessment.entgra.Dto.BuyerDto;
import com.assessment.entgra.Dto.DisplayItemsDto;
import com.assessment.entgra.Dto.ResponseDto;

import java.util.List;

public interface DashBoardService {
    public List<DisplayItemsDto> getAllDetails(int number);

    public List<DisplayItemsDto> getSpecificDetail(String itemCode);

    public ResponseDto processForAdd(String itemName, String itemType, String buyer, String newBuyerSearch, String pricePerItem,String  percentage, String material, String materialType, String userId);

    public ResponseDto editState(String userId, String state);

    public List<BuyerDto> getDropDown();
}
