package spring.boot.project.ctrler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.boot.project.domain.posts.Posts;
import spring.boot.project.domain.posts.PostsRepo;
import spring.boot.project.dto.PostDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PostCtrlerTest {

    @Autowired
    private PostsRepo repo;

    @AfterEach
    public void clean() {
        repo.deleteAll();
    }

    MockMvc mvc;
    @Autowired
    WebApplicationContext webCtxt;

    @BeforeAll
    public void createMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webCtxt).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    public void create() throws Exception {
        String author = "author 123";
        String title = "title 456";
        String content = "content 789";

        PostDto postDto = PostDto.builder()
                .author(author)
                .title(title)
                .content(content).build();

        MvcResult res = mvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andReturn();

        String respCont = res.getResponse().getContentAsString();
        assertThat(Long.valueOf(respCont)).isGreaterThan(0L);

        Posts post = repo.findAll().get(0);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser
    public void getPost() throws Exception {
        String author = "author !@#";
        String title = "title $%^";
        String content = "content &*()";

        Long id = repo.save(new Posts(author, title, content)).getId();

        mvc.perform(get("/api/posts/"+id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @WithMockUser
    public void modify() throws Exception {
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

        mvc.perform(put("/api/posts/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(id)));

        Posts post = repo.findAll().get(0);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }

}
