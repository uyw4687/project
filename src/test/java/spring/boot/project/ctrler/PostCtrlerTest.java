package spring.boot.project.ctrler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.boot.project.domain.posts.Posts;
import spring.boot.project.domain.posts.PostsRepo;
import spring.boot.project.dto.PostDto;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostCtrlerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTmpl;

    @Autowired
    private PostsRepo repo;

    @AfterEach
    public void clean() {
        repo.deleteAll();
    }

    @Test
    public void create() {
        String author = "author 123";
        String title = "title 456";
        String content = "content 789";

        PostDto postDto = PostDto.builder()
                .author(author)
                .title(title)
                .content(content).build();

        ResponseEntity<Long> resp
                = restTmpl.postForEntity("http://localhost:"+port+"/api/posts", postDto, Long.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isGreaterThan(0L);

        Posts post = repo.findAll().get(0);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void get() {
        String author = "author !@#";
        String title = "title $%^";
        String content = "content &*()";

        Long id = repo.save(new Posts(author, title, content)).getId();

        ResponseEntity<PostDto> resp
                = restTmpl.getForEntity("http://localhost:"+port+"/api/posts/"+id, PostDto.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody().getId()).isEqualTo(id);
        assertThat(resp.getBody().getAuthor()).isEqualTo(author);
        assertThat(resp.getBody().getTitle()).isEqualTo(title);
        assertThat(resp.getBody().getContent()).isEqualTo(content);
    }

    @Test
    public void modify() {
        String author = "author !!!";
        String title = "title @@@";
        String content = "content ###";

        Long id = repo.save(new Posts(author, title, content)).getId();

        String newTitle = "new title @@@";
        String newContent = "new content ###";

        PostDto postDto = PostDto.builder()
                .author(author)
                .title(newTitle)
                .content(newContent).build();

        HttpEntity<PostDto> reqEntity = new HttpEntity<>(postDto);

        ResponseEntity<Long> resp
                = restTmpl.exchange("http://localhost:"+port+"/api/posts/"+id,
                HttpMethod.PUT, reqEntity, Long.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo(id);

        Posts post = repo.findAll().get(0);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }

}
