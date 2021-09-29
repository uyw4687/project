package spring.boot.project.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.boot.project.domain.BaseTime;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column
    private String profileImage;

    @Builder
    public User(String email, String name, String profileImage, Role role) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.role = role;
    }

    public User update(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
        return this;
    }

}
