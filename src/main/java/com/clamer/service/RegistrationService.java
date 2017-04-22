package com.clamer.service;

import com.clamer.domain.entity.User;
import com.clamer.domain.entity.VerificationToken;
import com.clamer.utility.exception.GeneralException;
import com.clamer.utility.registration.EmailVerificationRequest;
import com.clamer.utility.registration.RegistrationRequest;

/**
 * Created by sungman.you on 2017. 4. 21..
 */
public interface RegistrationService {

    User registerNewUser(RegistrationRequest registrationRequest) throws GeneralException;
    User verifyEmailAddress(EmailVerificationRequest emailVerificationRequest) throws GeneralException;
}
