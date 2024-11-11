package com.assessment.entgra.Controller;

import com.assessment.entgra.Dto.ResponseDto;
import com.assessment.entgra.Service.LogInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173/")
@RequestMapping("/login")
public class LogInController {

    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

    @Autowired
    private LogInService logInService;

    @PostMapping("/user")
    public ResponseEntity logInData(@RequestBody Map<String, String> request){
        ResponseDto responseDto = new ResponseDto();
        String userId = (String) request.get("userId");
        String userPassword = (String) request.get("userPassword");
        try{
            responseDto = logInService.checkUser(userId,userPassword);
            logger.info("Login Controller Check Log In Details About User: "+userId);
        } catch (Exception ex){
           logger.error(ex.getMessage());
        }
        return ResponseEntity.status(200).body(responseDto);
    }
}
