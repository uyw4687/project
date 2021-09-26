package spring.boot.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot.project.domain.posts.Posts;
import spring.boot.project.domain.posts.PostsRepo;
import spring.boot.project.dto.PostDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostsRepo repo;

    @Transactional()
    public Long create(PostDto post) {
        return repo.save(post.toEntity()).getId();
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<PostDto> getAll() {
        return repo.selectAll(Sort.by(Order.desc("id")))
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto get(Long id) {
        Posts post = repo.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "번 게시물을 찾을 수 없습니다."));
        return new PostDto(post);
    }

    @Transactional
    public Long remove(Long id) {
        Posts post = repo.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "번 게시물을 찾을 수 없습니다."));
        repo.delete(post);
        return id;
    }
}
