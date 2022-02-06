package jinia.todoapp.img.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 업로드 전략패턴 추상화 인터페이스
 */
public interface ImgUploadStrategy {
    String uploadFile(MultipartFile file, String storeFileName);
}
