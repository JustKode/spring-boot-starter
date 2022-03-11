package justkode.starter.domain.user;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Collection;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @Column
    private Integer id;

    @Column(length = 64, unique = true, nullable = false)
    private String userId;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 32, unique = true, nullable = false)
    private String nickname;

    @Column(length = 128, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
