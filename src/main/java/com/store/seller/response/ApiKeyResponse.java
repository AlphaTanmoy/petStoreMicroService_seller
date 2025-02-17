package com.store.seller.response;

import com.store.seller.enums.CREATION_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyResponse {
    private String createdBy;
    private String createdFor;
    private String apiKey;
    private CREATION_STATUS createStatus;
    private String expiryTime;
}
