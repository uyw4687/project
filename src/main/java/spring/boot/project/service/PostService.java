package spring.boot.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.project.domain.posts.Posts;
import spring.boot.project.domain.posts.PostsRepo;
import spring.boot.project.dto.PostDto;

import javax.transaction.Transactional;

@Service
public class PostService {

    @Autowired
    PostsRepo repo;

    @Transactional
    public Long create(PostDto post) {
        return repo.save(post.toEntity()).getId();
    }

    public PostDto see(Long id) {
        Posts post = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. id : "+id));
        return new PostDto(post);
    }

    @Transactional
    public Long modify(PostDto modified, Long id) {
        Posts post = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. id : "+id));
        post.update(modified.getTitle(),modified.getContent());
        return id;
    }

}
