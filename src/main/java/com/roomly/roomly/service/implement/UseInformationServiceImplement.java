package com.roomly.roomly.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomly.roomly.dto.request.useInformations.PatchUseInformationRequestDto;
import com.roomly.roomly.dto.request.useInformations.PostUseInformationRequestDto;
import com.roomly.roomly.dto.response.ResponseDto;
import com.roomly.roomly.entity.AccommodationEntity;
import com.roomly.roomly.entity.UseInformationEntity;
import com.roomly.roomly.repository.AccommodationRepository;
import com.roomly.roomly.repository.UseInformationRepository;
import com.roomly.roomly.service.UseInfomationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UseInformationServiceImplement implements UseInfomationService {
    
    private final AccommodationRepository accommodationRepository;
    private final UseInformationRepository useInformationRepository;

    // 숙소 이용정보 등록 메서드
    @Override
    public ResponseEntity<ResponseDto> postUseInformation(PostUseInformationRequestDto dto) {

            try {
                String accommodationName = dto.getAccommodationName();
                if (accommodationName == null) return ResponseDto.validationFail(); 

                AccommodationEntity accommodationEntity = accommodationRepository.findByAccommodationName(accommodationName);
                if (accommodationEntity == null) return ResponseDto.noExistAccommodation();
            
                UseInformationEntity useInformationEntity = new UseInformationEntity(dto);
                useInformationRepository.save(useInformationEntity);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.databaseError();
            }
            return ResponseDto.success();

    }

    // 숙소 이용정보 수정 메서드
    @Override
    public ResponseEntity<ResponseDto> patchUseInformation(
        PatchUseInformationRequestDto dto,
        String accommodationName,
        Integer autoKey) {
        
            try {
                UseInformationEntity useInformationEntity = useInformationRepository.findByAccommodationNameAndAutoKey(accommodationName, autoKey);
                if (useInformationEntity == null ) return ResponseDto.noExistAccommodation();

                useInformationEntity.patch(dto);
                useInformationRepository.save(useInformationEntity);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.databaseError();
            }
            return ResponseDto.success();
    }

    // 숙소 이용정보 삭제 메서드
    @Override
    public ResponseEntity<ResponseDto> deleteUseInformation(Integer autoKey) {
        
        
        try {
            UseInformationEntity useInformationEntity = useInformationRepository.findByAutoKey(autoKey);
            if (useInformationEntity == null) return ResponseDto.noExistUseInformation();

            useInformationRepository.delete(useInformationEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    
    
}
