package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


/**
 * UserRepository is the interface that manages User
 *
 * @author MC
 * @version 1.0
 */
//public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailValidationKey(String validationKey);
}
