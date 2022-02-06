package jinia.todoapp.img.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 전략 패턴 적용한 이미지 업로드 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class ImgUploadServiceImpl implements ImgUploadService {

    private final ImgUploadStrategy imgUploadStrategy;

    @Override
    public List<String> storeFile(List<MultipartFile> imgList) {
        List<String> fileUrlList = new ArrayList<>();

        for(var file : imgList){
            checkImageMimeType(file);
            String storeFileName = createStoreFileName(file.getOriginalFilename());
            String uploadImgFileUrl = imgUploadStrategy.uploadFile(file, storeFileName);
            fileUrlList.add(uploadImgFileUrl);
        }

        return  fileUrlList;
    }

    /**
     * 이미지 파일인지 체크하는 메서드
     * @throws new IllegalArgumentException 이미지 파일아닐경우 에러발생 + 파일 에러도 추가감지
     * @param file
     */
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

    /**
     *  저장을 위한 고유 난수명 생성 메서드
     * @param originalFilename
     * @return New Random File Name
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 확장자 분리 메서드
     * @throws IllegalArgumentException 확장자 없을시 잘못된 파일 이름
     * @param originalFilename
     * @return 확장자를 분리한 순수 파일명
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        if(pos == -1) throw new IllegalArgumentException("잘못된 파일 이름 입니다.");
        return originalFilename.substring(pos + 1);
    }
}
