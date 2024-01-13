package com.telemed.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryDB extends CrudRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    List<User> findByType(int type);

    User findUserById(int id);

    List<User> findByLname(String lname);
}
