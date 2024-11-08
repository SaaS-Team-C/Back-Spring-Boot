package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.accommodation.PatchAccommodationRequestDto;
import com.roomly.roomly.dto.request.accommodation.PostAccommodationReqeustDto;
import com.roomly.roomly.dto.request.subImages.PatchAccommodationImageRequsetDto;
import com.roomly.roomly.dto.request.subImages.PostAccommodationImageRequestDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationImagesResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationListResponseDto;
import com.roomly.roomly.dto.response.ResponseDto;

public interface AccommodationService {

    // Accommodation 등록
    ResponseEntity<ResponseDto> postAccommodation(PostAccommodationReqeustDto dto);
    // Accommodation Sub Images 등록
    ResponseEntity<ResponseDto> postAccommodationImage(PostAccommodationImageRequestDto dto);
    // Accommodation 정보 수정
    ResponseEntity<ResponseDto> patchAccommodation(PatchAccommodationRequestDto dto, String accommodationName, String hostId);
    // Accommodation Sub Images 수정
    ResponseEntity<ResponseDto> patchAccommodationImage(PatchAccommodationImageRequsetDto dto, String accommodationName, String accommodationImage);
    // Accommodation 정보 보기
    ResponseEntity<? super GetAccommodationResponseDto> getAccommodation(String accommodationName);
    // Accommodation Sub Images 보기
    ResponseEntity<? super GetAccommodationImagesResponseDto> getAccommodationImages(String accommodationName);
    // Accommodation 리스트 보기
    ResponseEntity<? super GetAccommodationListResponseDto> getAccommodationList();
    // Accommodation 삭제 
    ResponseEntity<ResponseDto> deleteAccommodation(String accommodationName);
}
