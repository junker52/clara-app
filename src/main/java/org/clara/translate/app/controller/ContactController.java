package org.clara.translate.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.clara.translate.app.dto.ContactMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contactme")
@Slf4j
public class ContactController {

    @PostMapping
    public ResponseEntity<?> sendContactMessage(ContactMessageDTO messageDTO){
        log.debug("Message! "+messageDTO.toString());
        return null;
    }
}
