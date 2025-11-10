package org.example.todolist.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("auth")
public class AuthService {
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername(){
        return this.getAuthentication().getName();
    }

    public List<String> getRoles(){
        return this.getAuthentication().getAuthorities().stream()
                .map(au -> au.getAuthority().substring(5)).toList();
    }

    public boolean isAuthenticated(){
        String username = this.getUsername();
        return (username != null && !username.equals("anonymousUser"));
    }
}
