package org.clara.translate.app.controller;

import lombok.AllArgsConstructor;
import org.clara.translate.app.domain.Language;
import org.clara.translate.app.service.LanguageService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/languages")
@AllArgsConstructor
public class LanguageController {

    private LanguageService languageService;

    @GetMapping
    public List<Language> getAllLanguages() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return this.languageService.getAllLanguages(currentLocale);
    }

    @GetMapping("/other/{id}")
    public List<Language> getAllOtherLanguages(@PathVariable Long id) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return this.languageService.getAllOtherLanguages(id, currentLocale);
    }

}
