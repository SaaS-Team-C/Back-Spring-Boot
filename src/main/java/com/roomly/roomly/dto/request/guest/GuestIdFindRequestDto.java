package com.roomly.roomly.dto.request.guest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuestIdFindRequestDto {

    private String guestName;
    private String guestTelNumber;

}
