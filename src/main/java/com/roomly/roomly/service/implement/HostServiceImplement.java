package com.roomly.roomly.service.implement;

import java.util.List;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.roomly.roomly.common.object.Reservation;
import com.roomly.roomly.common.util.AuthNumberCreater;
import com.roomly.roomly.dto.request.guest.GuestIdFindRequsetDto;
import com.roomly.roomly.dto.request.host.HostIdFindRequestDto;
import com.roomly.roomly.dto.request.host.PatchHostPasswordRequestDto;
import com.roomly.roomly.dto.request.host.PatchHostTelNumberRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.admin.EntryHostRespnoseDto;
import com.roomly.roomly.dto.response.host.GetHostResponseDto;
import com.roomly.roomly.dto.response.host.HostIdFindSuccessResponseDto;
import com.roomly.roomly.dto.response.guest.GuestIdFindSuccessResponseDto;
import com.roomly.roomly.dto.response.reservation.GetReservationResponseDto;
import com.roomly.roomly.entity.AccommodationEntity;
import com.roomly.roomly.entity.GuestEntity;
import com.roomly.roomly.entity.HostEntity;
import com.roomly.roomly.entity.ReservationEntity;
import com.roomly.roomly.entity.RoomEntity;
import com.roomly.roomly.entity.TelAuthNumberEntity;
import com.roomly.roomly.provider.SmsProvider;
import com.roomly.roomly.repository.AccommodationRepository;
import com.roomly.roomly.repository.GuestRepository;
import com.roomly.roomly.repository.HostRepository;
import com.roomly.roomly.repository.ReservationRepository;
import com.roomly.roomly.repository.RoomRepository;
import com.roomly.roomly.repository.TelAuthNumberRepository;
import com.roomly.roomly.repository.resultSet.GetReservationResultSet;
import com.roomly.roomly.service.HostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostServiceImplement implements HostService{

    private final HostRepository hostRepository;
    private final TelAuthNumberRepository telAuthNumberRepository;
    private  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;
    private final GuestRepository guestRepository;
    private final SmsProvider smsProvider;
    

    @Override
    public ResponseEntity<? super GetHostResponseDto> getHost(String hostId) {

        HostEntity hostEntity;

        try {
            
            hostEntity = hostRepository.findByHostId(hostId);
            if (hostEntity == null) return ResponseDto.noExistUserId();
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetHostResponseDto.success(hostEntity);

    }

    @Override
    public ResponseEntity<ResponseDto> patchHostPassword(PatchHostPasswordRequestDto dto, String hostId) {
        
        String changePassword = dto.getHostPw();

        try {
            HostEntity hostEntity = hostRepository.findByHostId(hostId);
            if (hostEntity == null) return ResponseDto.noExistUserId();

            String host = hostEntity.getHostId();
            boolean isHost = host.equals(hostId);
            if(!isHost) return ResponseDto.noPermission();

            String encodedPassword = passwordEncoder.encode(changePassword);
            String basicPw = hostEntity.getHostPw();
            boolean isMatched = passwordEncoder.matches(changePassword, basicPw);

            if(isMatched) return ResponseDto.duplicatedPassword();
            
            hostEntity.setHostPw(encodedPassword);
            hostRepository.save(hostEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> patchHostTelNumber(PatchHostTelNumberRequestDto dto, String hostId, String hostTelNumber) {

        // 바꿀 전화번호 
        String changeTelNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();

        
        try {
            HostEntity hostEntity = hostRepository.findByHostId(hostId);
            if(hostEntity == null) return ResponseDto.noExistUserId();

            TelAuthNumberEntity telAuthNumberEntity = telAuthNumberRepository.findByTelNumberAndAuthNumber(changeTelNumber, authNumber);
            if (telAuthNumberEntity == null) return ResponseDto.telAuthFail();

            hostEntity.setHostTelNumber(changeTelNumber);
            hostRepository.save(hostEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        TelAuthNumberEntity telAuthNumberEntity = telAuthNumberRepository.findByTelNumber(hostTelNumber);
        if (telAuthNumberEntity == null) return ResponseDto.telAuthFail();
        telAuthNumberRepository.deleteByTelNumber(hostTelNumber);
        return ResponseDto.success();
    }


    @Override
    public ResponseEntity<? super GetReservationResponseDto> getRerservaitonList(String hostId) {

        List<GetReservationResultSet> resultSets = new ArrayList<>();
        List<Reservation> resrervationList = new ArrayList<>();

        try {

            boolean isExist = accommodationRepository.existsByHostId(hostId);
            if(!isExist) return ResponseDto.noExistAccommodation();

            resultSets = reservationRepository.getReservationList(hostId);
            if (resultSets == null) return ResponseDto.noExistReservation();

            for(GetReservationResultSet resultSet: resultSets){
                Reservation reservation = new Reservation(resultSet);
                resrervationList.add(reservation);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReservationResponseDto.success(resrervationList);
    }

    @Override
    public ResponseEntity<ResponseDto> hostIdFind(HostIdFindRequestDto dto) {
        
        String hostName = dto.getHostName();
        String hostTelNumber = dto.getHostTelNumber();

        try {

            boolean isMatched = hostRepository.existsByHostNameAndHostTelNumber(hostName, hostTelNumber);
            if (!isMatched) return ResponseDto.noExistUserId();

        } catch (Exception e)  {      
            e.printStackTrace();
            return ResponseDto.databaseError();
    }

        String authNumber = AuthNumberCreater.number4();

        boolean isSendSuccess = smsProvider.sendMessage(hostTelNumber, authNumber);
        if(!isSendSuccess) return ResponseDto.messageSendFail();

        try {
            TelAuthNumberEntity telAuthNumberEntity = new TelAuthNumberEntity(hostTelNumber, authNumber);
            telAuthNumberRepository.save(telAuthNumberEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    
}

    @Override
    public ResponseEntity<? super HostIdFindSuccessResponseDto> telAuthCheck(TelAuthCheckRequestDto dto) {
            
            String telNumber = dto.getTelNumber();
            String authNumber = dto.getAuthNumber();
            HostEntity hostEntity = null;

        try {

            TelAuthNumberEntity telAuthNumberEntity = telAuthNumberRepository.findByTelNumberAndAuthNumber(telNumber, authNumber);
            if (telAuthNumberEntity == null) return ResponseDto.telAuthFail();

            hostEntity = hostRepository.findByHostTelNumber(telNumber);
            if (hostEntity == null) return ResponseDto.noExistUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return HostIdFindSuccessResponseDto.success(hostEntity);
    }

    
    
}
