package com.roomly.roomly.repository.resultSet;

public interface GetReservationResultSet {
    
    Integer getReservationNumber();
    String getReservationDay();
    String getGuestName();
    String getGuestTelNumber();
    Integer getReservationTotalPeople();
    Integer getRoomPrice();
    String getRoomName();
    String getAccommodationMainImage();
    String getAccommodationName();
}
