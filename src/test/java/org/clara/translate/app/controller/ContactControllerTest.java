package org.clara.translate.app.controller;

import org.clara.translate.app.dto.ContactMessageDTO;
import org.clara.translate.app.mail.MailingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MailingService mailingService;

    @Test
    public void sendContactMessage() throws Exception {

        ContactMessageDTO contactMessageDTO = new ContactMessageDTO();
        contactMessageDTO.setEmail("test@test.es");
        contactMessageDTO.setName("Mr Test");

        this.mvc.perform(post("/contactme")
                .content("{name=\"Test\", email=\"r.molina91@gmail.com\", message=\"abc abc\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.mailingService, times(1)).sendContactMail(any());
    }
}