package spring.boot.project.ctrler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.boot.project.dto.PostDto;
import spring.boot.project.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RouteCtrler {

    private final PostService svc;

    @GetMapping("/")
    public String main(Model model) {
        List<PostDto> posts = svc.getAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/post-new")
    public String create() {
        return "post-new";
    }

    @GetMapping("/posts/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        PostDto post = svc.get(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/posts/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        PostDto post = svc.get(id);
        model.addAttribute("post", post);
        return "post-edit";
    }

}
