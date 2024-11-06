package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.useInformations.PatchUseInformationRequestDto;
import com.roomly.roomly.dto.request.useInformations.PostUseInformationRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;

public interface UseInfomationService {
    
    ResponseEntity<ResponseDto> postUseInformation(PostUseInformationRequestDto dto);
    ResponseEntity<ResponseDto> patchUseInformation(PatchUseInformationRequestDto dto, String accommodationName, Integer autoKey);
    ResponseEntity<ResponseDto> deleteUseInformation(Integer autoKey);
}
