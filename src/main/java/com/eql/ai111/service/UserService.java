package com.eql.ai111.service;

import com.eql.ai111.entities.User;

public interface UserService {

    void saveUser(User user);
    void updateUser(User user);



    User findUserByEmail(String email);

    User getUserById(Long id);

    void deleteUserById(Long id);

}
