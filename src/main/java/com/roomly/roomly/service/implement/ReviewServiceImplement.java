package com.roomly.roomly.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomly.roomly.dto.request.guest.GuestReviewListRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetAccommodationReviewResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestReviewResponseDto;
import com.roomly.roomly.entity.ReviewEntity;
import com.roomly.roomly.repository.AccommodationRepository;
import com.roomly.roomly.repository.GuestRepository;
import com.roomly.roomly.repository.ReservationRepository;
import com.roomly.roomly.repository.ReviewRepository;
import com.roomly.roomly.repository.resultSet.AccommodationReviewListResultSet;
import com.roomly.roomly.repository.resultSet.GuestReviewListResultSet;
import com.roomly.roomly.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplement implements ReviewService{
    

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    // 리뷰작성
    public ResponseEntity<ResponseDto> addReview(GuestReviewListRequestDto dto, Long reservationId ,String guestId) {

        ReviewEntity reviewEntity = null;

        try {
            boolean existsByreservationId = reservationRepository.existsByreservationId(reservationId);
            if(!existsByreservationId) return ResponseDto.noExistReservationId();
            
            boolean existsByGuestId = guestRepository.existsByGuestId(guestId);
            if(!existsByGuestId) return ResponseDto.noExistUserId();
            
            Long reservationIdTest = dto.getReservationId();
            boolean isReservationdId = reservationIdTest.equals(reservationId);
            if(!isReservationdId) return ResponseDto.notMatchValue(); 

            String guestsIdTest = dto.getGuestId();
            boolean isGuestId = guestsIdTest.equals(guestId);
            if (!isGuestId) return ResponseDto.notMatchValue();

            boolean iseixst = reservationRepository.existsByReservationIdAndGuestId(reservationId, guestId);
            if (!iseixst) return ResponseDto.notMatchValue();

            reviewEntity = new ReviewEntity(dto);
            reviewRepository.save(reviewEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
            return ResponseDto.success();
    }

    @Override
    // 해당 Id의 리뷰 List 보기
    public ResponseEntity<? super GetGuestReviewResponseDto> guestReviewList(String guestId) {
        
        List<GuestReviewListResultSet> resultSet = new ArrayList<>();


        try {
            // 해당 아이디가 review 테이블에 있는지 유효성 검사
            boolean id = reviewRepository.existsByGuestId(guestId);
            if (!id) return ResponseDto.noExistUserId();
            
            // 해당 아이디에 대해 작성한 리뷰가 있는지 검사
            resultSet = guestRepository.getReviewList(guestId);
            if (resultSet == null) return ResponseDto.noExistReviewId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetGuestReviewResponseDto.success(resultSet);
    }

    @Override
    // 숙소에 관한 리뷰 리스트 확인하기
    public ResponseEntity<? super GetAccommodationReviewResponseDto> accommodationReviewList(String accommodationName) {
    
        List<AccommodationReviewListResultSet> resultSet = new ArrayList<>();

        try {
            // 해당 숙소가 있는지 유효성 검사
            boolean accommodation = accommodationRepository.existsByAccommodationName(accommodationName);
            if (!accommodation) return ResponseDto.noExistAccommodation();
            
            // 해당 숙소에 대해 작성한 리뷰가 있는지 검사
            resultSet = accommodationRepository.getAccommodationReviewList(accommodationName);
            if (resultSet == null) return ResponseDto.noExistReviewId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetAccommodationReviewResponseDto.success(resultSet);
    }
}
