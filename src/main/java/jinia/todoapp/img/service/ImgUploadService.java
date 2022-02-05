package jinia.todoapp.img.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgUploadService {
    public List<String> storeFile(List<MultipartFile> imgList);
}
