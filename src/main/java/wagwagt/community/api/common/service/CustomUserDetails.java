package wagwagt.community.api.common.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wagwagt.community.api.domain.user.entities.User;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!user.getAuth().isEmpty()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuth().get(0).getAuthority());
            return Collections.singletonList(authority);
        } else {
            // 권한이 없는 경우 적절한 처리를 하세요. 예를 들어, 비어 있는 리스트 반환
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
