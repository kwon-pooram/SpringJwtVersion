package com.clamer.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by sungman.you on 2017. 4. 8..
 */
public interface MailService {

    void sendEmail(SimpleMailMessage email);
}
