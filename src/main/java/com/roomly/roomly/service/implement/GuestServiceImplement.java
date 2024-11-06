package com.roomly.roomly.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roomly.roomly.common.util.AuthNumberCreater;
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
import com.roomly.roomly.entity.GuestEntity;
import com.roomly.roomly.entity.ReviewEntity;
import com.roomly.roomly.entity.TelAuthNumberEntity;
import com.roomly.roomly.provider.SmsProvider;
import com.roomly.roomly.repository.AccommodationRepository;
import com.roomly.roomly.repository.GuestRepository;
import com.roomly.roomly.repository.ReservationRepository;
import com.roomly.roomly.repository.ReviewRepository;
import com.roomly.roomly.repository.RoomRepository;
import com.roomly.roomly.repository.TelAuthNumberRepository;
import com.roomly.roomly.repository.resultSet.AccommodationReviewListResultSet;
import com.roomly.roomly.repository.resultSet.GetReservationStatusResultSet;
import com.roomly.roomly.repository.resultSet.GetReservationViewResultSet;
import com.roomly.roomly.repository.resultSet.GuestReviewListResultSet;
import com.roomly.roomly.service.GuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestServiceImplement implements GuestService{

    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SmsProvider smsProvider;
    private final GuestRepository guestRepository;
    private final TelAuthNumberRepository telAuthNumberRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    // 게스트Id에 관한 MyPageList 메서드
    public ResponseEntity<? super GetGuestMyPageResponseDto> getGuestMyPage(String guestId) {
        
        GuestEntity id = null;

        try {
            
            id = guestRepository.findByGuestId(guestId);
            if (id == null) return ResponseDto.noExistGuest();
        
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetGuestMyPageResponseDto.success(id);
    }

    //비밀번호 수정 메서드 
    @Override
    public ResponseEntity<ResponseDto> patchGuestPw(
        PatchGuestPwRequestDto dto, String guestId) {
        
            String chnagePassword = dto.getGuestPw();
        try {
            
            GuestEntity guestEntity = guestRepository.findByGuestId(guestId);
            if (guestEntity == null) return ResponseDto.noExistGuest();

            String basicPw = guestEntity.getGuestPw();
            boolean isMatched = passwordEncoder.matches(chnagePassword, basicPw);
            if(isMatched) return ResponseDto.duplicatedPassword();

            String encodedPassword = passwordEncoder.encode(chnagePassword);
            dto.setGuestPw(encodedPassword);
            guestEntity.patchPw(dto);
            guestRepository.save(guestEntity);
            

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    // 전화번호 수정 
    @Override
    public ResponseEntity<ResponseDto> patchGuestTelNumber(
        PatchGuestTelNumberRequestDto dto, String guestId) {
        
        String guestTelNumber = dto.getTelNumber();

        try {

            boolean isExistedTelNumber = guestRepository.existsByGuestTelNumber(guestTelNumber);
            if(isExistedTelNumber) return ResponseDto.duplicatedTelNumber();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();   
        }

        String authNumber = AuthNumberCreater.number4();

        boolean isSendSuccess =  smsProvider.sendMessage(guestTelNumber, authNumber);
        if (!isSendSuccess) return ResponseDto.messageSendFail();
        
        try {
            
            TelAuthNumberEntity telAuthNumberEntity = new TelAuthNumberEntity(guestTelNumber,authNumber);
            telAuthNumberRepository.save(telAuthNumberEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return ResponseDto.success();
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto> patchGuestAuth(
        PatchGuestAuthRequestDto dto, String guestId) {
            
            String telNumber = dto.getGuestTelNumber();
            String authNumber = dto.getGuestAuthNumber();

            try {
                boolean existsByGuestId = guestRepository.existsByGuestId(guestId);
                if (!existsByGuestId) return ResponseDto.noExistGuest();
                
                boolean isMatchedAuth =  telAuthNumberRepository.existsByTelNumberAndAuthNumber(telNumber,authNumber);
                if(!isMatchedAuth) return ResponseDto.telAuthFail();
                
                GuestEntity guestEntity = guestRepository.findByGuestId(guestId);
                if(guestEntity == null) return ResponseDto.noExistGuest();

                String oldTelNumber = guestEntity.getGuestTelNumber();

                guestEntity.patchTelNumber(dto);
                guestRepository.save(guestEntity);


                telAuthNumberRepository.deleteByTelNumber(oldTelNumber);
                

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.databaseError();
            }

            return ResponseDto.success();
    }

    @Override
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

    @Override
    // 리뷰작성 메서드()
    public ResponseEntity<ResponseDto> review(GuestReviewListRequestDto dto, Long reservationId ,String guestId) {

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

    @Override
    public ResponseEntity<ResponseDto> guestIdFind(GuestIdFindRequsetDto dto) {
        String guestName = dto.getGuestName();
        String guestTelNumber = dto.getGusetTelNumber();

        try {

            boolean isMatched = guestRepository.existsByGuestNameAndGuestTelNumber(guestName, guestTelNumber);
            if (!isMatched) return ResponseDto.noExistUserId();

        } catch (Exception e)  {      
            e.printStackTrace();
            return ResponseDto.databaseError();
    }

        String authNumber = AuthNumberCreater.number4();

        boolean isSendSuccess = smsProvider.sendMessage(guestTelNumber, authNumber);
        if(!isSendSuccess) return ResponseDto.messageSendFail();

        try {
            TelAuthNumberEntity telAuthNumberEntity = new TelAuthNumberEntity(guestTelNumber, authNumber);
            telAuthNumberRepository.save(telAuthNumberEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    
}
    

    @Override
    public ResponseEntity<? super GuestIdFindSuccessResponseDto> guestTelAuthCheck(TelAuthCheckRequestDto dto) {
            String telNumber = dto.getTelNumber();
            String authNumber = dto.getAuthNumber();
            GuestEntity guestEntity = null;

        try {

            TelAuthNumberEntity telAuthNumberEntity = telAuthNumberRepository.findByTelNumberAndAuthNumber(telNumber, authNumber);
            if (telAuthNumberEntity == null) return ResponseDto.telAuthFail();

            guestEntity = guestRepository.findByGuestTelNumber(telNumber);
            if (guestEntity == null) return ResponseDto.noExistUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GuestIdFindSuccessResponseDto.success(guestEntity);
    }

}