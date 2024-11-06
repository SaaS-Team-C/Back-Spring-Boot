package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.host.HostIdFindRequestDto;
import com.roomly.roomly.dto.request.host.PatchHostPasswordRequestDto;
import com.roomly.roomly.dto.request.host.PatchHostTelNumberRequestDto;
import com.roomly.roomly.dto.request.host.TelAuthCheckRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.host.GetHostResponseDto;
import com.roomly.roomly.dto.response.reservation.GetReservationResponseDto;
import com.roomly.roomly.dto.response.host.HostIdFindSuccessResponseDto;

public interface HostService {

    ResponseEntity<? super GetHostResponseDto> getHost(String hostId);

    ResponseEntity<ResponseDto> patchHostPassword(PatchHostPasswordRequestDto dto, String hostId);

    ResponseEntity<ResponseDto> patchHostTelNumber(PatchHostTelNumberRequestDto dto, String hostId,
            String hostTelNumber);

    ResponseEntity<? super GetReservationResponseDto> getRerservaitonList(String hostId);

    ResponseEntity<ResponseDto> hostIdFind(HostIdFindRequestDto dto);

    ResponseEntity<? super HostIdFindSuccessResponseDto> telAuthCheck(TelAuthCheckRequestDto dto);

}
