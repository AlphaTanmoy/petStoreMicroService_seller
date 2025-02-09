package com.store.seller.preHitter;

import com.store.seller.enums.MICROSERVICE;
import com.store.seller.enums.RESPONSE_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreHitterResponse {
    RESPONSE_TYPE responseType;
    MICROSERVICE microservice;
}
