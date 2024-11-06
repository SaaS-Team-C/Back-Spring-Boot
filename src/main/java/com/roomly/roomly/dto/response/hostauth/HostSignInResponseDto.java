package com.roomly.roomly.dto.response.hostauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.response.ResponseCode;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.ResponseMessage;
import lombok.Getter;

@Getter
public class HostSignInResponseDto extends ResponseDto{
    
    private String accessToken;
    private Integer expiration;

    public HostSignInResponseDto(String accessToken){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.accessToken = accessToken;
        this.expiration = 10 * 60 * 60;
    }

    public static ResponseEntity<HostSignInResponseDto> success(String accessToken) {
        HostSignInResponseDto responseBody = new HostSignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
