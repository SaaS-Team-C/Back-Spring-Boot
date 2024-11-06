package com.roomly.roomly.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomly.roomly.dto.request.payment.PaymentSuccessRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReservationViewResponseDto;
import com.roomly.roomly.dto.response.guest.GetReservationStatusResponseDto;
import com.roomly.roomly.entity.ReservationEntity;
import com.roomly.roomly.repository.GuestRepository;
import com.roomly.roomly.repository.ReservationRepository;
import com.roomly.roomly.repository.RoomRepository;
import com.roomly.roomly.repository.resultSet.GetReservationStatusResultSet;
import com.roomly.roomly.repository.resultSet.GetReservationViewResultSet;
import com.roomly.roomly.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImplement implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    @Override
    // 결제 성공시
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

    @Override
    // 예약 결제 창
    public ResponseEntity<? super GetGuestReservationViewResponseDto> getReservationView(String guestId, Integer roomId) {
        GetReservationViewResultSet resultSet = null;
        
        try {
            boolean isGuest = guestRepository.existsByGuestId(guestId);
            if (!isGuest) return ResponseDto.noExistGuest();
            boolean isRoom = roomRepository.existsByRoomId(roomId);
            if (!isRoom) return ResponseDto.noExistRoom();

            resultSet = roomRepository.getReservationView(guestId, roomId);
            

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();

        }
        return GetGuestReservationViewResponseDto.success(resultSet);
    }

    @Override
    // 예약 현황 확인하기
    public ResponseEntity<? super GetReservationStatusResponseDto> reservationStatus(String guestId) {
        
        List<GetReservationStatusResultSet> resultSet = new ArrayList<>();

        try {
            resultSet = reservationRepository.getReservationStatus(guestId);
            if(resultSet == null) return ResponseDto.noExistUserId();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetReservationStatusResponseDto.success(resultSet);
    }
    
}
