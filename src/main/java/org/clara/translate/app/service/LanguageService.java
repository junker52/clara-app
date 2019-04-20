package org.clara.translate.app.service;

import org.clara.translate.app.domain.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getAllOtherLanguages(Long id);

    List<Language> getAllLanguages();

    Language findLanguageById(Long originLanguageId);
}
