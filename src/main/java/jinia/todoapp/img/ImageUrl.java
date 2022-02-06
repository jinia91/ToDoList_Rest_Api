package jinia.todoapp.img;

import jinia.todoapp.infra.BasicEntity;
import jinia.todoapp.todo.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ImageUrl extends BasicEntity {

    @Id
    @Column(name = "IMAGE_URL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;
    private String url;

    public ImageUrl(Todo todo, String url) {
        this.todo = todo;
        this.url = url;
    }
}
