package com.roomly.roomly.common.object;
import com.roomly.roomly.entity.AccommodationEntity;
import com.roomly.roomly.entity.GuestEntity;
import com.roomly.roomly.entity.ReservationEntity;
import com.roomly.roomly.entity.RoomEntity;
import com.roomly.roomly.entity.UseInformationEntity;
import com.roomly.roomly.repository.resultSet.GetReservationResultSet;
import java.util.List;
import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Reservation{

    private String accommodationName;
    private Integer reservationNumber;
    private String reservationDay;
    private Integer reservationTotalPeople;
    private String roomName;
    private String accommodationMainImage;
    private String guestName;
    private String guestTelNumber;

    public Reservation(GetReservationResultSet resultSets){
        this.accommodationName = resultSets.getAccommodationName();
        this.reservationNumber = resultSets.getReservationNumber();
        this.reservationDay = resultSets.getReservationDay();
        this.reservationTotalPeople = resultSets.getReservationTotalPeople();
        this.roomName = resultSets.getRoomName();
        this.accommodationMainImage = resultSets.getAccommodationMainImage();
        this.guestName = resultSets.getGuestName();
        this.guestTelNumber = resultSets.getGuestTelNumber();
    }

}