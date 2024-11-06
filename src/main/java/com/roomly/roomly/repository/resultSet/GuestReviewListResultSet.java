package com.roomly.roomly.repository.resultSet;

public interface GuestReviewListResultSet {
    
    Integer getReservationId();
    String getAccommodationName();
    String getReviewDate();
    String getReviewContent();
    Integer getReviewGrade();
}
