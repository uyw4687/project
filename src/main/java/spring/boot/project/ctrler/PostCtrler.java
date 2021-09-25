package spring.boot.project.ctrler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.boot.project.dto.PostDto;
import spring.boot.project.service.PostService;

@RestController
public class PostCtrler {

    @Autowired
    PostService postSvc;

    @PostMapping("/posts")
    public Long create(@RequestBody PostDto post) {
        return postSvc.create(post);
    }

    @GetMapping("/posts/{id}")
    public PostDto show(@PathVariable("id") Long id) {
        return postSvc.see(id);
    }

    @PutMapping("/posts/{id}")
    public Long modify(@RequestBody PostDto post, @PathVariable("id") Long id) {
        return postSvc.modify(post, id);
    }

}
