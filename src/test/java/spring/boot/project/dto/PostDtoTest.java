package spring.boot.project.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDtoTest {

    @Test
    public void set_and_get() {
        String author = "!author";
        String title = "@title";
        String content = "#content";

        PostDto post = PostDto.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();

        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

}
