package com.roomly.roomly.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roomly.roomly.entity.ReservationEntity;
import com.roomly.roomly.repository.resultSet.GetReservationResultSet;
import com.roomly.roomly.repository.resultSet.GetReservationStatusResultSet;

import java.util.Date;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer>{
    

    // @Query(value= 
    // "SELECT " +
    // " A.accommodation_name,"+
    // " A.accommodation_main_image,"+
    // " V.reservation_number,"+
    // " V.reservation_day,"+
    // " V.reservation_total_people,"+
    // " G.guest_name,"+
    // " G.guest_tel_number,"+
    // " R.room_name,"+
    // " R.room_price "+
    // " FROM accommodation A LEFT JOIN reservation V "+
    // " ON A.accommodation_name = V.accommodation_name "+
    // " LEFT JOIN guest G "+
    // " ON V.guest_id = G.guest_id "+
    // " LEFT JOIN room R "+
    // " ON V.room_id = R.room_id "+
    // " WHERE A.host_id = :hostId ",
    // nativeQuery = true
    // )
    @Query(value= 
    "SELECT " +
    " A.accommodation_name,"+
    " A.accommodation_main_image,"+
    " V.reservation_number,"+
    " V.reservation_day,"+
    " V.reservation_total_people,"+
    " G.guest_name,"+
    " G.guest_tel_number,"+
    " R.room_name,"+
    " R.room_price "+
    " FROM reservation V LEFT JOIN room R "+
    " ON V.room_id = R.room_id "+
    " LEFT JOIN accommodation A "+
    " ON R.accommodation_name = A.accommodation_name "+
    " LEFT JOIN guest G "+
    " ON V.guest_id= G.guest_id "+
    " WHERE A.host_id = :hostId ",
    nativeQuery = true
    )
    List<GetReservationResultSet> getReservationList(@Param("hostId") String accommodationName);

    boolean existsByRoomId(Integer roomId);

    @Query(value= 
    "SELECT check_out_day FROM reservation where room_id= :roomId",
    nativeQuery = true
    )
    List<Date> getReservationEnd(@Param("roomId") Integer roomId);

    @Query(value = 
    "SELECT" + 
    "    V.created_at as createdAt, " +
    "    V.reservation_id as reservationId, " +
    "    A.accommodation_main_image as AccommodationMainImage, " +
    "    A.accommodation_name as AccommodationName, " +
    "    R.room_name as RoomName, " +
    "    R.room_check_in as RoomCheckIn, " +
    "    R.room_check_out as RoomCheckOut, " +
    "    V.reservation_total_people as ReservationTotalPeople, " +
    "    V.total_price as totalPrice, " +
    "    V.total_night as totalNight " +
    "FROM reservation as V JOIN accommodation as A JOIN room as R " +
    "ON V.room_id = R.room_id AND R.accommodation_name = A.accommodation_name " +
    "JOIN guest as G ON V.guest_id = G.guest_id " +
    "WHERE V.guest_id = :guestId",
    nativeQuery = true
    )
    List<GetReservationStatusResultSet> getReservationStatus(
        @Param("guestId")String guestId
    );

    boolean existsByreservationId(Long reservationId);

    boolean existsByReservationIdAndGuestId(Long reservationId, String guestId);

    boolean existsByRoomIdAndCheckInDayAndCheckOutDay(Integer roomId, String checkInDay, String checkOutDay);

    
}
