package org.clara.translate.app.controller;

import lombok.AllArgsConstructor;
import org.clara.translate.app.dto.ContactMessageDTO;
import org.clara.translate.app.mail.MailingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contactme")
@AllArgsConstructor
public class ContactController {

    private MailingService mailingService;

    @PostMapping
    public ResponseEntity<?> sendContactMessage(ContactMessageDTO messageDTO){
        this.mailingService.sendContactMail(messageDTO);
        return ResponseEntity.ok().build();
    }
}
