package com.ageurdo.demo_user_auth_api.jwt;
import com.ageurdo.demo_user_auth_api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User {

    private User userEntity;

    public JwtUserDetails(User user) {
        super(user.getCpf(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.userEntity = user;
    }

    public Long getId(){
        return userEntity.getId();
    }

    public String getRole(){
        return this.userEntity.getRole().name();
    }
}
