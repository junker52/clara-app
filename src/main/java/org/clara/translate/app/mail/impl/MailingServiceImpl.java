package org.clara.translate.app.mail.impl;

import org.clara.translate.app.constants.MailTypes;
import org.clara.translate.app.domain.Budget;
import org.clara.translate.app.domain.Mail;
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

    private void saveSentBudgetMail(Budget budget) {
        Mail budgetMail = Mail.builder().budget(budget).type(MailTypes.BUDGET_MAIL).build();
        this.mailRepository.save(budgetMail);
    }

    private SimpleMailMessage prepareSimpleMessage(String budgetText, String budgetSubject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(this.mailTo);
        message.setSubject(budgetSubject);
        message.setText(budgetText);
        return message;
    }

    private String buildBudgetMailText(Budget budget) {
        return String.format("S'ha calculat un nou presupost. Del %s al %s amb un preu per paraula de %.2f. " +
                    "\nNúmero de paraules: %d - Preu aproximat: %.2f", budget.getCombination().getOriginLanguage().getCode(),
                    budget.getCombination().getTargetLanguage().getCode(),
                    budget.getCombination().getPricePerWord().doubleValue(),
                    budget.getWords(),
                    budget.getEstimatedPrice().doubleValue());
    }


}
