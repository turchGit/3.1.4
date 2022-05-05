package com.example.boot3_1_1.service;

import com.example.boot3_1_1.model.Role;
import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public User addUser(User user) {
        if (this.getUsers().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin())) &&
                !this.getUserById(user.getId()).getLogin().equals(user.getLogin())) {
            return null;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (user.getRole() == Role.ADMIN) {
            roles.add(Role.ADMIN);
        } else {
            roles.remove(Role.ADMIN);
        }
        user.setRoles(roles);
        return userRepository.save(user);

    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (this.getUsers().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin())) &&
                !this.getUserById(user.getId()).getLogin().equals(user.getLogin())) {
            return user;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (user.getRole() == Role.ADMIN) {
            roles.add(Role.ADMIN);
        } else {
            roles.remove(Role.ADMIN);
        }
        user.setRoles(roles);
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username);
    }
}
