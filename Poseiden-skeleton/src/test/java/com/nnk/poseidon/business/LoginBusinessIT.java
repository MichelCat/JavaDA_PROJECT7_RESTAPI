package com.nnk.poseidon.business;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.AppUserPrincipal;
import com.nnk.poseidon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LoginBusinessIT is a class of integration tests on login.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoginBusinessIT {

    @Autowired
    private LoginBusiness loginBusiness;

    private User userSave;


    @BeforeEach
    public void setUpBefore() {
        userSave = UserData.getUserSave();
    }

    // -----------------------------------------------------------------------------------------------
    // loadUserByUsername method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    public void loadUserByUsername_userExist() {
        // GIVEN
        // WHEN
        AppUserPrincipal result = (AppUserPrincipal)loginBusiness.loadUserByUsername(userSave.getUsername());
        // THEN
        assertThat(result.getAppUser()).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("validEmailEndDate")
                .isEqualTo(userSave);
    }

    // -----------------------------------------------------------------------------------------------
    // addUser method
    // -----------------------------------------------------------------------------------------------

}
