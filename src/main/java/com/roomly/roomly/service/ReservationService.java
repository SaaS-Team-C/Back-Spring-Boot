package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.payment.PaymentSuccessRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReservationViewResponseDto;
import com.roomly.roomly.dto.response.guest.GetReservationStatusResponseDto;

public interface ReservationService {
    
    // 결제 성공 메서드
    ResponseEntity<ResponseDto> paymentSuccess(PaymentSuccessRequestDto requestBody);
    // 예약및 결제 창
    ResponseEntity<? super GetGuestReservationViewResponseDto> getReservationView(String guestId, Integer roomId);
    // 예약현황 리스트
    ResponseEntity<? super GetReservationStatusResponseDto> reservationStatus(String guestId);
}