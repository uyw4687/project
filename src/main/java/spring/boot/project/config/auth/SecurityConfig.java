package spring.boot.project.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import spring.boot.project.domain.user.Role;
import spring.boot.project.service.auth.security.CustomOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oauth2Svc;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests().antMatchers("/", "/js/**", "/h2-console/**").permitAll()
                                    .antMatchers("/api/**").hasRole(Role.USER.name())
                                    .anyRequest().authenticated().and()
                .logout().logoutSuccessUrl("/").and()
                .oauth2Login().userInfoEndpoint().userService(oauth2Svc);

    }
}
