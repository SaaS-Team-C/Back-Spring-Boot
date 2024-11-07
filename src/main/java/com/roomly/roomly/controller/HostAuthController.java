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
    
    // 호스트 아이디 확인 api
    @PostMapping("/id-check")
    public ResponseEntity<ResponseDto> hostIdCheck(
        @RequestBody @Valid HostIdCheckRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostIdCheck(requestBody);
        return responseBody;
    }

    // 호스트 전화번호 중복확인 및 인증번호 전송 api
    @PostMapping("/tel-auth")
    public ResponseEntity<ResponseDto> hostTelNumber(
        @RequestBody @Valid HostTelNumberRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostTelNumber(requestBody);
        return responseBody;
    }

    // 호스트 전화번호 인증번호 인증 api
    @PostMapping("/tel-auth-check")
    public ResponseEntity<ResponseDto> hostTelAuthCheck(
        @RequestBody @Valid HostTelAuthCheckRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostTelAuthCheck(requestBody);
        return responseBody;
    }

    // 호스트 사업자 번호 중복확인 api
    @PostMapping("/business-number-check")
    public ResponseEntity<ResponseDto> hostBusinessNumber(
        @RequestBody @Valid HostBusinessNumberRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostBusinessNumber(requestBody);
        return responseBody;
    }

    // 호스트 사업자 이미지 중복확인 api
    @PostMapping("/business-image")
    public ResponseEntity<ResponseDto> hostBusinessImage(
        @RequestBody @Valid HostBusinessImageRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostBusinessImage(requestBody);
        return responseBody;
    }

    // 호스트 회원가입 api
    @PostMapping("/sign-up") 
    public ResponseEntity<ResponseDto> hostSignUp(
        @RequestBody @Valid HostSignUpRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = authService.hostSignUp(requestBody);
        return responseBody;
    }

    // 호스트 로그인 api
    @PostMapping("/sign-in")
    public ResponseEntity<? super HostSignInResponseDto> hostSignIn(
        @RequestBody @Valid HostSignInRequestDto requestBody
    ){
        ResponseEntity<? super HostSignInResponseDto> responseBody = authService.hostSignIn(requestBody);
        return responseBody;
    }
}
