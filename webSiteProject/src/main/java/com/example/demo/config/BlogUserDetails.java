package com.example.demo.config;


import com.example.demo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

public class BlogUserDetails extends User implements UserDetails {

    private User user;
    private ArrayList<String> roles;

    public BlogUserDetails(User user, ArrayList<String> roles) {
        //calls the parent constructor
        super(user.getEmail(), user.getName(), user.getPassword());
        this.user = user;
        this.roles = roles;
    }
    
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRoles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
    }

    public User getUser() {
        return user;
    }
}
