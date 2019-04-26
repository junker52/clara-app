package org.clara.translate.app.i18n;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Service
@AllArgsConstructor
public class InternazionalizationService {

    private MessageSource messageSource;

    public String getMessage(@NotNull String code) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return this.messageSource.getMessage(code, null, currentLocale);
    }
}
