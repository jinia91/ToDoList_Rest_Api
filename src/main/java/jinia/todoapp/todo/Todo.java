package jinia.todoapp.todo;

import lombok.*;
import jinia.todoapp.infra.BasicEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Todo extends BasicEntity {

    @Id
    @Column(name = "TODO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean completed;
    private LocalDateTime complete_at;

    @Builder
    public Todo(String name, Boolean completed) {
        this.name = name;
        this.completed = completed;
        setComplete_at();
    }

    public void setComplete_at(){
        if(this.getCompleted()!=null && this.getCompleted()) {
            this.complete_at = LocalDateTime.now();
        }
        else{
            this.complete_at = null;
        }
    }

    public void update(String name, Boolean completed) {
    this.name = name;
    this.completed =completed;
    setComplete_at();
    }
}
