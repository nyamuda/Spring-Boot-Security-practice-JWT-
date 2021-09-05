package com.example.demo.Services;

import java.util.List;

import com.example.demo.Models.MyUser;
import com.example.demo.Models.Role;
import com.example.demo.Repositories.MyUserRepository;
import com.example.demo.Repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpe
 */
@Service
@Transactional
public class UserServiceImpe implements UserService {
    @Autowired
    private MyUserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpe(MyUserRepository userRRepo, RoleRepository roleRRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRRepo;
        this.roleRepo = roleRRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<MyUser> getUsers() {

        return userRepo.findAll();
    }

    @Override
    public MyUser addUser(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public MyUser getUser(String username) {

        return userRepo.findByUsername(username);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepo.save(role);

    }

    @Override
    public void addRoleToUser(String username, String name) {
        MyUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(name);

        user.getRoles().add(role);
    }

}