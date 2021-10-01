package spring.boot.project.ctrler;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileCtrlerTest {

    @Test
    public void returns_right_profile_when_real_exists() {
        String expected = "real1";

        MockEnvironment env = new MockEnvironment();
        env.setActiveProfiles("profileA", expected);

        ProfileCtrler ctrler = new ProfileCtrler(env);
        assertThat(ctrler.return_profile()).isEqualTo(expected);
    }

    @Test
    public void returns_dev_when_real_doesnt_exist() {
        MockEnvironment env = new MockEnvironment();

        ProfileCtrler ctrler = new ProfileCtrler(env);
        assertThat(ctrler.return_profile()).isEqualTo("dev");
    }

}
