package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.payment.PaymentSuccessRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;

public interface ReservationService {
    
    // 결제 성공 메서드
    ResponseEntity<ResponseDto> paymentSuccess(PaymentSuccessRequestDto requestBody);
}