package com.nnk.springboot.repositories;

import com.nnk.springboot.data.UserData;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.data.BidData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserDaoIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(scripts = BidData.scriptClearDataBase)
    public void userTest() {
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
        assertThat(user).isNotNull();
        assertThat(user).usingRecursiveComparison().isEqualTo(oldUser);

        // Find
        List<User> listResult = userRepository.findAll();
        assertThat(listResult).isNotNull();
        assertThat(listResult).hasSize(1);
        assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(user);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> optUser = userRepository.findById(id);
        assertThat(optUser).isEmpty();
    }
}
