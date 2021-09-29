package spring.boot.project.service.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import spring.boot.project.domain.user.Role;
import spring.boot.project.domain.user.User;
import spring.boot.project.domain.user.UserRepo;
import spring.boot.project.dto.UserInfoDto;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepo repo;
    private final HttpSession sess;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attrs = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttrName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        User user = saveOrUpdate(attrs, registrationId);
        repo.save(user);

        if (registrationId.equals("naver")) {
            userNameAttrName = "id";
            attrs = (Map<String,Object>)attrs.get("response");
        }

        sess.setAttribute("user", new UserInfoDto(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())), attrs, userNameAttrName);
    }

    private User saveOrUpdate(Map<String, Object> attrs, String registrationId) {
        String _email = null;
        String _name = null;
        String _profileImage = null;

        if (registrationId.equals("google")) {
            _email = (String)attrs.get("email");
            _name = (String)attrs.get("name");
            _profileImage = (String)attrs.get("profileImage");
        }
        else if (registrationId.equals("naver")) {
            Map<String,Object> inner = (Map<String,Object>)attrs.get("response");
            _email = (String)inner.get("email");
            _name = (String)inner.get("name");
            _profileImage = (String)attrs.get("profile_image");
        }

        final String email = _email;
        final String name = _name;
        final String profileImage = _profileImage;

        return repo.findByEmail(email)
                .map((_user) -> _user.update(name, profileImage))
                .orElse(new User(email, name, profileImage, Role.GUEST));
    }

}
