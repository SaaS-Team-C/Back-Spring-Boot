package com.roomly.roomly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.room.GetRoomResponseDto;
import com.roomly.roomly.dto.response.room.GetRoomImageResponseDto;
import com.roomly.roomly.service.RoomService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roomly/room")
public class RoomController {

    private final RoomService roomService;
    
    // 객실 상세보기 api
    @GetMapping(value={"/detail/{roomId}"})
    public ResponseEntity<? super GetRoomResponseDto> getRoom(
        @PathVariable("roomId") Integer roomId
    ){
        ResponseEntity<? super GetRoomResponseDto> responseBody = roomService.getRoom(roomId);
        return responseBody;
    }

    // 객실 서브이미지리스트 상세보기 api
    @GetMapping(value={"/Images/{roomId}"})
    public ResponseEntity<? super GetRoomImageResponseDto> getRoomImage(
        @PathVariable("roomId") Integer roomId
    ){
        ResponseEntity<? super GetRoomImageResponseDto> responseBody = roomService.getRoomImage(roomId);
        return responseBody;
    }
    
    // 객실 삭제 api
    @DeleteMapping(value = {"/delete/{roomId}"})
    public ResponseEntity<ResponseDto> deleteRoom(
        @PathVariable("roomId") Integer roomId
    ){
        ResponseEntity<ResponseDto> responseBody = roomService.deleteRoom(roomId);
        return responseBody;
    }
    
}
