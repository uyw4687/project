package spring.boot.project.dto;

import lombok.Builder;
import lombok.Getter;
import spring.boot.project.domain.posts.Posts;

@Getter
public class PostDto {

    private Long id;
    private String author;
    private String title;
    private String content;

    @Builder
    public PostDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostDto(Posts post) {
        id = post.getId();
        author = post.getAuthor();
        title = post.getTitle();
        content = post.getContent();
    }

    public Posts toEntity() {
        return Posts.builder()
                .author(author)
                .title(title)
                .content(content).build();
    }

}
