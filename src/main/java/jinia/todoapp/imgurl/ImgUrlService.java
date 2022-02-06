package jinia.todoapp.imgurl;

import jinia.todoapp.todo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * todo에 포함될 img url 저장과 갱신, 삭제를 위한 비지니스 로직
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ImgUrlService {

    private final ImgUrlRepository imgUrlRepository;

    public void saveUrlList(List<String> imgList, Todo todo){
        for(var img : imgList){
            todo.addImgUrl(new ImageUrl(todo, img));
        }
    }

    public void updateUrlList(Todo todo, List<String> urlList) {
        if(urlList == null) {
            deleteList(todo, todo.getUrlList());
            return;
        }

        List<ImageUrl> originList = todo.getUrlList();
        List<ImageUrl> deleteList = originList
                .stream()
                .filter(imageUrl -> !urlList.contains(imageUrl.getUrl()))
                .collect(Collectors.toList());
        deleteList(todo, deleteList);

        List<String> originStringList =
                originList.stream()
                        .map(ImageUrl::getUrl)
                        .collect(Collectors.toList());
        for(var url : urlList){
            if(!originStringList.contains(url)){
                todo.addImgUrl(new ImageUrl(todo, url));
            }
        }
    }

    public void deleteList(Todo todo, List<ImageUrl> deleteList) {
        imgUrlRepository.deleteAll(deleteList);
        deleteList.forEach(todo::deleteImgUrl);
    }
}
