package com.roomly.roomly.repository;


import com.roomly.roomly.entity.AccommodationEntity;
import com.roomly.roomly.repository.resultSet.AccommodationReviewListResultSet;
import com.roomly.roomly.repository.resultSet.GetAccommodationListResultSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<AccommodationEntity, String> {

    AccommodationEntity findByAccommodationName(String accommodationName);
    boolean existsByAccommodationName(String accommodationName);
    boolean existsByHostId(String hostId);
    boolean existsByAccommodationNameAndAccommodationMainImage(String accommodationName, String accommodationMainImage);
    boolean existsByAccommodationMainImage(String accommodationMainImage);
    boolean existsByAccommodationNameAndAccommodationIntroduce(String accommodationName, String accommodationIntroduce);
    @Query(value=
    "SELECT * FROM accommodation",
    nativeQuery = true
    )
    List<AccommodationEntity> getAccommodationList();

    @Query(value= 
    "SELECT "+ 
    "A.accommodation_name,"+    
    "A.accommodation_main_image,"+
    "AVG(distinct RV.review_grade) as accommodationGradeAverage," +
    "A.accommodation_type,"+
    "A.category_area," +
    "A.category_pet," +
    "A.category_non_smoking_area," +
    "A.category_indoor_spa," +
    "A.category_dinner_party,"+
    "A.category_wifi,"+
    "A.category_car_park,"+
    "A.category_pool,"+
    "A.apply_status,"+
    "MIN(R.room_price) as minRoomPrice,"+
    "COUNT(distinct RV.reservation_id) as countReview " +
    "FROM accommodation A " +
    "LEFT JOIN room R ON A.accommodation_name = R.accommodation_name " +
    "LEFT JOIN reservation RS ON R.room_id = RS.room_id " +
    "LEFT JOIN review RV ON RS.reservation_id = RV.reservation_id " +
    "GROUP BY A.accommodation_name " ,
    nativeQuery = true
    )
    List<GetAccommodationListResultSet> getList();

    @Query(value = 
    "SELECT" +
    "    G.guest_name as guestName, " +
    "    RV.review_date as reviewDate, " +
    "    RV.review_grade as reviewGrade, " +
    "    RV.review_content as reviewContent " +
    "FROM review as RV JOIN reservation as RS " +
    "ON RV.reservation_id = RS.reservation_id " +
    "JOIN guest as G ON RV.guest_id = G.guest_id " +
    "JOIN room as R ON RS.room_id = R.room_id "+
    "WHERE R.accommodation_name = :accommodationName ",
    nativeQuery = true)
    List<AccommodationReviewListResultSet> getAccommodationReviewList(@Param("accommodationName") String accommodationName);

    
    
}