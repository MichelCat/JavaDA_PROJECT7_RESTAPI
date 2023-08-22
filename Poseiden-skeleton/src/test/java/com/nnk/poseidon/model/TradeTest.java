package com.nnk.poseidon.model;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.TradeData;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * TradeTest is the unit test class managing the Trade
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
class TradeTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<Trade> testConstraintViolation;
    private Trade trade;
    private Timestamp currentTimestamp;

    @BeforeEach
    public void setUpBefore() {
        currentTimestamp = GlobalData.CURRENT_TIMESTAMP;

        testConstraintViolation = new TestConstraintViolation<>(validator);

        trade = TradeData.getTradeSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Trade objBuild = Trade.builder()
                            .build();
        Trade objNew = new Trade();
        // THEN
        assertThat(objBuild).isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // account attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void account_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setAccount("Account Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getAccount()).isEqualTo("Account Test");
    }

    private static Stream<Arguments> listOfAccountToTest() {
        String[][] errorSpace = {{"account", "{constraint.notBlank.trade.account}"}};
        String[][] errorEmpty = {{"account", "{constraint.notBlank.trade.account}"}};
        String[][] errorNull = {{"account", "{constraint.notBlank.trade.account}"}};
        String[][] errorSizeTooBig = {{"account", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 31), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Account is {2} ({0}).")
    @MethodSource("listOfAccountToTest")
    void account_thenConstraintViolation(String account, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        trade.setAccount(account);
        // THEN
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // type attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void type_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setType("Type Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getType()).isEqualTo("Type Test");
    }

    private static Stream<Arguments> listOfTypeToTest() {
        String[][] errorSpace = {{"type", "{constraint.notBlank.trade.type}"}};
        String[][] errorEmpty = {{"type", "{constraint.notBlank.trade.type}"}};
        String[][] errorNull = {{"type", "{constraint.notBlank.trade.type}"}};
        String[][] errorSizeTooBig = {{"type", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 31), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Type is {2} ({0}).")
    @MethodSource("listOfTypeToTest")
    void type_thenConstraintViolation(String type, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        trade.setType(type);
        // THEN
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // buyQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void buyQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        trade.setBuyQuantity(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBuyQuantity()).isEqualTo(10d);
    }

    @Test
    void buyQuantity_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBuyQuantity(null);
        // THEN
        String[][] errorList = {{"buyQuantity", "{constraint.notNull.trade.buyQuantity}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sellQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sellQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        trade.setSellQuantity(11d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSellQuantity()).isEqualTo(11d);
    }

    // -----------------------------------------------------------------------------------------------
    // buyPrice attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void buyPrice_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        trade.setBuyPrice(12d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBuyPrice()).isEqualTo(12d);
    }

    // -----------------------------------------------------------------------------------------------
    // sellPrice attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sellPrice_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        trade.setSellPrice(13d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSellPrice()).isEqualTo(13d);
    }

    // -----------------------------------------------------------------------------------------------
    // benchmark attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void benchmark_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBenchmark("Benchmark Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBenchmark()).isEqualTo("Benchmark Test");
    }

    @Test
    void benchmark_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBenchmark(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"benchmark", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // tradeDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void tradeDate_normal() {
        // GIVEN
        // WHEN
        trade.setTradeDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getTradeDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // security attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void security_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSecurity("Security Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSecurity()).isEqualTo("Security Test");
    }

    @Test
    void security_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSecurity(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"security", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // status attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void status_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setStatus("Status");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getStatus()).isEqualTo("Status");
    }

    @Test
    void status_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setStatus(StringUtils.repeat('a', 11));
        // THEN
        String[][] errorList = {{"status", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // trader attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void trader_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setTrader("Trader Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getTrader()).isEqualTo("Trader Test");
    }

    @Test
    void trader_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setTrader(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"trader", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // book attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void book_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBook("Book Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBook()).isEqualTo("Book Test");
    }

    @Test
    void book_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBook(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"book", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void creationName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setCreationName("CreationName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getCreationName()).isEqualTo("CreationName Test");
    }

    @Test
    void creationName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setCreationName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"creationName", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void creationDate_normal() {
        // GIVEN
        // WHEN
        trade.setCreationDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getCreationDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // revisionName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void revisionName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setRevisionName("RevisionName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getRevisionName()).isEqualTo("RevisionName Test");
    }

    @Test
    void revisionName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setRevisionName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"revisionName", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // revisionDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void revisionDate_normal() {
        // GIVEN
        // WHEN
        trade.setRevisionDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getRevisionDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // dealName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void dealName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealName("DealName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getDealName()).isEqualTo("DealName Test");
    }

    @Test
    void dealName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealName", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // dealType attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void dealType_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealType("DealType Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getDealType()).isEqualTo("DealType Test");
    }

    @Test
    void dealType_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealType(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealType", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sourceListId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sourceListId_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSourceListId("SourceListId Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSourceListId()).isEqualTo("SourceListId Test");
    }

    @Test
    void sourceListId_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSourceListId(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sourceListId", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // side attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void side_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSide("Side Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSide()).isEqualTo("Side Test");
    }

    @Test
    void side_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSide(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"side", "{constraint.size.global}"}};
        testConstraintViolation.checking(trade, errorList);
    }
}
