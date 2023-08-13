package com.nnk.poseidon.domain;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.TradeData;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * TradeTest is the unit test class managing the Trade
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
public class TradeTest {

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
    public void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Trade objBuild = Trade.builder()
                            .build();
        Trade objNew = new Trade();
        // THEN
        assertThat(objBuild).usingRecursiveComparison().isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // account attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void account_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setAccount("Account Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getAccount()).isEqualTo("Account Test");
    }

    @Test
    public void account_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setAccount(" ");
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(trade, errorList);
    }

    @Test
    public void account_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setAccount("");
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(trade, errorList);
    }

    @Test
    public void account_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setAccount(null);
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(trade, errorList);
    }

    @Test
    public void account_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setAccount(StringUtils.repeat('a', 31));
        // THEN
        String[][] errorList = {{"account", "Maximum length of 30 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // type attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void type_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setType("Type Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getType()).isEqualTo("Type Test");
    }

    @Test
    public void type_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setType(" ");
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(trade, errorList);
    }

    @Test
    public void type_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setType("");
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(trade, errorList);
    }

    @Test
    public void type_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setType(null);
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(trade, errorList);
    }

    @Test
    public void type_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setType(StringUtils.repeat('a', 31));
        // THEN
        String[][] errorList = {{"type", "Maximum length of 30 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // buyQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void buyQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        trade.setBuyQuantity(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBuyQuantity()).isEqualTo(10d);
    }

    @Test
    public void buyQuantity_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBuyQuantity(null);
        // THEN
        String[][] errorList = {{"buyQuantity", "Buy quantity must not be null"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sellQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sellQuantity_normal_thenNoConstraintViolations() {
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
    public void buyPrice_normal_thenNoConstraintViolations() {
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
    public void sellPrice_normal_thenNoConstraintViolations() {
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
    public void benchmark_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBenchmark("Benchmark Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBenchmark()).isEqualTo("Benchmark Test");
    }

    @Test
    public void benchmark_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBenchmark(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"benchmark", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // tradeDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void tradeDate_normal() {
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
    public void security_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSecurity("Security Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSecurity()).isEqualTo("Security Test");
    }

    @Test
    public void security_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSecurity(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"security", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // status attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void status_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setStatus("Status");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getStatus()).isEqualTo("Status");
    }

    @Test
    public void status_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setStatus(StringUtils.repeat('a', 11));
        // THEN
        String[][] errorList = {{"status", "Maximum length of 10 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // trader attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void trader_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setTrader("Trader Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getTrader()).isEqualTo("Trader Test");
    }

    @Test
    public void trader_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setTrader(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"trader", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // book attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void book_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBook("Book Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getBook()).isEqualTo("Book Test");
    }

    @Test
    public void book_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setBook(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"book", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setCreationName("CreationName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getCreationName()).isEqualTo("CreationName Test");
    }

    @Test
    public void creationName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setCreationName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"creationName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationDate_normal() {
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
    public void revisionName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setRevisionName("RevisionName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getRevisionName()).isEqualTo("RevisionName Test");
    }

    @Test
    public void revisionName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setRevisionName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"revisionName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // revisionDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void revisionDate_normal() {
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
    public void dealName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealName("DealName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getDealName()).isEqualTo("DealName Test");
    }

    @Test
    public void dealName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // dealType attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void dealType_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealType("DealType Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getDealType()).isEqualTo("DealType Test");
    }

    @Test
    public void dealType_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setDealType(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealType", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sourceListId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sourceListId_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSourceListId("SourceListId Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSourceListId()).isEqualTo("SourceListId Test");
    }

    @Test
    public void sourceListId_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSourceListId(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sourceListId", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // side attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void side_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSide("Side Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(trade, errorList);
        assertThat(trade.getSide()).isEqualTo("Side Test");
    }

    @Test
    public void side_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        trade.setSide(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"side", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(trade, errorList);
    }
}
