package com.assessment.entgra.Dao;

import com.assessment.entgra.Dto.BuyerDto;
import com.assessment.entgra.Dto.DisplayItemsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DashBoardDaoImpl implements DashBoardDao{

    private static final Logger logger = LoggerFactory.getLogger(DashBoardDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DisplayItemsDto> getDetailList(int number) {
        List<DisplayItemsDto> itemDtoList = new ArrayList<>();
        int ofSet = ((number - 1) * 5);
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT ");
            sql.append("main.item_code, ");
            sql.append("main.item_name, ");
            sql.append("main.material_name, ");
            sql.append("main.material_type, ");
            sql.append("price.buying_price, ");
            sql.append("price.final_price, ");
            sql.append("buyer.buyer_name, ");
            sql.append("main.item_status ");
            sql.append("FROM items AS main ");
            sql.append("LEFT JOIN item_prices AS price ON main.item_code = price.item_code ");
            sql.append("LEFT JOIN buyers AS buyer ON main.buyer_id = buyer.buyer_id ORDER BY main.item_code LIMIT 5 OFFSET ?;");
            SqlRowSet rawSet = jdbcTemplate.queryForRowSet(sql.toString(), ofSet);
            while (rawSet.next()) {
                DisplayItemsDto displayItemsDto = new DisplayItemsDto();
                displayItemsDto.setItemCode(rawSet.getString(1));
                displayItemsDto.setItemName(rawSet.getString(2));
                displayItemsDto.setMaterialName(rawSet.getString(3));
                displayItemsDto.setMaterialType(rawSet.getString(4));
                displayItemsDto.setBuyingPrice(rawSet.getString(5));
                displayItemsDto.setFinalPrice(rawSet.getString(6));
                displayItemsDto.setBuyerName(rawSet.getString(7));
                displayItemsDto.setItemStatus(rawSet.getString(8));
                itemDtoList.add(displayItemsDto);
            }
            sql.setLength(0);
            logger.info("Dashboard Dao To Get All Details In Items Table");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return itemDtoList;
    }

    @Override
    public List<BuyerDto> getDropDown() {
        List<BuyerDto> buyerDtos = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT * ");
            sql.append("FROM buyers;");
            SqlRowSet rawSet = jdbcTemplate.queryForRowSet(sql.toString());
            while (rawSet.next()) {
                BuyerDto buyerDto = new BuyerDto();
                buyerDto.setBuyerName(rawSet.getString(2));
                buyerDto.setBuyerId(rawSet.getString(1));
                buyerDtos.add(buyerDto);
            }
            sql.setLength(0);
            logger.info("Dashboard Dao To Get All Drop Down Details From Buyer");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return buyerDtos;
    }

    @Override
    public List<DisplayItemsDto> getSpecificDetail(String itemCode) {
        List<DisplayItemsDto> displayItemsDto = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT ");
            sql.append("main.item_code, ");
            sql.append("main.item_name, ");
            sql.append("main.material_name, ");
            sql.append("main.material_type, ");
            sql.append("price.buying_price, ");
            sql.append("price.final_price, ");
            sql.append("buyer.buyer_name, ");
            sql.append("main.item_status ");
            sql.append("FROM items AS main ");
            sql.append("LEFT JOIN item_prices AS price ON main.item_code = price.item_code ");
            sql.append("LEFT JOIN buyers AS buyer ON main.buyer_id = buyer.buyer_id ");
            sql.append("WHERE main.item_code LIKE ?;");
            String searchPattern = "%"+itemCode+"%";
            SqlRowSet rawSet = jdbcTemplate.queryForRowSet(sql.toString(),searchPattern);
            while (rawSet.next()) {
                DisplayItemsDto displayItemsDtoSpecific = new DisplayItemsDto();
                displayItemsDtoSpecific.setItemCode(rawSet.getString(1));
                displayItemsDtoSpecific.setItemName(rawSet.getString(2));
                displayItemsDtoSpecific.setMaterialName(rawSet.getString(3));
                displayItemsDtoSpecific.setMaterialType(rawSet.getString(4));
                displayItemsDtoSpecific.setBuyingPrice(rawSet.getString(5));
                displayItemsDtoSpecific.setFinalPrice(rawSet.getString(6));
                displayItemsDtoSpecific.setBuyerName(rawSet.getString(7));
                displayItemsDtoSpecific.setItemStatus(rawSet.getString(8));
                displayItemsDto.add(displayItemsDtoSpecific);
            }
           sql.setLength(0);
            logger.info("Dashboard Dao To Get Specific Item Details For User: "+itemCode);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return displayItemsDto;
    }

    @Override
    public String addItem(String itemCode, String itemName, String material, String materialType, String buyer, String onHand, String userId, LocalDateTime today) {
        StringBuilder sql = new StringBuilder();
        String daoResponse = "";
        try {
            sql.append("INSERT INTO items ");
            sql.append("(item_code, item_name, material_name, material_type, buyer_id, item_status, created_at, created_by) ");
            sql.append("VALUES ");
            sql.append("(?, ?, ?, ?, ?, ?, ?, ?);");
            int rawSet = jdbcTemplate.update(sql.toString(), itemCode, itemName, material, materialType, buyer, onHand, today, userId);
            if(rawSet>0){
                daoResponse = "ok";
            }
            sql.setLength(0);
            logger.info("Dash Board Dao Class To Add Items To items Table ");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return daoResponse;
    }

    @Override
    public String addPrice(String itemCode, String pricePerItem, Double price) {
        StringBuilder sql = new StringBuilder();
        String daoResponse = "";
        try {
            sql.append("INSERT INTO item_prices ");
            sql.append("(item_code, buying_price, final_price) ");
            sql.append("VALUES ");
            sql.append("(?, ?, ?);");
            int rawSet = jdbcTemplate.update(sql.toString(), itemCode, pricePerItem, price);
            if(rawSet>0){
                daoResponse = "ok";
            }
            sql.setLength(0);
            logger.info("Dash Board Dao To Add Price To item_prices Table");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return daoResponse;
    }

    @Override
    public String addNewBuyer(String buyerId, String buyer) {
        StringBuilder sql = new StringBuilder();
        String daoResponse = "";
        try {
            sql.append("INSERT INTO buyers ");
            sql.append("(buyer_id, buyer_name) ");
            sql.append("VALUES ");
            sql.append("(?, ?);");
            int rawSet = jdbcTemplate.update(sql.toString(), buyerId, buyer);
            if(rawSet>0){
                daoResponse = "ok";
            }
            sql.setLength(0);
            logger.info("");
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return daoResponse;
    }

    @Override
    public String updateState(String itemCode, String state, Double newPrice) {
        String daoResponse = "";
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("UPDATE item_prices ");
            sql.append("SET final_price = ? ");
            sql.append("WHERE item_code = ?;");
            int rawSetForPrice = jdbcTemplate.update(sql.toString(),newPrice.toString(),itemCode);

            if(rawSetForPrice>0){
                sql.setLength(0);
                sql.append("UPDATE items ");
                sql.append("SET item_status = ? ");
                sql.append("WHERE item_code = ?;");
                int rawSet = jdbcTemplate.update(sql.toString(),state,itemCode);
                    if(rawSet>0){
                        daoResponse = "ok";
                    } else {
                        logger.warn("Fail To Update New Price Of Item Code: "+itemCode);
                    }
            } else {
                logger.warn("Fail To Update State Of Item Code: "+itemCode);
            }
            logger.info("Dao For Update Sale State And New Price Of The Sale To Database Of Item: "+itemCode);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return daoResponse;
    }

}
