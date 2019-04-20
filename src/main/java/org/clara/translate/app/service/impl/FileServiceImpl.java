package org.clara.translate.app.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.clara.translate.app.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final List<String> allowedExtensions = Arrays.asList(new String[]{"doc", "docx"});

    private final Long FIVE_MB_IN_BYTES = 5000000L;

    @Override
    public Boolean isFileValid(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!allowedExtensions.contains(extension)) {
            return Boolean.FALSE;
        }

        Long fileSize = file.getSize();
        if (fileSize > FIVE_MB_IN_BYTES) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


}
