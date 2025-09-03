package com.bivolaris.userservice.dtos;



import com.bivolaris.userservice.entities.AddressTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserAddressDto {

    private UUID id;
    private UUID userId;
    private String userFirstName;
    private String userLastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private AddressTypeEnum addressType;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime updatedAt;
    private Boolean isDefault;

}
