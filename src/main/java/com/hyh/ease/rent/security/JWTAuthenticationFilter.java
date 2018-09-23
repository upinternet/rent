package com.hyh.ease.rent.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;

        String token = req.getHeader(HEADER_STRING);
//        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTM2MjM1NTU2Mzk4LCJzdWJqZWN0Ijoie1wiYWNjb3VudE5vbkV4cGlyZWRcIjp0cnVlLFwiYWNjb3VudE5vbkxvY2tlZFwiOnRydWUsXCJhdXRob3JpdGllc1wiOlt7XCJhdXRob3JpdHlcIjpcIlJPTEVfQURNSU5cIn1dLFwiY3JlZGVudGlhbHNOb25FeHBpcmVkXCI6dHJ1ZSxcImVuYWJsZWRcIjp0cnVlLFwiaWRcIjowLFwicGFzc3dvcmRcIjpcIiQyYSQxMCRvVS50LlBGekVmYWFBRjVIcXZzbFMubHFPTXJlT0dhQXVJLk5LeFZ2YXJiOTEyOElJMHg0LlwiLFwicm9sZXNcIjpbe1wiaWRcIjowLFwibmFtZVwiOlwiUk9MRV9BRE1JTlwifV0sXCJ1c2VybmFtZVwiOlwiaGV5YW5odWlcIn0iLCJleHAiOjE1MzcwOTk1NTZ9.vp8tlobUEbRq4qLSYcF4lKyyihe9ag8BWicjr8kD9EudFGv54Wjb1p8-jOHL0enqKGGXkUe5QrxQRbFM2XvAfw";
        if(StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX)) {
            TokenAuthenticationHandler tokenAuthenticationHandler = new TokenAuthenticationHandler();
            String subject = tokenAuthenticationHandler.getSubjectFromToken(token.replace(TOKEN_PREFIX, ""));
            if(StringUtils.isNotBlank(subject)) {
                SecurityContextHolder.getContext().setAuthentication(new JWTAuthenticationToken(subject));
            }
        }
        filterChain.doFilter(request,response);
    }

}
