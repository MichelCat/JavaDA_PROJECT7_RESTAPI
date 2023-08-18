package com.nnk.poseidon.repository;

import com.nnk.poseidon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

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

    boolean existsByUsername(String username);

    Optional<User> findByEmailValidationKey(String validationKey);
}
