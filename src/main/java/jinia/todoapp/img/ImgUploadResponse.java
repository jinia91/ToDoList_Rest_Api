package jinia.todoapp.img;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ImgUploadResponse {
    private List<String> urlList;

    public ImgUploadResponse(List<String> imgUrlList) {
        urlList = imgUrlList;
    }
}

