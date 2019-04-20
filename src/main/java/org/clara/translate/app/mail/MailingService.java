package org.clara.translate.app.mail;

import org.clara.translate.app.domain.Budget;
import org.springframework.scheduling.annotation.Async;

public interface MailingService {
    @Async
    void sendBudgetMail(Budget budget);
}
