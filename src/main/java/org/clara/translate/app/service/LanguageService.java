package org.clara.translate.app.service;

import org.clara.translate.app.domain.Language;

import java.util.List;
import java.util.Locale;

public interface LanguageService {
    List<Language> getAllOtherLanguages(Long id, Locale locale);

    List<Language> getAllLanguages(Locale locale);

    Language findLanguageById(Long originLanguageId);
}
