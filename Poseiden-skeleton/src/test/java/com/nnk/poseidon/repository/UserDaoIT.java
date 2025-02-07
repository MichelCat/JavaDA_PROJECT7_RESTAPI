package com.nnk.poseidon.repository;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserDaoIT is the integration test class handling User
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserDaoIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void userTest() {
        User oldUser;
        User user = UserData.getUserSource();

        // Save
        oldUser = user;
        user = userRepository.save(user);
        assertThat(user).isNotNull();
        assertThat(user).usingRecursiveComparison().ignoringFields("id").isEqualTo(oldUser);
        assertThat(user.getId()).isEqualTo(1);

        // Update
        user.setFullname("User Update");
        oldUser = user;
        user = userRepository.save(user);
        assertThat(user).isNotNull()
                            .isEqualTo(oldUser);

        // Find
        List<User> listResult = userRepository.findAll();
        assertThat(listResult).isNotNull()
                                .hasSize(1)
                                .contains(user);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> optUser = userRepository.findById(id);
        assertThat(optUser).isEmpty();
    }
}
