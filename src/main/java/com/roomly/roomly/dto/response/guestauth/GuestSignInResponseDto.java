package com.roomly.roomly.dto.response.guestauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.response.ResponseCode;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
public class GuestSignInResponseDto extends ResponseDto{
    
    private String GuestAccessToken;
    private Integer expiration;

    private GuestSignInResponseDto(String accessToken) {
        
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.GuestAccessToken = accessToken;
        this.expiration = 10*60*60; // 10시간 인증
    }

    public static ResponseEntity<GuestSignInResponseDto> success(String accessToken) {
        GuestSignInResponseDto responseBody = new GuestSignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
