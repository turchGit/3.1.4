package com.example.boot3_1_1.service;

import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).get();

    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);

    }
}
