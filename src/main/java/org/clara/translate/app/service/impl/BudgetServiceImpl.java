package org.clara.translate.app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.clara.translate.app.domain.Budget;
import org.clara.translate.app.domain.Combination;
import org.clara.translate.app.repository.BudgetRepository;
import org.clara.translate.app.service.BudgetService;
import org.clara.translate.app.service.CombinationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private BudgetRepository budgetRepository;

    private CombinationService combinationService;

    @Override
    public Budget createBudget(@NotNull MultipartFile file, Long originLanguageId, Long targetLanguageId) {
        Combination combination = this.combinationService.getCombinationFromOriginAndTargetLanguages(originLanguageId, targetLanguageId);
        Integer words = this.countFileWords(file);
        Budget budget = Budget.builder()
                .combination(combination)
                .originalFileName(file.getOriginalFilename())
                .words(words)
                .estimatedPrice(this.calculateEstimatePrice(words, combination))
                .readableSize(FileUtils.byteCountToDisplaySize(file.getSize()))
                .build();
        budget = this.budgetRepository.save(budget);
        return budget;
    }

    private Integer countFileWords(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Integer wordsCount;
        switch (extension) {
            case "doc":
                wordsCount = this.countOnDocFile(file);
                break;
            case "docx":
                wordsCount = this.countOnDocxFile(file);
                break;
            default:
                throw new UnsupportedOperationException("Extension not supported");
        }
        return wordsCount;
    }

    private Integer countOnDocxFile(MultipartFile file) {
        Integer words = null;
        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            words = document.getProperties().getExtendedProperties().getWords();
        } catch (IOException e) {
            log.error("Error counting words on docx extension file");
            e.printStackTrace();
        }
        return words;
    }

    private Integer countOnDocFile(MultipartFile file) {
        Integer words = null;
        try {
            HWPFDocument document = new HWPFDocument(file.getInputStream());
            words = document.getDocProperties().getCWords();
        } catch (IOException e) {
            log.error("Error counting words on doc extension file");
            e.printStackTrace();
        }
        return words;
    }

    private BigDecimal calculateEstimatePrice(Integer words, Combination combination) {
        BigDecimal pricePerWord = combination.getPricePerWord();
        return pricePerWord.multiply(new BigDecimal(words));
    }


}
