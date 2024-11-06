package com.roomly.roomly.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomly.roomly.dto.request.payment.PaymentSuccessRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.entity.ReservationEntity;
import com.roomly.roomly.repository.ReservationRepository;
import com.roomly.roomly.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplement implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    // 결제 성공시 저장되는 테이블
    public ResponseEntity<ResponseDto> paymentSuccess(PaymentSuccessRequestDto dto) {
        
        try {
            
            Integer roomId = dto.getRoomId();
            String checkInDay = dto.getCheckInDay();
            String checkOutDay = dto.getCheckOutDay();

        
            boolean isExist = reservationRepository.existsByRoomIdAndCheckInDayAndCheckOutDay(roomId,checkInDay, checkOutDay);
            if (isExist) return ResponseDto.noExistReservation();

            ReservationEntity reservationEntity = new ReservationEntity(dto);
            reservationRepository.save(reservationEntity);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
            return ResponseDto.success();
    }
    
}
