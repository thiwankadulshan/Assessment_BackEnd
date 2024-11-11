package com.assessment.entgra.Service;

import com.assessment.entgra.Dto.ResponseDto;

public interface LogInService {
    public ResponseDto checkUser(String userId, String userPassword);
}
