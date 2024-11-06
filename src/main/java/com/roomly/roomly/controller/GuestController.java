package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomly.roomly.dto.request.guest.PatchGuestAuthRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestPwRequestDto;
import com.roomly.roomly.dto.request.guest.PatchGuestTelNumberRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.request.guest.AddBookMarkRequestDto;
import com.roomly.roomly.dto.request.guest.GuestIdFindRequsetDto;
import com.roomly.roomly.dto.request.guest.GuestReviewListRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetAccommodationReviewResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestBookMarkResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReservationViewResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestMyPageResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReviewResponseDto;
import com.roomly.roomly.dto.response.guest.GetReservationStatusResponseDto;
import com.roomly.roomly.dto.response.guest.GuestIdFindSuccessResponseDto;
import com.roomly.roomly.service.BookmarkService;
import com.roomly.roomly.service.GuestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roomly/guest")
@RequiredArgsConstructor
public class GuestController {
    
    private final GuestService guestService;
    private final BookmarkService bookmarkService;
    
    
    // 해당 Id에 관한 게스트 정보 보기
    @GetMapping("/MyPage/{guestId}")
    public ResponseEntity<? super GetGuestMyPageResponseDto> getGuestMyPage(
        @PathVariable("guestId") String guestId
    ){
        ResponseEntity<? super GetGuestMyPageResponseDto> response = guestService.getGuestMyPage(guestId);
        return response;
    }

    // 게스트 비밀번호 수정
    @PatchMapping("/pw/{guestId}")
    public ResponseEntity<ResponseDto> patchGuestPw(
        @RequestBody @Valid PatchGuestPwRequestDto requestBody,
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity<ResponseDto> response = guestService.patchGuestPw(requestBody, guestId);
        return response;
    }

    // 게스트 전화번호 중복확인 및 인증번호 발송
    @PatchMapping("/telNumber/{guestId}")
    public ResponseEntity<ResponseDto> patchGuestTelNumber(
        @RequestBody @Valid PatchGuestTelNumberRequestDto requestBody,
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity<ResponseDto> response = guestService.patchGuestTelNumber(requestBody, guestId);
        return response;
    }

    // 게스트 인증번호 확인 및 전화번호 수정
    @PatchMapping("/Auth/{guestId}")
    public ResponseEntity<ResponseDto> patchGuestAuth(
        @RequestBody @Valid PatchGuestAuthRequestDto requestBody,
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity<ResponseDto> response = guestService.patchGuestAuth(requestBody, guestId);
        return response;
    }

    // 즐겨찾기 리스트보기 (해당 id에 대한)
    @GetMapping("/getBookMarkList/{guestId}")
    public ResponseEntity<? super GetGuestBookMarkResponseDto> getBookMarkList(
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity <? super GetGuestBookMarkResponseDto> response = bookmarkService.getBookMarkList(guestId);
        return response;
    }

    // 즐겨찾기 추가
    @PostMapping("/addBookMark/{guestId}")
    public ResponseEntity<ResponseDto> addBookMark(
        @RequestBody @Valid AddBookMarkRequestDto requestBody,
        @PathVariable("guestId") String guestId
    ) {
        ResponseEntity<ResponseDto> response = bookmarkService.addBookMark(requestBody,guestId);
        return response;
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/deleteBookMark/{guestId}/{accommodationName}")
    public ResponseEntity<ResponseDto> deleteBookMark(
        @PathVariable("guestId") String guestId,
        @PathVariable("accommodationName") String accommodationName
    ){
        ResponseEntity<ResponseDto> response = bookmarkService.deleteBookMark(guestId, accommodationName);
        return response;
    }

    // 예약 및 결제 창
    @GetMapping("/reservationView/{guestId}/{roomId}")
    public ResponseEntity<? super GetGuestReservationViewResponseDto> getReservationView(
        @PathVariable("guestId") String guestId,
        @PathVariable("roomId") Integer roomId
    ) {
        ResponseEntity<? super GetGuestReservationViewResponseDto> response = guestService.getReservationView(guestId, roomId);
        return response;
    }

    // 예약현황리스트
    @GetMapping("/ReservationStatus/{guestId}")
    public ResponseEntity<? super GetReservationStatusResponseDto> reservationStatus(
        @PathVariable("guestId") String guestId
    ){
        ResponseEntity<? super GetReservationStatusResponseDto> response = guestService.reservationStatus(guestId);
        return response;
    }

    // 리뷰작성하기
    @PostMapping("/review/{reservationId}/{guestId}")
    public ResponseEntity<ResponseDto> review(
        @RequestBody @Valid GuestReviewListRequestDto requestBody,
        @PathVariable("reservationId") Long reservationId,
        @PathVariable("guestId") String guestId
    ) {
        
        ResponseEntity<ResponseDto> response = guestService.review(requestBody,reservationId, guestId);
        return response;
    }

    // 게스트아이디에 관한 리뷰리스트 보기
    @GetMapping("review/guestId/{guestId}")
    public ResponseEntity<? super GetGuestReviewResponseDto> reveiwList(
        @PathVariable("guestId") String guestId
    ){
        ResponseEntity<? super GetGuestReviewResponseDto> response = guestService.guestReviewList(guestId);
        return response;
    }
    
    // 숙소명에 관한 리뷰리스트 보기
    @GetMapping("review/accommodationName/{accommodationName}")
    public ResponseEntity<? super GetAccommodationReviewResponseDto> accommodationReviewList(
        @PathVariable("accommodationName") String accommodationName
    ){
        ResponseEntity<? super GetAccommodationReviewResponseDto> response = guestService.accommodationReviewList(accommodationName);
        return response;
    }

    // 게스트 아이디 찾기
    @PostMapping(value={"/id-find"})
    public ResponseEntity<ResponseDto> guestIdFind(
        @RequestBody @Valid GuestIdFindRequsetDto requestBody){
            ResponseEntity<ResponseDto> responseBody = guestService.guestIdFind(requestBody);
            return responseBody;
    }

    @PostMapping(value={"/tel-auth-check"})
        public ResponseEntity<? super GuestIdFindSuccessResponseDto> guestTelAuthCheck(
            @RequestBody @Valid TelAuthCheckRequestDto requestBody
        ){
            ResponseEntity<? super GuestIdFindSuccessResponseDto> responseBody = guestService.guestTelAuthCheck(requestBody);
            return responseBody;
        }

}

