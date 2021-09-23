package com.fam.studyio.services;

import com.fam.studyio.domain.entity.UserEntity;
import com.fam.studyio.domain.models.UserModel;
import com.fam.studyio.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsDataService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userDao.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                new ArrayList<>());
    }

    public UserEntity getUserDataByUsername(String user){
        return userDao.findByUserName(user);
    }

    public UserEntity save(UserModel user) {
        UserEntity newUser = new UserEntity();

        newUser.setUserName(user.getUserName());
        newUser.setName(user.getName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setBirthDate(user.getBirthDate());

        return userDao.save(newUser);
    }
}
