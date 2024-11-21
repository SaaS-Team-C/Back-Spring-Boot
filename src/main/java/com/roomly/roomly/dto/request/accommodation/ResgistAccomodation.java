package com.roomly.roomly.dto.request.accommodation;

import java.time.LocalDateTime;
import java.util.List;

import com.roomly.roomly.dto.request.room.PostRoomRequestDto;

import com.roomly.roomly.dto.request.useInformations.PostUseInformationRequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResgistAccomodation {


    // private PostAccommodationReqeustDto accommodationReqeustDto;
    @NotBlank
    private String accommodationName;
    @NotBlank
    private String accommodationMainImage;
    @NotBlank
    private String accommodationAddress;
    @NotBlank
    @Pattern(regexp="^(호텔|팬션|리조트)$")
    private String accommodationType;
    @NotBlank
    private String accommodationIntroduce;

    private String categoryArea;
    private boolean categoryPet;
    private boolean categoryNonSmokingArea;
    private boolean categoryIndoorSpa;
    private boolean categoryDinnerParty;
    private boolean categoryWifi;
    private boolean categoryCarPark;
    private boolean categoryPool;

    @NotBlank
    private String hostId;
    private Integer applyStatus;
    private LocalDateTime entryTime;

    private List<String> accommodationImages; // 숙소 서브 이미지

    private List<PostUseInformationRequestDto> useInformations;

    private List<PostRoomRequestDto> roomRequestDtoList;
    
}
