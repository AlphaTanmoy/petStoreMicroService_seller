package com.store.seller.response;

import com.store.seller.enums.TIRE_CODE;
import com.store.seller.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProfile {

    private String name;
    private String apiKey;
    private USER_ROLE userRole;
    private TIRE_CODE tireCode;
}
