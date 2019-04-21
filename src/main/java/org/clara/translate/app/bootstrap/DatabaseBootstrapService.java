package org.clara.translate.app.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clara.translate.app.domain.Language;
import org.clara.translate.app.repository.LanguageRepository;
import org.clara.translate.app.service.CombinationService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class DatabaseBootstrapService implements ApplicationListener<ContextRefreshedEvent> {

    private LanguageRepository languageRepository;

    private CombinationService combinationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (this.languageRepository.count() > 0) {
            return;
        }
        this.saveLanguagesAndCombinations();
        log.debug("DB: Languages and Combinations have been populated");
    }

    private void saveLanguagesAndCombinations() {
        Language spanish = Language.builder().name("spanish").code("ES").translatingCode("lang_es").build();
        Language french = Language.builder().name("french").code("FR").translatingCode("lang_fr").build();
        Language english = Language.builder().name("english").code("EN").translatingCode("lang_en").build();
        Language catalan = Language.builder().name("catalan").code("CA").translatingCode("lang_ca").build();

        this.languageRepository.saveAll(Arrays.asList(spanish, french, english, catalan));

        this.combinationService.createNewCombination(spanish, french, new BigDecimal(0.25));
        this.combinationService.createNewCombination(spanish, english, new BigDecimal(0.35));
        this.combinationService.createNewCombination(spanish, catalan, new BigDecimal(0.41));

        this.combinationService.createNewCombination(french, catalan, new BigDecimal(0.56));
        this.combinationService.createNewCombination(french, spanish, new BigDecimal(0.76));
        this.combinationService.createNewCombination(french, english, new BigDecimal(0.02));

        this.combinationService.createNewCombination(english, spanish, new BigDecimal(0.51));
        this.combinationService.createNewCombination(english, french, new BigDecimal(0.12));
        this.combinationService.createNewCombination(english, catalan, new BigDecimal(0.21));

        this.combinationService.createNewCombination(catalan, spanish, new BigDecimal(0.81));
        this.combinationService.createNewCombination(catalan, english, new BigDecimal(0.43));
        this.combinationService.createNewCombination(catalan, french, new BigDecimal(0.61));
    }

}
