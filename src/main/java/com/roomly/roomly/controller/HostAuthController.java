package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomly.roomly.dto.request.hostauth.HostBusinessImageRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostBusinessNumberRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostIdCheckRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostSignInRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostSignUpRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostTelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostTelNumberRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.hostauth.HostSignInResponseDto;
import com.roomly.roomly.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roomly/auth/host")
@RequiredArgsConstructor
public class HostAuthController {

    private final AuthService authService;
    
    @PostMapping(value={"/id-check"})
    public ResponseEntity<ResponseDto> hostIdCheck(
        @RequestBody @Valid HostIdCheckRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostIdCheck(requestBody);
        return responseBody;
    }

    @PostMapping(value={"/tel-number"})
    public ResponseEntity<ResponseDto> hostTelNumber(
        @RequestBody @Valid HostTelNumberRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostTelNumber(requestBody);
        return responseBody;
    }

    @PostMapping(value={"/tel-auth-check"})
    public ResponseEntity<ResponseDto> hostTelAuthCheck(
        @RequestBody @Valid HostTelAuthCheckRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostTelAuthCheck(requestBody);
        return responseBody;
    }

    @PostMapping(value={"/business-number-check"})
    public ResponseEntity<ResponseDto> hostBusinessNumber(
        @RequestBody @Valid HostBusinessNumberRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostBusinessNumber(requestBody);
        return responseBody;
    }

    @PostMapping(value={"/business-image"})
    public ResponseEntity<ResponseDto> hostBusinessImage(
        @RequestBody @Valid HostBusinessImageRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostBusinessImage(requestBody);
        return responseBody;
    }

    @PostMapping(value={"/sign-up"})
    public ResponseEntity<ResponseDto> hostSignUp(
        @RequestBody @Valid HostSignUpRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostSignUp(requestBody);
        return responseBody;
    }

    @PostMapping(value={"/sign-in"})
    public ResponseEntity<? super HostSignInResponseDto> hostSignIn(
        @RequestBody @Valid HostSignInRequestDto requestBody
    ){
        ResponseEntity<? super HostSignInResponseDto> responseBody = authService.hostSignIn(requestBody);
        return responseBody;
    }
}
