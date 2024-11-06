package com.roomly.roomly.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomly.roomly.dto.request.guest.AddBookMarkRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.guest.GetGuestBookMarkResponseDto;
import com.roomly.roomly.entity.BookmarkEntity;
import com.roomly.roomly.repository.AccommodationRepository;
import com.roomly.roomly.repository.BookMarkRepository;
import com.roomly.roomly.repository.GuestRepository;
import com.roomly.roomly.repository.resultSet.GetBookMarkResultSet;
import com.roomly.roomly.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImplement implements BookmarkService {
    
    
    private final BookMarkRepository bookMarkRepository;
    private final GuestRepository guestRepository;
    private final AccommodationRepository accommodationRepository;
    
    // 즐겨찾기 리스트 메서드
    @Override
    public ResponseEntity<? super GetGuestBookMarkResponseDto> getBookMarkList(String guestId) {
        
        List<GetBookMarkResultSet> bookMarkResultSets = new ArrayList<>();

        try {
            bookMarkResultSets = bookMarkRepository.findByGuestId(guestId);
            if (bookMarkResultSets == null) return ResponseDto.noExistUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetGuestBookMarkResponseDto.success(bookMarkResultSets);
    }

    @Override
    public ResponseEntity<ResponseDto> addBookMark(AddBookMarkRequestDto dto, String guestId) {
        
        String id = null;
        BookmarkEntity bookmarkEntity = null;

        try {
            
            // 매개변수로 받아온 Id 유효성 검사
            boolean isExistedId = guestRepository.existsByGuestId(guestId);
            if (!isExistedId) return ResponseDto.noExistGuest();
            
            // 매개변수로 받은 Id와 requestBody의 아이디값도 일치해야함
            id = dto.getGuestId();
            boolean existsByGuestId = id.equals(guestId);
            if(!existsByGuestId) return ResponseDto.notMatchValue();

            String accommodation = dto.getAccommodationName();
            boolean isExisted = accommodationRepository.existsByAccommodationName(accommodation);
            if (!isExisted) return ResponseDto.noExistAccommodation();

            // 여기서 궁금한 점
            /*
            두 문자열 객체를 비교하는 것이기에 일치할 수 가 없구나... 인정 이해함
                id = dto.getGuestId();
                if(id != guestId) return ResponseDto.notMatchValue();
                하면 왜 매개변수로 받은 guestId 와 AddBookMarkRequestDto dto안의 값이 일치해도 일치하지 않는다고 뜰까?
             */

            bookmarkEntity = new BookmarkEntity(dto);
            bookMarkRepository.save(bookmarkEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBookMark(String guestId, String accommodationName) {
        
        try {

        
            // // 북마크 유효성 검사
            // BookmarkEntity bookmarkEntity = bookMarkRepository.findByBookmarkId(bookmarkId);
            // if (bookmarkEntity == null ) return ResponseDto.noExistBookMark();

            // // 아이디 유효성 검사
            // String guest = bookmarkEntity.getGuestId();
            // boolean isGuest = guest.equals(guestId);
            // if (!isGuest) return ResponseDto.noPermission();

            boolean isGuest = guestRepository.existsByGuestId(guestId);
            if (!isGuest) return ResponseDto.noExistGuest();
            
            boolean isAccommodation = accommodationRepository.existsByAccommodationName(accommodationName);
            if (!isAccommodation) return ResponseDto.noExistAccommodation();

            BookmarkEntity bookmarkEntity = bookMarkRepository.findByGuestIdAndAccommodationName(guestId, accommodationName);
            if (bookmarkEntity == null) return ResponseDto.noExistBookMark();


            bookMarkRepository.delete(bookmarkEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }
    
}
