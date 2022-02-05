package jinia.todoapp.img;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jinia.todoapp.img.service.ImgUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImgUploadController {

    final private ImgUploadService imgUploadService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상 업로드"),
            @ApiResponse(code = 400, message = "잘못된 파일 업로드"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/images")
    public ResponseEntity<ImgUploadResponse> imgUpload(@RequestParam("img") List<MultipartFile> imgList){
        List<String> imgUrlList = imgUploadService.storeFile(imgList);
        ImgUploadResponse imgUploadResponse = new ImgUploadResponse(imgUrlList);
        return new ResponseEntity<>(imgUploadResponse, HttpStatus.OK);
    }
}
