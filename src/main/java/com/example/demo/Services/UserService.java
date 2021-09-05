package com.example.demo.Services;

import java.util.List;

import com.example.demo.Models.MyUser;
import com.example.demo.Models.Role;

public interface UserService {
    public List<MyUser> getUsers();

    public MyUser addUser(MyUser user);

    public MyUser getUser(String username);

    public Role addRole(Role role);

    public void addRoleToUser(String username, String name);

}
