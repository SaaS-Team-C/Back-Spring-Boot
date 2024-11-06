package com.roomly.roomly.service.implement;

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
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestMyPageResponseDto;
import com.roomly.roomly.dto.response.guest.GuestIdFindSuccessResponseDto;
import com.roomly.roomly.entity.GuestEntity;
import com.roomly.roomly.entity.TelAuthNumberEntity;
import com.roomly.roomly.provider.SmsProvider;
import com.roomly.roomly.repository.GuestRepository;
import com.roomly.roomly.repository.TelAuthNumberRepository;
import com.roomly.roomly.service.GuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestServiceImplement implements GuestService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SmsProvider smsProvider;
    private final GuestRepository guestRepository;
    private final TelAuthNumberRepository telAuthNumberRepository;

    @Override
    // 게스트Id에 관한 MyPageList 메서드
    public ResponseEntity<? super GetGuestMyPageResponseDto> getGuestMyPage(String guestId) {

        GuestEntity id = null;

        try {

            id = guestRepository.findByGuestId(guestId);
            if (id == null)
                return ResponseDto.noExistGuest();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetGuestMyPageResponseDto.success(id);
    }

    // 비밀번호 수정 메서드
    @Override
    public ResponseEntity<ResponseDto> patchGuestPw(
            PatchGuestPwRequestDto dto, String guestId) {

        String chnagePassword = dto.getGuestPw();
        try {

            GuestEntity guestEntity = guestRepository.findByGuestId(guestId);
            if (guestEntity == null)
                return ResponseDto.noExistGuest();

            String basicPw = guestEntity.getGuestPw();
            boolean isMatched = passwordEncoder.matches(chnagePassword, basicPw);
            if (isMatched)
                return ResponseDto.duplicatedPassword();

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
            if (isExistedTelNumber)
                return ResponseDto.duplicatedTelNumber();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        String authNumber = AuthNumberCreater.number4();

        boolean isSendSuccess = smsProvider.sendMessage(guestTelNumber, authNumber);
        if (!isSendSuccess)
            return ResponseDto.messageSendFail();

        try {

            TelAuthNumberEntity telAuthNumberEntity = new TelAuthNumberEntity(guestTelNumber, authNumber);
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
            if (!existsByGuestId)
                return ResponseDto.noExistGuest();

            boolean isMatchedAuth = telAuthNumberRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if (!isMatchedAuth)
                return ResponseDto.telAuthFail();

            GuestEntity guestEntity = guestRepository.findByGuestId(guestId);
            if (guestEntity == null)
                return ResponseDto.noExistGuest();

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
    public ResponseEntity<ResponseDto> guestIdFind(GuestIdFindRequsetDto dto) {
        String guestName = dto.getGuestName();
        String guestTelNumber = dto.getGusetTelNumber();

        try {

            boolean isMatched = guestRepository.existsByGuestNameAndGuestTelNumber(guestName, guestTelNumber);
            if (!isMatched)
                return ResponseDto.noExistUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        String authNumber = AuthNumberCreater.number4();

        boolean isSendSuccess = smsProvider.sendMessage(guestTelNumber, authNumber);
        if (!isSendSuccess)
            return ResponseDto.messageSendFail();

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

            TelAuthNumberEntity telAuthNumberEntity = telAuthNumberRepository.findByTelNumberAndAuthNumber(telNumber,
                    authNumber);
            if (telAuthNumberEntity == null)
                return ResponseDto.telAuthFail();

            guestEntity = guestRepository.findByGuestTelNumber(telNumber);
            if (guestEntity == null)
                return ResponseDto.noExistUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GuestIdFindSuccessResponseDto.success(guestEntity);
    }

}