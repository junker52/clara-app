package org.clara.translate.app.mail.impl;

import org.clara.translate.app.constants.MailTypes;
import org.clara.translate.app.domain.Budget;
import org.clara.translate.app.domain.Mail;
import org.clara.translate.app.dto.ContactMessageDTO;
import org.clara.translate.app.mail.MailingService;
import org.clara.translate.app.repository.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailingServiceImpl implements MailingService {

    private JavaMailSender mailSender;

    private MailRepository mailRepository;

    @Value("${user.mail}")
    private String mailTo;

    public MailingServiceImpl(JavaMailSender mailSender, MailRepository mailRepository) {
        this.mailSender = mailSender;
        this.mailRepository = mailRepository;
    }

    @Async
    @Override
    public void sendBudgetMail(Budget budget){
        String budgetText = this.buildBudgetMailText(budget);
        String budgetSubject = "[APP] Pressupost Calculat";
        SimpleMailMessage message = this.prepareSimpleMessage(budgetText, budgetSubject);
        this.mailSender.send(message);
        this.saveSentBudgetMail(budget);
    }

    @Async
    @Override
    public void sendContactMail(ContactMessageDTO messageDTO){
        String contactText = String.format("S'ha registrat el següent contacte: \n\nNom: %s\nEmail: %s\nMissatge: %s"
                , messageDTO.getName()
                , messageDTO.getEmail()
                , messageDTO.getMessage());
        String contactSubject = "[APP] Missatge de Contacte";
        SimpleMailMessage message = this.prepareSimpleMessage(contactText, contactSubject);
        this.mailSender.send(message);
        this.saveSentContactMail(messageDTO);
    }

    private void saveSentContactMail(ContactMessageDTO messageDTO) {
        Mail contactMail = Mail.builder().type(MailTypes.CONTACT_MAIL)
                .mailFrom(messageDTO.getEmail()).message(messageDTO.getMessage()).build();
        this.mailRepository.save(contactMail);
    }

    private void saveSentBudgetMail(Budget budget) {
        Mail budgetMail = Mail.builder().budget(budget).type(MailTypes.BUDGET_MAIL).build();
        this.mailRepository.save(budgetMail);
    }

    private SimpleMailMessage prepareSimpleMessage(String text, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(this.mailTo);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    private String buildBudgetMailText(Budget budget) {
        return String.format("S'ha calculat un nou presupost. Del %s al %s amb un preu per paraula de %.2f. " +
                    "\nNúmero de paraules: %d - Preu aproximat: %.2f euros.", budget.getCombination().getOriginLanguage().getCode(),
                    budget.getCombination().getTargetLanguage().getCode(),
                    budget.getCombination().getPricePerWord().doubleValue(),
                    budget.getWords(),
                    budget.getEstimatedPrice().doubleValue());
    }


}
