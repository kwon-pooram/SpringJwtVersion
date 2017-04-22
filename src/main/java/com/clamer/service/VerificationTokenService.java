package com.clamer.service;

import com.clamer.domain.entity.User;
import com.clamer.domain.entity.VerificationToken;
import com.clamer.utility.registration.ResendVerificationEmailRequest;

/**
 * Created by sungman.you on 2017. 4. 8..
 */
public interface VerificationTokenService {

    void deleteToken(String token);
    void createToken(User user, String token);
    VerificationToken updateToken(ResendVerificationEmailRequest resendVerificationEmailRequest);
    VerificationToken getToken(String token);
}
