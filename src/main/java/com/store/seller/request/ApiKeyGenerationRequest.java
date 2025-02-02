package com.store.seller.request;

import com.store.seller.enums.DATE_RANGE_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyGenerationRequest {
    private String id;
    private DATE_RANGE_TYPE expiryDate;
}
