package com.johncnstn.data.service.impl;

import com.johncnstn.data.detail.CustomUserDetail;
import com.johncnstn.data.dto.UserDto;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.UserRepository;
import com.johncnstn.data.service.UserService;
import com.johncnstn.exception.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User registerNewUserAccount(UserDto userDto) throws UsernameExistsException {

        if (usernameExist(userDto.getUserName())) {
            throw new UsernameExistsException("There is an account with that email address: "  + userDto.getUserName());
        }

        User user = new User();

        user.setUserName(userDto.getUserName());

        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    private boolean usernameExist(String userName) {
        User user = userRepository.findByUserName(userName);
        return user != null;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CustomUserDetail loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUser(user);
        customUserDetail.setAuthorities(getGrantedAuthorities());

        return customUserDetail;
    }

    private List<GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

}
