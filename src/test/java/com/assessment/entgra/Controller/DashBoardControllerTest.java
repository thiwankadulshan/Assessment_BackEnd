package com.assessment.entgra.Controller;

import com.assessment.entgra.Dto.BuyerDto;
import com.assessment.entgra.Dto.DisplayItemsDto;
import com.assessment.entgra.Dto.ResponseDto;
import com.assessment.entgra.Service.DashBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashBoardController.class)
class DashBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashBoardService dashBoardService;

    @Test
    void getItemsTestCase() throws Exception{
        List<DisplayItemsDto> displayItemsDtos = new ArrayList<>();
        DisplayItemsDto displayItemsDto = new DisplayItemsDto();
        displayItemsDto.setItemCode("qjgtry45637");
        displayItemsDto.setItemName("tshirt");
        displayItemsDto.setMaterialName("trouser clothes");
        displayItemsDto.setMaterialType("linun");
        displayItemsDto.setBuyingPrice("2000.0");
        displayItemsDto.setFinalPrice("2600.0");
        displayItemsDto.setBuyerName("carnage");
        displayItemsDto.setItemStatus("ON HAND");

        displayItemsDtos.add(displayItemsDto);
        when(dashBoardService.getAllDetails(0)).thenReturn(displayItemsDtos);

        mockMvc.perform(post("/dashboard/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ofSet\":0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(displayItemsDtos.size()));
    }

    @Test
    void dropDownTestCase() throws Exception {
        List<BuyerDto> buyerDtos = new ArrayList<>();
        BuyerDto buyerDto = new BuyerDto();
        buyerDto.setBuyerId("b004");
        buyerDto.setBuyerName("carnage");
        buyerDtos.add(buyerDto);
        when(dashBoardService.getDropDown()).thenReturn(buyerDtos);

        mockMvc.perform(get("/dashboard/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(buyerDtos.size()));
    }

    @Test
    void getSpecificItemTestCase() throws Exception{
        List<DisplayItemsDto> displayItemsDtos = new ArrayList<>();
        DisplayItemsDto displayItemsDto = new DisplayItemsDto();
        displayItemsDto.setItemCode("iuer87987e");
        displayItemsDto.setItemName("trouser");
        displayItemsDto.setMaterialName("trouser clothes");
        displayItemsDto.setMaterialType("denim");
        displayItemsDto.setBuyingPrice("1000.0");
        displayItemsDto.setFinalPrice("1300.0");
        displayItemsDto.setBuyerName("carnage");
        displayItemsDto.setItemStatus("ON HAND");

        displayItemsDtos.add(displayItemsDto);
        when(dashBoardService.getSpecificDetail("dhjweh734")).thenReturn(displayItemsDtos);

        mockMvc.perform(post("/dashboard/specific")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemCode\":\"dhjweh734\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(displayItemsDtos.size()));
    }

    @Test
    void addItemTestCase() throws Exception{
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        String status = responseDto.getStatus();
        when(dashBoardService.processForAdd("trouser", "buy", "signature", "1", "3000", "30", "trouser_clothes", "linen", "userOne")).thenReturn(responseDto);

        mockMvc.perform(post("/dashboard/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"trouser\",\"itemType\":\"buy\",\"supplierName\":\"signature\"," +
                                "\"newBuyerCheck\":\"1\",\"pricePerItem\":\"3000\",\"percentage\":\"30\",\"material\":\"trouser_clothes\",\"materialType\":\"linen\", \"userId\":\"userOne\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Success"));
    }

    @Test
    void editItemTestCase() throws Exception{
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        when(dashBoardService.editState("SALE", "SALE")).thenReturn(responseDto);

        mockMvc.perform(post("/dashboard/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"state\":\"SALE\",\"itemCode\":\"SALE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Success"));
    }
}