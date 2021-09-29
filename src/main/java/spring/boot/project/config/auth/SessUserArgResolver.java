package spring.boot.project.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import spring.boot.project.dto.UserInfoDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class SessUserArgResolver implements HandlerMethodArgumentResolver {

    private final HttpSession sess;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean annotationUsed = parameter.getParameterAnnotation(SessUser.class)!=null;
        boolean userInfoParam = parameter.getParameterType().equals(UserInfoDto.class);
        return annotationUsed && userInfoParam;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return sess.getAttribute("user");
    }

}
