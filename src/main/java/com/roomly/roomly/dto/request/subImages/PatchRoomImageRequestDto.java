package com.roomly.roomly.dto.request.subImages;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchRoomImageRequestDto {
    
    @NotBlank
    private String roomImage;
}