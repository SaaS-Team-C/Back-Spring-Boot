package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomly.roomly.dto.request.accommodation.PatchAccommodationRequestDto;
import com.roomly.roomly.dto.request.accommodation.PostAccommodationReqeustDto;
import com.roomly.roomly.dto.request.subImages.PatchAccommodationImageRequsetDto;
import com.roomly.roomly.dto.request.subImages.PostAccommodationImageRequestDto;
import com.roomly.roomly.dto.request.useInformations.PatchUseInformationRequestDto;
import com.roomly.roomly.dto.request.useInformations.PostUseInformationRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.service.AccommodationService;
import com.roomly.roomly.service.UseInfomationService;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationListResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationImagesResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roomly/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final UseInfomationService useInfomationService;

    
    @PostMapping(value={"/register"})
    public ResponseEntity<ResponseDto> postAccommodation(
        @RequestBody @Valid PostAccommodationReqeustDto reqeustBody){
            ResponseEntity<ResponseDto> responseBody = accommodationService.postAccommodation(reqeustBody);
            return responseBody;
    }

    @PostMapping(value={"/image"})
    public ResponseEntity<ResponseDto> postAccommodationImage(
        @RequestBody @Valid PostAccommodationImageRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = accommodationService.postAccommodationImage(requestBody);
        return responseBody;
    }

    @PostMapping(value = {"/information"})
    public ResponseEntity<ResponseDto> postUseInformation(
        @RequestBody @Valid PostUseInformationRequestDto requestBody){
            ResponseEntity<ResponseDto> responseBody = useInfomationService.postUseInformation(requestBody);
            return responseBody;
        }
    
    @GetMapping(value={"/{accommodationName}"})
    public ResponseEntity<? super GetAccommodationResponseDto> getAccommodation(
        @PathVariable("accommodationName") String accommodationName
    ){
        ResponseEntity<? super GetAccommodationResponseDto> responseBody = accommodationService.getAccommodation(accommodationName);
        return responseBody;
    }

    @PatchMapping(value={"/update/{accommodationName}"})
    public ResponseEntity<ResponseDto> patchAccommodation(
        @PathVariable("accommodationName") String accommodationName,
        @RequestBody PatchAccommodationRequestDto requestBody,
        @AuthenticationPrincipal String hostId
    ){
        ResponseEntity<ResponseDto> responseBody = accommodationService.patchAccommodation(requestBody,accommodationName, hostId);
        return responseBody;
    }

    @PatchMapping(value = {"/information/update/{accommodationName}/{autoKey}"})
    public ResponseEntity<ResponseDto> patchUseInformation(
        @RequestBody @Valid PatchUseInformationRequestDto requestBody,
        @PathVariable("accommodationName") String accommodationName,
        @PathVariable("autoKey") Integer autoKey
        ){
            ResponseEntity<ResponseDto> responseBody = useInfomationService.patchUseInformation(requestBody, accommodationName, autoKey);
            return responseBody;
        }
    
    @PatchMapping(value = {"/image/update/{accommodationName}/{accommodationImage}"})
    public ResponseEntity<ResponseDto> patchAccommodationImage(
        @RequestBody @Valid PatchAccommodationImageRequsetDto requestBody,
        @PathVariable("accommodationName") String accommodationName,
        @PathVariable("accommodationImage") String accommodationImage
    ){
        ResponseEntity<ResponseDto> responseBody = accommodationService.patchAccommodationImage(requestBody, accommodationName, accommodationImage);
        return responseBody;
    }

    @GetMapping(value={"/image/{accommodationName}"})
    public ResponseEntity<? super GetAccommodationImagesResponseDto> getAccommodationImages(
        @PathVariable("accommodationName") String accommodationName
    ){
        ResponseEntity<? super GetAccommodationImagesResponseDto> responseBody = accommodationService.getAccommodationImages(accommodationName);
        return responseBody;
    }

    @GetMapping(value={"/list"})
    public ResponseEntity<? super GetAccommodationListResponseDto> getAccommodationList(){
        ResponseEntity<? super GetAccommodationListResponseDto> responseBody = accommodationService.getAccommodationList();
        return responseBody;
    }

    @DeleteMapping(value={"/delete/{accommodationName}"})
    public ResponseEntity<ResponseDto> deleteAccommodation(
        @PathVariable("accommodationName") String accommodationName,
        @AuthenticationPrincipal String hostId
    ){
        ResponseEntity<ResponseDto> responseBody = accommodationService.deleteAccommodation(accommodationName);
        return responseBody;
    }
    @DeleteMapping(value={"/information/delete/{autoKey}"})
    public ResponseEntity<ResponseDto> deleteUseInformation(
        @PathVariable("autoKey") Integer autoKey,
        @AuthenticationPrincipal String hostId
    ){
        ResponseEntity<ResponseDto> responseBody = useInfomationService.deleteUseInformation(autoKey);
        return responseBody;
    }
}
