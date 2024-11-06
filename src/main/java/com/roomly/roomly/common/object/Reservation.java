package com.roomly.roomly.common.object;
import com.roomly.roomly.repository.resultSet.GetReservationResultSet;
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