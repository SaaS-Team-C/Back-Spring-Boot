package com.roomly.roomly.dto.request.guestauth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuestTelAuthCheckRequestDto {
    
    @NotBlank
    private String guestTelNumber;
    @NotBlank
    private String guestAuthNumber;
}
