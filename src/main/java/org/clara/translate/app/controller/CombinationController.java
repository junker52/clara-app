package org.clara.translate.app.controller;

import lombok.AllArgsConstructor;
import org.clara.translate.app.domain.Combination;
import org.clara.translate.app.service.CombinationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/combinations")
@AllArgsConstructor
public class CombinationController {

    private CombinationService combinationService;

    @GetMapping("/{originLanguageId}/{targetLanguageId}")
    public Combination getCombination(@PathVariable Long originLanguageId, @PathVariable Long targetLanguageId) {
        return this.combinationService.getCombinationFromOriginAndTargetLanguages(originLanguageId, targetLanguageId);
    }
    
    @PutMapping("/{originLanguageId}/{targetLanguageId}")
    public Combination updateCombinationPrice(@PathVariable Long originLanguageId, @PathVariable Long targetLanguageId, @RequestParam Double price) {
    	return this.combinationService.updateCombinationPrice(originLanguageId, targetLanguageId, price);
    }
}