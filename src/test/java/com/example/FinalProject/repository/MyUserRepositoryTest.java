package com.example.FinalProject.repository;

import com.example.FinalProject.exceptions.MyUserNotFoundExeption;
import com.example.FinalProject.model.MyUser;
import com.example.FinalProject.service.MyUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyUserRepositoryTest {

    @Mock
    MyUserRepository myUserRepository;


    @Test
    public void testFindMyUserByUsername_returnSuccessfully() throws MyUserNotFoundExeption{
        //given
        MyUser testUser = new MyUser(
                "user",
                ""        );


        when(myUserRepository.findByUsername(anyString())).thenReturn(testUser);
        when(myUserRepository.existsByUsername(anyString())).thenReturn(true);
        MyUserService myUserService = new MyUserService(myUserRepository);
        //when
        MyUser selectedUser = myUserService.getMyUserByUsername("TestUser");

        //then
        assertNotNull(selectedUser);
        assertEquals("user", selectedUser.getUsername());
        verify(myUserRepository).findByUsername("TestUser");
    }
}
