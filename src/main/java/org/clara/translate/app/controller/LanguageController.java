package org.clara.translate.app.controller;

import lombok.AllArgsConstructor;
import org.clara.translate.app.domain.Language;
import org.clara.translate.app.service.LanguageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/languages")
@AllArgsConstructor
public class LanguageController {

    private LanguageService languageService;

    @GetMapping
    public List<Language> getAllLanguages() {
        return this.languageService.getAllLanguages();
    }

    @GetMapping("/other/{id}")
    public List<Language> getAllOtherLanguages(@PathVariable Long id) {
        return this.languageService.getAllOtherLanguages(id);
    }

}
