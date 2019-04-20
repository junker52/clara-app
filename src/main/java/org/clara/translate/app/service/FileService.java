package org.clara.translate.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Boolean isFileValid(MultipartFile file);
}
