package spring.boot.project.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepoTest {

    @Autowired
    PostsRepo repo;

    @AfterEach
    public void clean() {
        repo.deleteAll();
    }

    @Test
    public void savesPost() {
        String title = "title abcd";
        String content = "content 1234";
        String author = "abcd@xyz.com";

        repo.save(Posts.builder()
                .author(author)
                .title(title)
                .content(content).build());

        Posts post = repo.findAll().get(0);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void setAuditedDates() {
        LocalDateTime bef = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0));

        String author = "author!";
        String title = "title@";
        String content = "content#";
        repo.save(Posts.builder()
                .author(author)
                .title(title)
                .content(content).build());

        Posts post = repo.findAll().get(0);

        assertThat(post.getCreated()).isAfter(bef);
        assertThat(post.getLastModified()).isAfter(bef);
    }

}
