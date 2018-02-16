package com.johncnstn.data.service;

import com.johncnstn.data.dto.UserDto;
import com.johncnstn.data.entity.User;
import com.johncnstn.exception.UsernameExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();
    User findOne(long id);
    User registerNewUserAccount(UserDto accountDto) throws UsernameExistsException;
}