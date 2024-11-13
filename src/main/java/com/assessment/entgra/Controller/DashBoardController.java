package com.assessment.entgra.Controller;

import com.assessment.entgra.Dto.BuyerDto;
import com.assessment.entgra.Dto.DisplayItemsDto;
import com.assessment.entgra.Dto.ResponseDto;
import com.assessment.entgra.Service.DashBoardService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173/")
@RequestMapping("/dashboard")
public class DashBoardController {

    private static final Logger logger = LoggerFactory.getLogger(DashBoardController.class);

    @Autowired
    private DashBoardService dashBoardService;

    @PostMapping("/get")
    public ResponseEntity getItems(@Valid @RequestBody Map<String, Integer> number, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        List<DisplayItemsDto> itemDtosList = new ArrayList<>();
        int ofSet = number.get("ofSet");
        try{
            itemDtosList = dashBoardService.getAllDetails(ofSet);
            logger.info("Dashboard Controller Endpoint To Get all Details");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return ResponseEntity.status(200).body(itemDtosList);
    }

    @GetMapping("/list")
    public ResponseEntity dropDown(){
        List<BuyerDto> buyerDtos = new ArrayList<>();
        try{
            buyerDtos = dashBoardService.getDropDown();
            logger.info("Dashboard Controller Endpoint To Get all Details");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return ResponseEntity.status(200).body(buyerDtos);
    }

    @PostMapping("/specific")
    public ResponseEntity getSpecificItems(@Valid @RequestBody Map<String, String> request, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        List<DisplayItemsDto> displayItemsDto = new ArrayList<>();
        try{
            String itemCode = (String) request.get("itemCode");
            displayItemsDto = dashBoardService.getSpecificDetail(itemCode);
            logger.info("Dashboard Controller End Point To Get Specific Item For Item Code: "+itemCode);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return ResponseEntity.status(200).body(displayItemsDto);
    }

    @PostMapping("/add")
    public ResponseEntity addItem(@Valid @RequestBody Map<String, String> request, BindingResult result){
        System.out.println("request is: "+request);
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        ResponseDto responseDto = new ResponseDto();
        String itemName = (String) request.get("itemName");
        String itemType = (String) request.get("itemType");
        String buyer = (String) request.get("supplierName");
        String newBuyerSearch = (String) request.get("newBuyerCheck");
        String pricePerItem = (String) request.get("pricePerItem");
        String material = (String) request.get("material");
        String materialType = (String) request.get("materialType");
        String userId = (String) request.get("userId");
        try{
            logger.info("Dashboard Controller End Point To Store Data To Database");
            responseDto = dashBoardService.processForAdd(itemName, itemType, buyer, newBuyerSearch, pricePerItem, material, materialType, userId);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return ResponseEntity.status(200).body(responseDto);
    }

    @PostMapping("/edit")
    public ResponseEntity editState(@Valid @RequestBody Map<String, String> request, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        String state = (String) request.get("state");
        String itemCode = (String) request.get("itemCode");
        ResponseDto responseDto = new ResponseDto();
        try{
            responseDto = dashBoardService.editState(itemCode,state);
            logger.info("Dashboard Controller to Update State Of The Item");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return ResponseEntity.status(200).body(responseDto);
    }
}
