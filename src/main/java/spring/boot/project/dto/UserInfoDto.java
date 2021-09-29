package spring.boot.project.dto;

import lombok.Getter;
import spring.boot.project.domain.user.User;

import java.io.Serializable;

@Getter
public class UserInfoDto implements Serializable {

    private String email;
    private String name;
    private String profileImage;

    public UserInfoDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
    }

}
