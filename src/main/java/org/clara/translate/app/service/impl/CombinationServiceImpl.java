package org.clara.translate.app.service.impl;

import lombok.AllArgsConstructor;
import org.clara.translate.app.domain.Combination;
import org.clara.translate.app.domain.Language;
import org.clara.translate.app.repository.CombinationRepository;
import org.clara.translate.app.service.CombinationService;
import org.clara.translate.app.service.LanguageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CombinationServiceImpl implements CombinationService {

    private CombinationRepository combinationRepository;

    private LanguageService languageService;

    @Override
    public void createNewCombination(Language origin, Language to, BigDecimal pricePerWord) {
        if (!this.combinationRepository.findByOriginLanguageAndTargetLanguage(origin, to).isPresent()) {
            Combination combination = Combination.builder().originLanguage(origin).targetLanguage(to).pricePerWord(pricePerWord).build();
            this.combinationRepository.save(combination);
        }
    }

    @Override
    public Combination getCombinationFromOriginAndTargetLanguages(Long originLanguageId, Long targetLanguageId) {
        Language originLanguage = this.languageService.findLanguageById(originLanguageId);
        Language targetLanguage = this.languageService.findLanguageById(targetLanguageId);
        return this.combinationRepository.findByOriginLanguageAndTargetLanguage(originLanguage, targetLanguage).orElseThrow(() -> new RuntimeException("Combination not found"));
    }

	@Override
	@Transactional
	public Combination updateCombinationPrice(Long originLanguageId, Long targetLanguageId, Double price) {
		Combination combination = this.getCombinationFromOriginAndTargetLanguages(originLanguageId, targetLanguageId);
		combination.setPricePerWord(new BigDecimal(price));
		return this.combinationRepository.save(combination);
	}

}
