package com.assessment.entgra.Service;

import com.assessment.entgra.Dao.LogInDao;
import com.assessment.entgra.Dto.ResponseDto;
import com.assessment.entgra.Dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogInServiceImpl implements LogInService{

    private static final Logger logger = LoggerFactory.getLogger(LogInServiceImpl.class);

    @Autowired
    private LogInDao logInDao;

    @Override
    public ResponseDto checkUser(String userId, String userPassword) {
        ResponseDto responseDto = new ResponseDto();
        try{
            UserDto userDto = logInDao.getUserDetail(userId);
            if(userDto.getUserPassword().equals(userPassword)){
                responseDto.status = "Success";
            }else{
                responseDto.status = "Unsuccessful";
            }
            logger.info("Log In Service To Check User Is Valid For Id: "+userId);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return responseDto;
    }
}
