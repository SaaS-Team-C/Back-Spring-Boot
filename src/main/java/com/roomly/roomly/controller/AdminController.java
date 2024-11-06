package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomly.roomly.dto.request.admin.PatchEntryStatusRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.admin.EntryHostRespnoseDto;
import com.roomly.roomly.dto.response.admin.GetAccommodationListResponseDto;
import com.roomly.roomly.dto.response.admin.GetHostListResponseDto;
import com.roomly.roomly.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roomly/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;


    @GetMapping(value={"/info/list"})
    public ResponseEntity<? super GetHostListResponseDto> getHostList(){
        ResponseEntity<? super GetHostListResponseDto> responseBody = adminService.getHostList();
        return responseBody;
    }

    @GetMapping(value={"/info/detail/{hostId}"})
    public ResponseEntity<? super EntryHostRespnoseDto> geEntryhost(
        @PathVariable("hostId") String hostId
    ){
        ResponseEntity<? super EntryHostRespnoseDto> responseBody = adminService.getHost(hostId);
        return responseBody;
    }

    @PatchMapping(value={"/update/status/{hostId}"})
    public ResponseEntity<ResponseDto> patchEntryStatus(
        @RequestBody PatchEntryStatusRequestDto requestBody,
        @PathVariable("hostId") String hostId
    ){
        ResponseEntity<ResponseDto> responseBody = adminService.patchEntryStatus(requestBody, hostId);
        return responseBody;
    }

    @GetMapping(value={"/admin/accommodation-list"})
    public ResponseEntity<? super GetAccommodationListResponseDto> getAccommodationList(){
        ResponseEntity<? super GetAccommodationListResponseDto> responseBody = adminService.getAccommodationList();
        return responseBody;
    }

    @PatchMapping(value = {"/admin/accommodation-apply/{accommodationName}"})
    public ResponseEntity<ResponseDto> patchApplyStatus(
        @RequestBody PatchEntryStatusRequestDto requestBody,
        @PathVariable("accommodationName") String accommodationName
    ){
        ResponseEntity<ResponseDto> responseBody = adminService.patchApplyStatus(accommodationName);
        return responseBody;
    }

}
