package com.project.retail.user.service.impl;

import com.project.retail.model.entity.User;
import com.project.retail.user.repository.UserRepository;
import com.project.retail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        else return null;
    }
}
