package com.hyh.ease.rent.security;

import com.hyh.ease.rent.security.domain.SysRole;
import com.hyh.ease.rent.security.domain.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

public class CustomUserService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = new SysUser();
        user.setId(0l);
        user.setUsername("heyanhui");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        SysRole role = new SysRole();
        role.setId(0l);
        role.setName("ROLE_ADMIN");
        user.setRoles(Arrays.asList(role));
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("s:"+s);
        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
        System.out.println("size:"+user.getRoles().size());
        System.out.println("role:"+user.getRoles().get(0).getName());
        return user;
    }
}
