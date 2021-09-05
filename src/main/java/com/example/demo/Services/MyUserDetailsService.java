package com.example.demo.Services;

import java.util.ArrayList;
import java.util.Collection;

import com.example.demo.Models.MyUser;
import com.example.demo.Repositories.MyUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * MyUserDetailsService
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserRepository userRepo;

    public MyUserDetailsService(MyUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Incorrect credentials");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}