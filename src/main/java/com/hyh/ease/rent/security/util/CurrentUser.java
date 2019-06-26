package com.hyh.ease.rent.security.util;

import com.hyh.ease.rent.security.domain.SysRole;
import com.hyh.ease.rent.security.domain.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class CurrentUser {

    public static SysUser details()
    {
        return (SysUser) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
    }

    public static Long id()
    {
        return details().getId();
    }

    public static String username()
    {
        return details().getUsername();
    }

}
