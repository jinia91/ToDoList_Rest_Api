package jinia.todoapp.img;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class ImgUploadRequest {
    private List<MultipartFile> imgList;
}

