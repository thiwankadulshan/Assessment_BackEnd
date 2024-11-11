package com.assessment.entgra.Dto;

import lombok.Data;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ResponseDto {
    public String returnCode;
    public String status;
    public String description;
    public String errorCode;
    public String referenceId;
    public String token;
    public String userType;
    public int totalCount;
    public List content;

    public JSONObject ResponseMessage() {
        JSONObject jsonObject = new JSONObject();
        JSONObject responseHeader = new JSONObject();
        JSONObject responseBody = new JSONObject();

        responseHeader.put("status", status);
        responseHeader.put("returnCode", returnCode);
        responseBody.put("description", description);
        responseBody.put("errorCode", errorCode);
        responseBody.put("token",token);
        responseBody.put("referenceId", referenceId);
        responseBody.put("totalCount", totalCount);
        responseBody.put("userType", userType);
        responseBody.put("content", content);

        jsonObject.put("responseHeader", responseHeader);
        jsonObject.put("responseBody", responseBody);
        return jsonObject;
    }
}
