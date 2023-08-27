package com.clinic.service;


import com.clinic.entity.User;
import com.clinic.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class UserServiceImplementsTest {

    @SpyBean
    @Autowired
    private UserService toTest;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void test_deleteById_ok(){
        Mockito.doReturn(new User()).when(toTest).findById(Mockito.any());
        toTest.deleteById(1);
    }





}
