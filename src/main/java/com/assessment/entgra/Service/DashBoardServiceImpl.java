package com.assessment.entgra.Service;

import com.assessment.entgra.Dao.DashBoardDao;
import com.assessment.entgra.Dto.BuyerDto;
import com.assessment.entgra.Dto.DisplayItemsDto;
import com.assessment.entgra.Dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardServiceImpl implements DashBoardService{

    private static final Logger logger = LoggerFactory.getLogger(DashBoardServiceImpl.class);

    @Autowired
    private DashBoardDao dashBoardDao;

    @Override
    public List<DisplayItemsDto> getAllDetails(int number) {
        List<DisplayItemsDto> itemDtoList = new ArrayList<>();
        try {
            itemDtoList = dashBoardDao.getDetailList(number);
            logger.info("Service To Get All Item Details");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return itemDtoList;
    }

    @Override
    public List<BuyerDto> getDropDown() {
        List<BuyerDto> buyerDtos = new ArrayList<>();
        try{
            buyerDtos = dashBoardDao.getDropDown();
            logger.info("Dashboard Service To Get All Drop Down From Buyer");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return buyerDtos;
    }

    @Override
    public List<DisplayItemsDto> getSpecificDetail(String itemCode) {
        List<DisplayItemsDto> displayItemsDto = new ArrayList<>();
        try {
            displayItemsDto = dashBoardDao.getSpecificDetail(itemCode);
            logger.info("Service To Get All Item Details");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return displayItemsDto;
    }

    @Override
    public ResponseDto processForAdd(String itemName, String itemType, String buyer, String newBuyerSearch, String pricePerItem, String material, String materialType, String userId) {
        LocalDateTime today = LocalDateTime.now();
        ResponseDto responseDto = new ResponseDto();
        String daoResponseTwo = "";
        String daoResponse = "";
        String daoResponseThree = "";
        String date = LocalDateTime.now().toString();
        try {
            String itemCode = itemName.substring(0,2)+material.substring(0,2)+materialType.substring(0,2)+date;
            Double price = 0.0;
            if(itemType.equals("buy")){
                price = Double.parseDouble(pricePerItem) + Double.parseDouble(pricePerItem)*(30.0/100);
                if(newBuyerSearch.equals("1")){
                    String buyerId = buyer+date;
                    daoResponseThree = dashBoardDao.addNewBuyer(buyerId, buyer);
                    if(daoResponseThree.equals("ok")){
                        daoResponse = dashBoardDao.addItem(itemCode, itemName, material, materialType, buyerId, "ON HAND", userId, today);
                        if(daoResponse.equals("ok")){
                            daoResponseTwo = dashBoardDao.addPrice(itemCode, pricePerItem, price);
                            if(daoResponseTwo.equals("ok")){
                                responseDto.status = "Success";
                            }else{
                                responseDto.status = "Unsuccessfully";
                            }
                        }else{
                            responseDto.status = "Unsuccessfully";
                        }
                    }else {
                        responseDto.status = "Unsuccessfully";
                    }
                } else {
                    String buyerId = null;
                    List<BuyerDto> buyerDto = dashBoardDao.getDropDown();
                    for(BuyerDto buyerDto1 : buyerDto){
                        if(buyerDto1.getBuyerId().trim().equals(buyer.trim())){
                            buyerId = buyerDto1.getBuyerId();
                            break;
                        }
                    }
                    daoResponse = dashBoardDao.addItem(itemCode, itemName, material, materialType, buyerId, "ON HAND", userId, today);
                    if(daoResponse.equals("ok")){
                        daoResponseTwo = dashBoardDao.addPrice(itemCode, pricePerItem, price);
                        if(daoResponseTwo.equals("ok")){
                            responseDto.status = "Success";
                        }else{
                            responseDto.status = "Unsuccessfully";
                        }
                    }else{
                        responseDto.status = "Unsuccessfully";
                    }
                }
            } else {
                price =Double.parseDouble(pricePerItem);
                boolean buyerCheck = false;
                List<BuyerDto> buyerDto = dashBoardDao.getDropDown();
                for(BuyerDto buyerDto1 : buyerDto){
                    if(buyerDto1.getBuyerId().equals("B0")) {
                        buyerCheck = true;
                        break;
                    }
                }
                if(!buyerCheck){
                    dashBoardDao.addNewBuyer("B0", "Own Product");
                }
                daoResponse = dashBoardDao.addItem(itemCode, itemName, material, materialType, "B0", "ON HAND", userId, today);
                if(daoResponse.equals("ok")){
                    daoResponseTwo = dashBoardDao.addPrice(itemCode, pricePerItem, price);
                        if(daoResponseTwo.equals("ok")){
                            responseDto.status = "Success";
                        } else {
                            responseDto.status = "Unsuccessfully";
                        }
                }else{
                    responseDto.status = "Unsuccessfully";
                }
            }
            logger.info("Service To Process Data To Add To Data Base On Item Code: ");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto editState(String itemCode, String state) {
        ResponseDto responseDto = new ResponseDto();
        Double price = 0.0;
        try {
            List<DisplayItemsDto> displayItemsDto = dashBoardDao.getSpecificDetail(itemCode);
            if(!displayItemsDto.isEmpty()){
                DisplayItemsDto item = displayItemsDto.get(0);
                price = Double.parseDouble(item.getBuyingPrice());
            }
            logger.info("Service To Get Old Price For Update Item State Of Item: "+itemCode);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        try {
            Double newPrice = 0.0;
            if(state.equals("SALE")){
                newPrice = price - price*(35.0/100);
            } else if (state.equals("STOCK CLEAR")) {
                newPrice = price - price*(35.0/100);
            }
            String daoResponse = dashBoardDao.updateState(itemCode, state, newPrice);
            if(daoResponse.equals("ok")){
                responseDto.status = "Success";
            }else {
                responseDto.status = "Unsuccessfully";
            }
            logger.info("Service To Update Item State Of Item: "+itemCode);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return responseDto;
    }

}
