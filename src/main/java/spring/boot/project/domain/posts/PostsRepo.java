package spring.boot.project.domain.posts;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepo extends JpaRepository<Posts,Long> {

    @Query( "SELECT p FROM Posts p")
    List<Posts> selectAll(Sort sort);

}
