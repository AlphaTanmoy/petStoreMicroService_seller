package com.store.seller.response;

import com.store.seller.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeGrabber {
    private String otp;
    private String email;
    private User user;
    private LocalDateTime expiryDate;
}
