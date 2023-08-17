package com.example.FinalProject.repository;

import com.example.FinalProject.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUsername(String username);

    Boolean existsByUsername(String username);


}
