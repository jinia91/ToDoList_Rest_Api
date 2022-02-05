package jinia.todoapp.img.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadStrategy {
    String uploadFile(MultipartFile file, String storeFileName);
}
