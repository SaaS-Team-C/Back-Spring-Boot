package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomly.roomly.dto.request.payment.PaymentSuccessRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roomly/payment")
@RequiredArgsConstructor
public class PaymentController {
    
    private final ReservationService reservationService;

    @PostMapping("/paymentSuccess")
    // 결제성공후
    public ResponseEntity<ResponseDto> paymentSuccess(
        @RequestBody @Valid PaymentSuccessRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = reservationService.paymentSuccess(requestBody);
        return response;
    }
}
