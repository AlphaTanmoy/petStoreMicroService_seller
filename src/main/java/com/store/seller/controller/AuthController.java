package com.store.seller.controller;

import com.store.seller.config.JwtProvider;
import com.store.seller.config.KeywordsAndConstants;
import com.store.seller.enums.USER_ROLE;
import com.store.seller.error.BadRequestException;
import com.store.seller.model.User;
import com.store.seller.request.LoginRequest;
import com.store.seller.request.RequestEmail;
import com.store.seller.request.SignUpRequest;
import com.store.seller.response.ApiResponse;
import com.store.seller.response.AuthResponse;
import com.store.seller.response.GetProfile;
import com.store.seller.service.ApiKeyService;
import com.store.seller.service.AuthService;
import com.store.seller.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final ApiKeyService apiKeyService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignUpRequest req, HttpServletRequest httpRequest) throws BadRequestException {

        String jwt=authService.createUser(req, httpRequest);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("Register Success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);
        res.setStatus(true);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/otp")
    public ResponseEntity<ApiResponse> sentLoginOtp(
            @RequestBody RequestEmail req
    ) throws Exception {

        authService.sentLoginOtp(req.getEmail());

        ApiResponse res = new ApiResponse();
        res.setStatus(true);
        res.setMessage("We will send an OTP to your email -> " + req.getEmail());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest, HttpServletRequest httpRequest) throws Exception {
        AuthResponse authResponse = authService.signIn(loginRequest, httpRequest);
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/getProfile")
    public GetProfile getProfile(
            @Valid @RequestHeader(value = KeywordsAndConstants.HEADER_AUTH_TOKEN, required = false) String token
    ){
        BadRequestException badRequestException = new BadRequestException();
        if(token.isEmpty()) {
            badRequestException.setErrorMessage("Provide a valid Token");
        }

        String actionTakerId = jwtProvider.getIdFromJwtToken(token);
        Optional<User> findUser = userService.findUserById(actionTakerId);
        GetProfile getProfile = new GetProfile();

        if(findUser.isPresent()){
            getProfile.setName(findUser.get().getFullName());
            String apiKey = apiKeyService.findApiKeyByUserId(findUser.get().getId());
            if(apiKey == null) getProfile.setApiKey("No API Key Present");
            else getProfile.setApiKey(apiKey);
            getProfile.setUserRole(findUser.get().getRole());
            getProfile.setTireCode(findUser.get().getTireCode());
        }
        else{
            badRequestException.setErrorMessage("No User Data Found");
            throw badRequestException;
        }

        return getProfile;
    }
}
