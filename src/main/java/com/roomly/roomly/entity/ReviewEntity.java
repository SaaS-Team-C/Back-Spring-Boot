package com.roomly.roomly.entity;

import com.roomly.roomly.dto.request.guest.GuestReviewListRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Review")
@Table(name = "review")
public class ReviewEntity {
    
    @Id
    private Long reservationId;
    private String reviewContent;
    private Integer reviewGrade;
    private String reviewDate;

    public ReviewEntity(GuestReviewListRequestDto dto){
        this.reservationId = dto.getReservationId();
        this.reviewContent = dto.getReviewContent();
        this.reviewGrade = dto.getReviewGrade();
        this.reviewDate = dto.getReviewDate();
    }
}
