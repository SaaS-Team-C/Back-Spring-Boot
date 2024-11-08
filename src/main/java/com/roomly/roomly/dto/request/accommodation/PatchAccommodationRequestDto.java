package com.roomly.roomly.dto.request.accommodation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchAccommodationRequestDto {


    private String accommodationMainImage;
    private String accommodationIntroduce;
    private String categoryArea;
    private boolean categoryPet;
    private boolean categoryNonSmokingArea;
    private boolean categoryIndoorSpa;
    private boolean categoryDinnerParty;
    private boolean categoryWifi;
    private boolean categoryCarPark;
    private boolean categoryPool;
    
}
