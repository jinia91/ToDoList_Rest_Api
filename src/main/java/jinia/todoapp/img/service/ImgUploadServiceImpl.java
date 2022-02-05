package jinia.todoapp.img.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImgUploadServiceImpl implements ImgUploadService {

    private final FileUploadStrategy fileUploadStrategy;

    @Override
    public List<String> storeFile(List<MultipartFile> imgList) {
        List<String> fileUrlList = new ArrayList<>();

        for(var file : imgList){
            String storeFileName = createStoreFileName(file.getOriginalFilename());
            String uploadImgFileUrl = fileUploadStrategy.uploadFile(file, storeFileName);
            fileUrlList.add(uploadImgFileUrl);
        }

        return  fileUrlList;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
