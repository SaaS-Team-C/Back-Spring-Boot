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

    ResponseEntity<ResponseDto> postAccommodation(PostAccommodationReqeustDto dto);
    ResponseEntity<ResponseDto> postAccommodationImage(PostAccommodationImageRequestDto dto);
    ResponseEntity<ResponseDto> patchAccommodation(PatchAccommodationRequestDto dto, String accommodationName, String hostId);
    ResponseEntity<ResponseDto> patchAccommodationImage(PatchAccommodationImageRequsetDto dto, String accommodationName, String accommodationImage);
    ResponseEntity<? super GetAccommodationResponseDto> getAccommodation(String accommodationName);
    ResponseEntity<? super GetAccommodationImagesResponseDto> getAccommodationImages(String accommodationName);
    ResponseEntity<? super GetAccommodationListResponseDto> getAccommodationList();
    ResponseEntity<ResponseDto> deleteAccommodation(String accommodationName);
}
