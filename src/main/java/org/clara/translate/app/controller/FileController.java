package org.clara.translate.app.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clara.translate.app.service.BudgetService;
import org.clara.translate.app.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@Slf4j
public class FileController {

    private FileService fileService;

    private BudgetService budgetService;

    @PostMapping("/{originLangId}/{targetLangId}")
    public ResponseEntity<?> getFile(@RequestParam("file") MultipartFile uploadfile, @PathVariable Long originLangId, @PathVariable Long targetLangId) throws IOException {

        if (!fileService.isFileValid(uploadfile)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.budgetService.createBudget(uploadfile, originLangId, targetLangId));
    }
}
