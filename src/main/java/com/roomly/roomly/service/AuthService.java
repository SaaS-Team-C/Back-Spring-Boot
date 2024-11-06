package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.guestauth.GuestIdCheckRequestDto;
import com.roomly.roomly.dto.request.guestauth.GuestSignInRequestDto;
import com.roomly.roomly.dto.request.guestauth.GuestSignUpRequestDto;
import com.roomly.roomly.dto.request.guestauth.GuestTelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.guestauth.GuestTelAuthRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostBusinessImageRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostBusinessNumberRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostIdCheckRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostSignInRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostSignUpRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostTelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.hostauth.HostTelNumberRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guestauth.GuestSignInResponseDto;
import com.roomly.roomly.dto.response.hostauth.HostSignInResponseDto;

public interface AuthService {
    // -------- 호스트 --------
    ResponseEntity<ResponseDto> hostIdCheck(HostIdCheckRequestDto dto);
    ResponseEntity<ResponseDto> hostTelNumber(HostTelNumberRequestDto dto);
    ResponseEntity<ResponseDto> hostTelAuthCheck(HostTelAuthCheckRequestDto dto);
    ResponseEntity<ResponseDto> hostBusinessNumber(HostBusinessNumberRequestDto dto);
    ResponseEntity<ResponseDto> hostBusinessImage(HostBusinessImageRequestDto dto);
    ResponseEntity<ResponseDto> hostSignUp(HostSignUpRequestDto dto);
    ResponseEntity<? super HostSignInResponseDto> hostSignIn(HostSignInRequestDto dto);

    // -------- 게스트 --------
    ResponseEntity<ResponseDto> guestIdCheck(GuestIdCheckRequestDto dto);
    ResponseEntity<ResponseDto> guestTelAuth(GuestTelAuthRequestDto dto);
    ResponseEntity<ResponseDto> guestTelAuthCheck(GuestTelAuthCheckRequestDto dto);
    ResponseEntity<ResponseDto> guestSignUp(GuestSignUpRequestDto dto);
    ResponseEntity<? super GuestSignInResponseDto> guestSignIn(GuestSignInRequestDto dto);
}
