package com.example.auth.services;

import com.example.auth.entity.UserInfo;
import com.example.auth.entity.UserInfoDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.repository.UserInfoRepository;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    private PasswordEncoder passwordEncoder;

    private UserInfoRepository userInfoRepository;

    public UserInfoService() {
    }

    @Autowired
    public UserInfoService(PasswordEncoder passwordEncoder, UserInfoRepository userInfoRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userInfoRepository = userInfoRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            Optional<UserInfo> userDetail = userInfoRepository.findByName(username);
            System.out.println(userDetail);
            System.out.println(username);

            return userDetail.map(UserInfoDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        } catch (Exception e) {
            throw e;
            // TODO: handle exception
        }
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User registered";
    }
}
