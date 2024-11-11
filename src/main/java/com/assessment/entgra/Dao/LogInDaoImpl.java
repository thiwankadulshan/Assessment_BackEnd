package com.assessment.entgra.Dao;

import com.assessment.entgra.Dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


@Repository
public class LogInDaoImpl implements LogInDao{

    private static final Logger logger = LoggerFactory.getLogger(LogInDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDto getUserDetail(String userId) {
        StringBuilder sql = new StringBuilder();
        UserDto userDto = new UserDto();
        try {
            sql.append("SELECT user_password FROM user WHERE user_id = ?");
            SqlRowSet rawSet = jdbcTemplate.queryForRowSet(sql.toString(),userId);
            while (rawSet.next()) {
                userDto.setUserPassword(rawSet.getString(1));
            }
            sql.setLength(0);
            logger.info("Log In Dao To Get User Details Base On User Id: "+userId);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return userDto;
    }
}
