package org.clara.translate.app.mail.impl;

import lombok.AllArgsConstructor;
import org.clara.translate.app.mail.MailingService;
import org.clara.translate.app.repository.MailRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailingServiceImpl implements MailingService {

    private JavaMailSender mailSender;

    private MailRepository mailRepository;


}
