package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.host.HostIdFindRequestDto;
import com.roomly.roomly.dto.request.host.HostMyPageRequestDto;
import com.roomly.roomly.dto.request.host.PatchHostPasswordRequestDto;
import com.roomly.roomly.dto.request.host.PatchHostTelNumberRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.host.GetHostResponseDto;
import com.roomly.roomly.dto.response.reservation.GetReservationResponseDto;
import com.roomly.roomly.dto.response.host.HostIdFindSuccessResponseDto;

public interface HostService {

    // 마이페이지 호스트 정보 가져오기
    ResponseEntity<? super GetHostResponseDto> getHost(HostMyPageRequestDto dto, String hostId);
    // 호스트 비밀번호 변경
    ResponseEntity<ResponseDto> patchHostPassword(PatchHostPasswordRequestDto dto, String hostId);
    // 호스트 전화번호 변경 및 전화번호 재인증
    ResponseEntity<ResponseDto> patchHostTelNumber(PatchHostTelNumberRequestDto dto, String hostId);
    // 호스트 숙소별 예약 리스트
    ResponseEntity<? super GetReservationResponseDto> getRerservaitonList(String hostId);
    // 호스트 아이디 찾기 
    ResponseEntity<ResponseDto> hostIdFind(HostIdFindRequestDto dto);
    // 호스트 아이디 찾기에 사용된 전화번호 인증
    ResponseEntity<? super HostIdFindSuccessResponseDto> telAuthCheck(TelAuthCheckRequestDto dto);

}
