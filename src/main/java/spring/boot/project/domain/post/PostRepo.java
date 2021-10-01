package spring.boot.project.domain.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {

    @Query( "SELECT p FROM Post p")
    List<Post> selectAll(Sort sort);

}
