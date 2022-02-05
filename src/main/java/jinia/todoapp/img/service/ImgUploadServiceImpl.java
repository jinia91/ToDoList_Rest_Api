package jinia.todoapp.img.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImgUploadServiceImpl implements ImgUploadService {

    private final FileUploadStrategy fileUploadStrategy;

    @Override
    public List<String> storeFile(List<MultipartFile> imgList) {
        List<String> fileUrlList = new ArrayList<>();

        for(var file : imgList){
            checkImageMimeType(file);
            String storeFileName = createStoreFileName(file.getOriginalFilename());
            String uploadImgFileUrl = fileUploadStrategy.uploadFile(file, storeFileName);
            fileUrlList.add(uploadImgFileUrl);
        }

        return  fileUrlList;
    }
    private void checkImageMimeType(MultipartFile file) {
        Tika tika = new Tika();
        String mimeType = null;
        try {
            mimeType = tika.detect(file.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("잘못된 파일입니다.");
        }
        if(!mimeType.startsWith("image")) throw new IllegalArgumentException("지원하는 이미지 확장가가 아닙니다.");
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        if(pos == -1) throw new IllegalArgumentException("잘못된 파일 이름 입니다.");
        return originalFilename.substring(pos + 1);
    }
}
