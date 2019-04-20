package org.clara.translate.app.service;

import org.clara.translate.app.domain.Combination;
import org.clara.translate.app.domain.Language;

import java.math.BigDecimal;

public interface CombinationService {
    void createNewCombination(Language origin, Language to, BigDecimal pricePerWord);

    Combination getCombinationFromOriginAndTargetLanguages(Long originLanguageId, Long targetLanguageId);
}
