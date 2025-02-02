package com.store.seller.response;

import com.store.seller.enums.DATA_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTBlackListResponse {
    private String actionTakenOnUser;
    private DATA_STATUS dataStatus;
    private String comment;
}
