package com.roomly.roomly.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roomly.roomly.common.object.Guest;
import com.roomly.roomly.entity.GuestEntity;
import com.roomly.roomly.repository.resultSet.GuestReviewListResultSet;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, String> {

    @Query(value =
    "SELECT "+
    "guest_id, guest_name, guest_tel_number "+
    "FROM guest ",
    nativeQuery = true 
    )
    List<Guest> getList();

    boolean existsByGuestNameAndGuestTelNumber(String guestName, String guestTelNumber);
    GuestEntity findByGuestTelNumber(String guestTelNumber);
    
    boolean existsByGuestId(String guestId);
    boolean existsByGuestTelNumber(String guestTelNumber);
    GuestEntity findByGuestId(String guestId);
    // sns 가입할때 필요한 snsid 와 가입경로
    GuestEntity findBySnsIdAndJoinPath(String sns, String joinPath);

    @Query(value = 
    "SELECT" +
    "    V.reservation_id as reservationId, " +
    "    R.accommodation_name as accommodationName, " +
    "    V.review_date as reviewDate, " +
    "    V.review_content as reviewContent," +
    "    V.review_grade as reviewGrade " +
    "FROM review as V JOIN reservation as RS " +
    "ON V.reservation_id = RS.reservation_id " +
    "JOIN room as R ON RS.room_id = R.room_id "+
    "WHERE RS.guest_id = V.guest_id " +
    "AND RS.guest_id = :guestId ",
    nativeQuery = true)
    List<GuestReviewListResultSet> getReviewList(@Param("guestId") String guestId);
}
