package com.example.demo.Repositories;

import com.example.demo.Models.MyUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {

    public MyUser findByUsername(String username);

}
