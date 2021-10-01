package spring.boot.project.dto;

import lombok.Builder;
import lombok.Getter;
import spring.boot.project.domain.post.Post;

import java.time.LocalDateTime;

@Getter
public class PostDto {

    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime lastModified;

    @Builder
    public PostDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    // reverse
    public PostDto(Post post) {
        id = post.getId();
        author = post.getAuthor();
        title = post.getTitle();
        content = post.getContent();
        lastModified = post.getLastModified();
    }

    public Post toEntity() {
        return Post.builder()
                .author(author)
                .title(title)
                .content(content).build();
    }

}
