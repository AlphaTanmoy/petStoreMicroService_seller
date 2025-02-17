package com.store.seller.service;


import com.store.seller.config.KeywordsAndConstants;
import com.store.seller.error.BadRequestException;
import com.store.seller.request.RabbitMqRequestForOtpDeliver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EmailService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) throws BadRequestException {
        try {
            RabbitMqRequestForOtpDeliver emailMessage = new RabbitMqRequestForOtpDeliver();
            emailMessage.setEmail(userEmail);
            emailMessage.setOtp(otp);
            emailMessage.setSubject(subject);
            emailMessage.setMessage(text);

            rabbitTemplate.convertAndSend(
                    KeywordsAndConstants.RABBIT_MQ_EXCHANGE,
                    KeywordsAndConstants.RABBIT_MQ_ROUTE_KEY_FOR_LOGIN_OR_SIGNUP_OTP,
                    emailMessage
            );
        } catch (Exception e) {
            throw new BadRequestException("Failed to send email through RabbitMQ");
        }
    }

}
