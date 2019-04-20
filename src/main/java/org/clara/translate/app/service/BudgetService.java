package org.clara.translate.app.service;

import org.clara.translate.app.domain.Budget;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public interface BudgetService {
    Budget createBudget(@NotNull MultipartFile file, Long originLanguageId, Long targetLanguageId);
}
