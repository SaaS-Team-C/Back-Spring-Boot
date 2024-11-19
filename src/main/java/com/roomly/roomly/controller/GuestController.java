package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomly.roomly.dto.request.guest.PatchGuestAuthRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestPwRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.guest.GuestIdFindRequestDto;
import com.roomly.roomly.dto.request.guest.GuestInformationRequestDto;
import com.roomly.roomly.dto.request.guest.GuestPwFindRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestMyPageResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestSignInResponseDto;
import com.roomly.roomly.dto.response.guest.GuestIdFindSuccessResponseDto;
import com.roomly.roomly.service.GuestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roomly/guest")
@RequiredArgsConstructor
public class GuestController {
    
    private final GuestService guestService;

    // 해당 Id에 관한 게스트 정보보기
    @PostMapping("/guest-information/{guestId}")
    public ResponseEntity<? super GetGuestMyPageResponseDto> getGuestMyPage(
        @PathVariable("guestId") String guestId,
        @RequestBody @Valid GuestInformationRequestDto requestBody
    ) {
        ResponseEntity<? super GetGuestMyPageResponseDto> response = guestService.getGuestMyPage(guestId,requestBody);
        return response;
    }

    // 게스트 비밀번호 수정(로그인상태)
    @PatchMapping("/pw/{guestId}")
    public ResponseEntity<ResponseDto> patchGuestPw(
        @RequestBody @Valid PatchGuestPwRequestDto requestBody,
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity<ResponseDto> response = guestService.patchGuestPw(requestBody, guestId);
        return response;
    }
    // 게스트 인증번호 확인 및 전화번호 수정,삭제
    @PatchMapping("/auth-number/{guestId}")
    public ResponseEntity<ResponseDto> patchGuestAuth(
        @RequestBody @Valid PatchGuestAuthRequestDto requestBody,
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity<ResponseDto> response = guestService.patchGuestAuth(requestBody, guestId);
        return response;
    }

    // 게스트 아이디 찾기
    @PostMapping("/id-find")
    public ResponseEntity<ResponseDto> guestIdFind(
        @RequestBody @Valid GuestIdFindRequestDto requestBody
    ) {
            ResponseEntity<ResponseDto> responseBody = guestService.guestIdFind(requestBody);
            return responseBody;
    }
    
    // 아이디 찾기에 대한 전화번호 인증확인  
    @PostMapping("/tel-auth-check")
        public ResponseEntity<? super GuestIdFindSuccessResponseDto> guestTelAuthCheck(
            @RequestBody @Valid TelAuthCheckRequestDto requestBody
    ) {
            ResponseEntity<? super GuestIdFindSuccessResponseDto> responseBody = guestService.guestTelAuthCheck(requestBody);
            return responseBody;
        }
    
    // 게스트 비밀번호 변경(로그아웃상태)
    @PatchMapping("/pw-find")
    public ResponseEntity<ResponseDto> guestPwFind(
        @RequestBody @Valid GuestPwFindRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> responseBody = guestService.guestPwFind(requestBody);
        return responseBody;
    }

    // 게스트 정보 보내기
    @GetMapping("/sign-in")
    public ResponseEntity<? super GetGuestSignInResponseDto> getGuestSignIn(
        @AuthenticationPrincipal String guestId
    ) {
        ResponseEntity<? super GetGuestSignInResponseDto> response = guestService.getGuestSignIn(guestId);
        return response;
    }
}
