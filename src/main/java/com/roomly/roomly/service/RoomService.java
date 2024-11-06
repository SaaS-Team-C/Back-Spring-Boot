package com.roomly.roomly.service;

import org.springframework.http.ResponseEntity;

import com.roomly.roomly.dto.request.room.PatchRoomRequestDto;
import com.roomly.roomly.dto.request.room.PostRoomRequestDto;
import com.roomly.roomly.dto.request.subImages.PatchRoomImageRequestDto;
import com.roomly.roomly.dto.request.subImages.PostRoomImageRequestDto;
import com.roomly.roomly.dto.response.room.GetRoomResponseDto;
import com.roomly.roomly.dto.response.room.GetRoomImageResponseDto;

import com.roomly.roomly.dto.response.ResponseDto;

public interface RoomService {

    ResponseEntity<ResponseDto> postRoom(PostRoomRequestDto dto, String accommodationName);
    ResponseEntity<ResponseDto> patchRoom(PatchRoomRequestDto dto, String accommodationName, Integer roomId);
    ResponseEntity<ResponseDto> postRoomImage(PostRoomImageRequestDto dto, String accommodationName,Integer roomId );
    ResponseEntity<ResponseDto> patchRoomImage(PatchRoomImageRequestDto dto, String accommodationName, Integer roomId, String roomImage);
    ResponseEntity<? super GetRoomResponseDto> getRoom(Integer roomId);
    ResponseEntity<? super GetRoomImageResponseDto> getRoomImage(Integer roomId);
    ResponseEntity<ResponseDto> deleteRoom(Integer roomId);
    
}
