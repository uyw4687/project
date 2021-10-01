package spring.boot.project.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.boot.project.domain.BaseTime;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Post(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

