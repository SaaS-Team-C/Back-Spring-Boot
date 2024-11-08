package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.guest.PatchGuestAuthRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestPwRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestTelNumberRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.guest.GuestIdFindRequestDto;
import com.roomly.roomly.dto.request.guest.GuestInformationRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestMyPageResponseDto;
import com.roomly.roomly.dto.response.guest.GuestIdFindSuccessResponseDto;

public interface GuestService {
    
    // Guest 정보 보기
    // ResponseEntity <? super GetGuestMyPageResponseDto> getGuestMyPage(String guestId);
    ResponseEntity <? super GetGuestMyPageResponseDto> getGuestMyPage(String guestId, GuestInformationRequestDto dto);
    // Guest 정보 수정
    ResponseEntity<ResponseDto> patchGuestPw(PatchGuestPwRequestDto dto, String guestId);
    // Guest 전화번호 수정 
    ResponseEntity<ResponseDto> patchGuestTelNumber(PatchGuestTelNumberRequestDto dto, String guestId);
    // Guest 인증및 이전번호 삭제
    ResponseEntity<ResponseDto> patchGuestAuth(PatchGuestAuthRequestDto dto, String guestId);
    // 게스트 아이디 찾기
    ResponseEntity<ResponseDto> guestIdFind(GuestIdFindRequestDto dto);
    //guest 전화번호 확인
    ResponseEntity<? super GuestIdFindSuccessResponseDto> guestTelAuthCheck(TelAuthCheckRequestDto dto);
}
