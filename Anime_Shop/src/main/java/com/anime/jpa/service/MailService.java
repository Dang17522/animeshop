package com.anime.jpa.service;

import javax.mail.MessagingException;

import com.anime.model.MailInfo;

public interface MailService {
    void send(MailInfo mailInfo) throws MessagingException;

    void send(String to, String subject, String body) throws MessagingException;
}
