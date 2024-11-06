package com.roomly.roomly.dto.response.admin;

import com.roomly.roomly.common.object.Guest;
import com.roomly.roomly.dto.response.ResponseCode;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.ResponseMessage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;


@Getter
public class GetGuestListResponseDto extends ResponseDto {

    private List<Guest> guestList;

    public GetGuestListResponseDto(List<Guest> guestList){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        this.guestList = guestList;
    }

    public static ResponseEntity<GetGuestListResponseDto> success(List<Guest> guestList) {
        GetGuestListResponseDto responseBody = new GetGuestListResponseDto(guestList);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
