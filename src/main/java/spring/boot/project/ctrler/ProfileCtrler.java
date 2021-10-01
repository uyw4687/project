package spring.boot.project.ctrler;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileCtrler {

    public final Environment env;

    @GetMapping("/profile")
    public String return_profile() {
        for (String s : env.getActiveProfiles()) {
            if (s.startsWith("real")) {
                return s;
            }
        }
        return "dev";
    }

}
