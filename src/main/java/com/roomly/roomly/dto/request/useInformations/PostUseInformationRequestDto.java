package com.roomly.roomly.dto.request.useInformations;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUseInformationRequestDto {
    
    private String accommodationName;
    private String title;
    private String context;

}
