package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.guest.PatchGuestAuthRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestPwRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestTelNumberRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.guest.GuestIdFindRequsetDto;
import com.roomly.roomly.dto.request.guest.GuestReviewListRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetAccommodationReviewResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReservationViewResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestMyPageResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReviewResponseDto;
import com.roomly.roomly.dto.response.guest.GetReservationStatusResponseDto;
import com.roomly.roomly.dto.response.guest.GuestIdFindSuccessResponseDto;

public interface GuestService {
    
    // 게스트 정보 보기
    ResponseEntity <? super GetGuestMyPageResponseDto> getGuestMyPage(String guestId);
    // 게스트 정보 수정
    ResponseEntity<ResponseDto> patchGuestPw(PatchGuestPwRequestDto dto, String guestId);
    ResponseEntity<ResponseDto> patchGuestTelNumber(PatchGuestTelNumberRequestDto dto, String guestId);
    ResponseEntity<ResponseDto> patchGuestAuth(PatchGuestAuthRequestDto dto, String guestId);
    // 예약및 결제 창
    ResponseEntity<? super GetGuestReservationViewResponseDto> getReservationView(String guestId, Integer roomId);
    // 예약현황 리스트
    ResponseEntity<? super GetReservationStatusResponseDto> reservationStatus(String guestId);
    // 리뷰 작성하기
    ResponseEntity<ResponseDto> review(GuestReviewListRequestDto dto, Long reservationId, String guesrId);
    // 해당 guestId의 리뷰보기
    ResponseEntity<? super GetGuestReviewResponseDto> guestReviewList(String guestId);
    // 해당 숙소명의 리뷰 보기
    ResponseEntity<? super GetAccommodationReviewResponseDto> accommodationReviewList(String accommodationName);

    ResponseEntity<ResponseDto> guestIdFind(GuestIdFindRequsetDto dto);
    ResponseEntity<? super GuestIdFindSuccessResponseDto> guestTelAuthCheck(TelAuthCheckRequestDto dto);
}
