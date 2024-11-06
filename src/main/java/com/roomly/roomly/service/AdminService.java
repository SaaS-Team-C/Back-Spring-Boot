package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.admin.EntryHostRespnoseDto;
import com.roomly.roomly.dto.response.admin.GetGuestListResponseDto;
import com.roomly.roomly.dto.response.admin.GetHostListResponseDto;


import com.roomly.roomly.dto.request.admin.PatchEntryStatusRequestDto;
import com.roomly.roomly.dto.response.admin.GetAccommodationListResponseDto;


public interface AdminService {

    ResponseEntity<? super GetGuestListResponseDto> getGuestList();
    ResponseEntity<? super GetHostListResponseDto> getHostList();
    ResponseEntity<? super EntryHostRespnoseDto> getHost(String hostId);
    ResponseEntity<ResponseDto> patchEntryStatus(PatchEntryStatusRequestDto dto, String hostId);
    ResponseEntity<? super GetAccommodationListResponseDto> getAccommodationList();
    ResponseEntity<ResponseDto> patchApplyStatus(String accommodationName);
    
}
