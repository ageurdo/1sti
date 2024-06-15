package com.ageurdo.demo_user_auth_api.jwt;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        User user = userService.getByCpf(cpf);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String cpf) {
        User.Role role = userService.getRoleByCpf(cpf);
        return JwtUtils.createToken(cpf, role.name().substring("ROLE_".length()));
    }
}
