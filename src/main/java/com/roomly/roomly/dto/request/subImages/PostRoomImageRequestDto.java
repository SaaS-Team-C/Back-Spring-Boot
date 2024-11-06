package com.roomly.roomly.dto.request.subImages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRoomImageRequestDto {
    
    @NotNull
    private Integer roomId;
    
    @NotBlank
    private String roomImage;
}
