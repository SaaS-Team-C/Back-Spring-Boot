package com.roomly.roomly.repository.resultSet;


public interface GetRoomResultSet {
    Integer getRoomId();
    String getRoomName();
    Integer getRoomPrice();
    String getRoomCheckIn();
    String getRoomCheckOut();
    String getRoomTotalGuest();
}
