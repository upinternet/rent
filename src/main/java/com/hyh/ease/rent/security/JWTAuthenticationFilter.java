package com.hyh.ease.rent.security;

import com.hyh.ease.rent.security.domain.AccessKey;
import com.hyh.ease.rent.security.util.Signature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {

    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    private CustomUserService customUserService = new CustomUserService();

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

        auth1(req);

        filterChain.doFilter(request,response);
    }

    private void auth1(HttpServletRequest req)
    {
        String keyId = req.getHeader("keyId");
        if(StringUtils.isEmpty(keyId))
        {
            return;
        }
        Signature.verify(new AccessKey(keyId,AccessKey.pare.get(keyId)),req);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(null,null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        //设置为已登录
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

       /* UserDetails userDetails = customUserService.loadUserByUsername("s");
        if(userDetails != null)
        {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            //设置为已登录
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }*/

    }

}
