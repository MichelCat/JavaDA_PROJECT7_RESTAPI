package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * EmailActivationBusinessIT is a class of integration tests on email.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EmailActivationBusinessIT {

    @Autowired
    private EmailActivationBusiness emailActivationBusiness;

    private User userSave;
    private String keyValue;

    @BeforeEach
    public void setUpBefore() {
        userSave = UserData.getAlexSave();
        keyValue = userSave.getEmailValidationKey();
    }

    // -----------------------------------------------------------------------------------------------
    // activatedUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    public void activatedUser_normal_returnUser() throws MyException {
        // GIVEN
        // WHEN
        User result = emailActivationBusiness.activatedUser(keyValue);
        // THEN
        userSave.setEnabled(true);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("validEmailEndDate")
                .isEqualTo(userSave);
    }
}
