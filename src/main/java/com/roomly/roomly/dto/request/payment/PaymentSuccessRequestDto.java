package com.roomly.roomly.dto.request.payment;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentSuccessRequestDto{
    
    
    private Long totalPrice; // 결제가격(숙박일수*객실1박가격)
    private String checkInDay; // 체크인날짜
    private String checkOutDay; // 체크아웃날짜
    private Integer reservationTotalPeople; // 예약 총 인원
    private String createdAt; // 예약 생성일
    private Integer totalNight; // x박 수 
    private String guestId; // 게스트
    private Integer roomId; // 객실 고유 번호
    
}
