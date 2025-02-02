package com.store.seller.controller;

import com.store.seller.config.JwtProvider;
import com.store.seller.config.KeywordsAndConstants;
import com.store.seller.error.BadRequestException;
import com.store.seller.request.ApiKeyGenerationRequest;
import com.store.seller.response.ApiKeyResponse;
import com.store.seller.service.ApiKeyService;
import com.store.seller.utils.DateUtil;
import com.store.seller.utils.ValidateForUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiKey")
@RequiredArgsConstructor
public class APIKeyController {

    private final ApiKeyService apiKeyService;
    private final JwtProvider jwtProvider;
    private final DateUtil dateUtil;
    @PostMapping("/createApiKey")
    public ApiKeyResponse createApiKey(
            @Valid @RequestHeader(value = KeywordsAndConstants.HEADER_AUTH_TOKEN, required = false) String token,
            @RequestBody ApiKeyGenerationRequest apiKeyGenerationRequest
    ){
        BadRequestException badRequestException = new BadRequestException();
        String actionTakerId;
        if (token != null) {
            actionTakerId = jwtProvider.getIdFromJwtToken(token);
        } else {
            badRequestException.setErrorMessage("Invalid token");
            throw badRequestException;
        }
        ValidateForUUID.check(actionTakerId, "User");
        ValidateForUUID.check(apiKeyGenerationRequest.getId(), "User");

        return apiKeyService.createApiKey(
                actionTakerId,apiKeyGenerationRequest.getId(),
                apiKeyGenerationRequest.getExpiryDate(),
                DateUtil.checkValid(apiKeyGenerationRequest)
        );
    }

    @PostMapping("/deleteApiKey")
    public ApiKeyResponse deleteApiKey(
            @Valid @RequestHeader(value = KeywordsAndConstants.HEADER_AUTH_TOKEN, required = false) String token,
            @RequestBody String id
    ){
        BadRequestException badRequestException = new BadRequestException();
        String actionTakerId;
        if (token != null) {
            actionTakerId = jwtProvider.getIdFromJwtToken(token);
        } else {
            badRequestException.setErrorMessage("Invalid token");
            throw badRequestException;
        }
        ValidateForUUID.check(actionTakerId, "User");
        ValidateForUUID.check(id, "User");
        return apiKeyService.deleteApiKey(actionTakerId,id);
    }

}
