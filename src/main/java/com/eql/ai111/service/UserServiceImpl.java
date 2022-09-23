package com.eql.ai111.service;

import com.eql.ai111.entities.Role;
import com.eql.ai111.entities.User;
import com.eql.ai111.repository.RoleRepository;
import com.eql.ai111.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(User user) {
        User user1 = new User();

        user1.setId(user.getId());
        user1.setNom(user.getNom());
        user1.setPrenom(user.getPrenom());
        user1.setAdresse(user.getAdresse());
        user1.setNumero(user.getNumero());
        user1.setZip(user.getZip());
        user1.setPays(user.getPays());
        user1.setEmail(user.getEmail());
        user1.setVille(user.getVille());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByLabel("ROLE_USER");

        if(role == null){
            role = checkRoleExist();
        }
        user1.setRoles(Arrays.asList(role));
        userRepository.save(user1);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }


    private Role checkRoleExist(){
        Role role = new Role();
        role.setLabel("ROLE_USER");
        return  roleRepository.save(role);
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        User user = null;

        if (optional.isPresent()){
            user = optional.get();
        } else {
            throw new RuntimeException("Il existe pas: " +id);
        }
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
