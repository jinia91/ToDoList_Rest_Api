package jinia.todoapp.img;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<ImageUrl, Long> {
}
