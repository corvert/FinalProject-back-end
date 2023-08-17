package com.example.FinalProject.service;

import com.example.FinalProject.exceptions.MyUserNotFoundExeption;
import com.example.FinalProject.model.MyUser;
import com.example.FinalProject.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private MyUserRepository myUserRepository;

    public MyUserService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);
        return new User(myUser.getUsername(), myUser.getPassword(), new ArrayList<>());
    }

    public MyUser getMyUserByUsername(String username)
        throws MyUserNotFoundExeption {
        if (myUserRepository.existsByUsername(username)){
            return myUserRepository.findByUsername(username);
        }
        throw new MyUserNotFoundExeption("Username: " + username + " not found", 1);
    }
}
