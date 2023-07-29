package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MyException;
import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.data.UserData;
import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;

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
    // EmailActivationBusiness method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    public void emailActivationBusiness_normal_returnUser() throws MyException {
        // GIVEN
        // WHEN
        User result = emailActivationBusiness.emailActivationBusiness(keyValue);
        // THEN
        userSave.setEnabled(true);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("validEmailEndDate")
                .isEqualTo(userSave);
    }
}
