package com.roomly.roomly.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomly.roomly.common.object.Room;
import com.roomly.roomly.dto.request.accommodation.PatchAccommodationRequestDto;
import com.roomly.roomly.dto.request.accommodation.PostAccommodationReqeustDto;
import com.roomly.roomly.dto.request.subImages.PatchAccommodationImageRequsetDto;
import com.roomly.roomly.dto.request.subImages.PostAccommodationImageRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationImagesResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationListResponseDto;
import com.roomly.roomly.dto.response.accommodation.GetAccommodationResponseDto;
import com.roomly.roomly.entity.AccImageEntity;
import com.roomly.roomly.entity.AccommodationEntity;
import com.roomly.roomly.entity.HostEntity;
import com.roomly.roomly.entity.RoomImageEntity;
import com.roomly.roomly.entity.UseInformationEntity;
import com.roomly.roomly.repository.AccImageRepository;
import com.roomly.roomly.repository.AccommodationRepository;
import com.roomly.roomly.repository.HostRepository;
import com.roomly.roomly.repository.RoomImageRepository;
import com.roomly.roomly.repository.RoomRepository;
import com.roomly.roomly.repository.UseInformationRepository;
import com.roomly.roomly.repository.resultSet.GetAccommodationListResultSet;
import com.roomly.roomly.repository.resultSet.GetRoomResultSet;
import com.roomly.roomly.service.AccommodationService;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImplement implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final HostRepository hostRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    private final UseInformationRepository useInformationRepository;
    private final AccImageRepository accImageRepository;
    
    // 숙소 등록 메서드
    @Override
    public ResponseEntity<ResponseDto> postAccommodation(PostAccommodationReqeustDto dto) {
        try {
            String hostId = dto.getHostId();
            HostEntity hostEntity = hostRepository.findByHostId(hostId);
            boolean entry = hostEntity.getEntryStatus();
            if (!entry) return ResponseDto.noPermission();
        
            String accommodationName = dto.getAccommodationName();
            boolean isExisted = accommodationRepository.existsByAccommodationName(accommodationName);
            if (isExisted) return ResponseDto.duplicatedAccommodationName();

            AccommodationEntity accommodationEntity = new AccommodationEntity(dto);
            LocalDateTime now = LocalDateTime.now();
            accommodationEntity.setEntryTime(now);
            accommodationRepository.save(accommodationEntity);

            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    // 숙소 서브 이미지 등록 메서드
    @Override
    public ResponseEntity<ResponseDto> postAccommodationImage(PostAccommodationImageRequestDto dto) {
        
            AccommodationEntity accommodationEntity = null;
        try {
            
            String accommodationName = dto.getAccommodationName();
            accommodationEntity = accommodationRepository.findByAccommodationName(accommodationName);
            if (accommodationEntity == null) return ResponseDto.noExistAccommodation();
            
            String imageUrl = dto.getAccommodationImage();
            AccImageEntity accImageEntity = accImageRepository.findByAccommodationNameAndAccommodationImage(accommodationName,imageUrl);
            if (accImageEntity != null) return ResponseDto.duplicatedImage();

            accImageEntity = new AccImageEntity(dto);
            accImageRepository.save(accImageEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    } 

    // 숙소 정보(숙소, 숙소 서브이미지, 숙소 이용정보, 객실, 객실서브 이미지) 가져오기 메서드
    @Override
    public ResponseEntity<? super GetAccommodationResponseDto> getAccommodation(String accommodationName) {

        List<GetRoomResultSet> resultSets = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        List<UseInformationEntity> useInformationEntities = new ArrayList<>();
        List<AccImageEntity> accImageEntities = new ArrayList<>();
        
        AccommodationEntity accommodationEntity;
        
        try {
            
            accommodationEntity = accommodationRepository.findByAccommodationName(accommodationName);
            if (accommodationEntity == null) return ResponseDto.noExistAccommodation();
            
            boolean isMatcedRoomId = roomRepository.existsByAccommodationName(accommodationName);
            if (!isMatcedRoomId) return ResponseDto.noExistRoom();

            accImageEntities = accImageRepository.findByAccommodationName(accommodationName);

            resultSets = roomRepository.getRoomList(accommodationName);
            if (resultSets == null) return ResponseDto.noExistRoom();
            
            for (GetRoomResultSet resultSet: resultSets) {
                List<RoomImageEntity> roomImageEntities = roomImageRepository.findByRoomId(resultSet.getRoomId());
                Room room = new Room(resultSet, roomImageEntities);
                roomList.add(room);
            }
            useInformationEntities = useInformationRepository.findByAccommodationName(accommodationName);
        
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetAccommodationResponseDto.success(accommodationEntity, accommodationName, roomList, useInformationEntities,accImageEntities);
    }

    // 숙소 정보 수정 메서드
    @Override
    public ResponseEntity<ResponseDto> patchAccommodation(PatchAccommodationRequestDto dto, String accommodationName, String hostId) {
        
        try {
            AccommodationEntity accommodationEntity =  accommodationRepository.findByAccommodationName(accommodationName);
            if (accommodationEntity == null) return ResponseDto.noExistAccommodation();

            String changeImage = dto.getAccommodationMainImage();
            boolean isExistedImage = accommodationRepository.existsByAccommodationNameAndAccommodationMainImage(accommodationName, changeImage);
            if (isExistedImage) return ResponseDto.duplicatedImage();

            String changeIntroduce = dto.getAccommodationIntroduce();
            boolean isExsistedIntroduce = accommodationRepository.existsByAccommodationNameAndAccommodationIntroduce(accommodationName, changeIntroduce);
            if(isExsistedIntroduce) return ResponseDto.duplicatedIntroduce();


            String host = accommodationEntity.getHostId();
            boolean isHost = host.equals(hostId);
            if(!isHost) return ResponseDto.noPermission();


            accommodationEntity.patch(dto);
            accommodationRepository.save(accommodationEntity);    

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    // 숙소 서브 이미지 수정 메서드
    @Override
    public ResponseEntity<ResponseDto> patchAccommodationImage(
        PatchAccommodationImageRequsetDto dto,
            String accommodationName,
            String accommodationImage) {
            
        try {
            
            boolean isExisted = accommodationRepository.existsByAccommodationName(accommodationName);
            if (!isExisted) return ResponseDto.noExistAccommodation();

            String accommodationChangeImage = dto.getAccommodationImage();
            boolean isMatched = accImageRepository.existsByAccommodationImage(accommodationChangeImage);
            if (isMatched) return ResponseDto.duplicatedImage();

            AccImageEntity accImageEntity = accImageRepository.findByAccommodationNameAndAccommodationImage(accommodationName,accommodationImage);
            if (accImageEntity==null) return ResponseDto.noExistImage();

            accImageRepository.patchAccommodationImage(accommodationChangeImage, accommodationImage, accommodationName);
            accImageRepository.save(accImageEntity);
    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    // 숙소 서브 이미지들 조회 메서드
    @Override
    public ResponseEntity<? super GetAccommodationImagesResponseDto> getAccommodationImages(String accommodationName) {

        AccommodationEntity accommodationEntity;
        List<AccImageEntity> accImageEntities = new ArrayList<>();
        try {

            accommodationEntity = accommodationRepository.findByAccommodationName(accommodationName);
            if (accommodationEntity == null) return ResponseDto.noExistAccommodation();

            accImageEntities = accImageRepository.findByAccommodationName(accommodationName);
            if (accImageEntities == null) return ResponseDto.noExistImage();
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetAccommodationImagesResponseDto.success(accommodationEntity, accImageEntities);
    }

    // 숙소 리스트(메인 검색 페이지에서 사용될) 조회 메서드
    @Override
    public ResponseEntity<? super GetAccommodationListResponseDto> getAccommodationList() {
        
        List<GetAccommodationListResultSet> resultSets = new ArrayList<>();

        try {
            resultSets = accommodationRepository.getList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetAccommodationListResponseDto.success(resultSets);

    }

    // 숙소 삭제 메서드
    @Override
    public ResponseEntity<ResponseDto> deleteAccommodation(String accommodationName) {
        try {
            AccommodationEntity accommodationEntity = accommodationRepository.findByAccommodationName(accommodationName);
            if (accommodationEntity == null) return ResponseDto.noExistAccommodation();

            boolean isExistedRoom = roomRepository.existsByAccommodationName(accommodationName);
            if (isExistedRoom) return ResponseDto.deleteFail();


            accommodationRepository.delete(accommodationEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

}
