package revox.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ashraf on 8/2/15.
 */
@Entity
public class user {
    @Id
    @GeneratedValue
    Long id;
    @Column(length = 30)
    String userName;
    @Column(length = 30)
    String email;
    @Column(length = 60)
    String password;
    String imgPath;
    int role;


    public Set<GrantedAuthority> grantedAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (role == 0) authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public user setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public user setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public user setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public user setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getImgPath() {
        return imgPath;
    }

    public user setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }

    public int getRole() {
        return role;
    }

    public user setRole(int role) {
        this.role = role;
        return this;
    }
}
