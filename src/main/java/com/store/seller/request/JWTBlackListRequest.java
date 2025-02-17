package com.store.seller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTBlackListRequest {
    private String comment;
    private String actionTakenForId;
}
