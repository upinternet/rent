package com.hyh.ease.rent.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter{

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    private AuthenticationSuccessHandler successHandler;

    public JWTLoginFilter() {
    }

    public JWTLoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        super.successfulAuthentication(req,res,chain,auth);
        TokenAuthenticationHandler tokenAuthenticationHandler = new TokenAuthenticationHandler();

        Object obj = auth.getPrincipal();
        if (obj != null) {
            UserDetails userDetails = (UserDetails) obj;
            String token = tokenAuthenticationHandler.generateToken(JSON.toJSONString(userDetails));
            res.setHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        }

        if (successHandler != null) {
            successHandler.onAuthenticationSuccess(req, res, auth);
        }

    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

}