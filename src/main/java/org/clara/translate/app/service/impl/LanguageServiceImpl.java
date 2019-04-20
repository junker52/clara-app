package org.clara.translate.app.service.impl;

import lombok.AllArgsConstructor;
import org.clara.translate.app.domain.Language;
import org.clara.translate.app.repository.LanguageRepository;
import org.clara.translate.app.service.LanguageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;

    @Override
    public List<Language> getAllOtherLanguages(Long id) {
        return this.languageRepository.findAll().stream()
                .filter(lang -> lang.getId() != id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Language> getAllLanguages() {
        return this.languageRepository.findAll();
    }

    @Override
    public Language findLanguageById(Long originLanguageId) {
        return this.languageRepository.findById(originLanguageId).orElseThrow(() -> new RuntimeException(String.format("Language with id %d not found", originLanguageId)));
    }
}
