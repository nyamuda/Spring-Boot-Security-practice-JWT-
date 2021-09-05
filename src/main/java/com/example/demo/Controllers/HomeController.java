package com.example.demo.Controllers;

import java.util.List;

import com.example.demo.Models.MyUser;
import com.example.demo.Models.Role;
import com.example.demo.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String goHome() {
        return ("<h1>hey friend </h1>");
    }

    @GetMapping("/users")
    public ResponseEntity<List<MyUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<MyUser> addUser(@RequestBody MyUser user) {
        return ResponseEntity.ok().body(userService.addUser(user));

    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.addRole(role));

    }

    @PostMapping("/user/add_role")
    public void addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
    }

    @GetMapping("/admin")
    public String goAdmin() {
        return ("<h1>Hello Admin </h1>");
    }

    /**
     * RoleToUser
     */

}

class RoleToUserForm {
    private String username;
    private String roleName;

    public void RoleToUser() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
