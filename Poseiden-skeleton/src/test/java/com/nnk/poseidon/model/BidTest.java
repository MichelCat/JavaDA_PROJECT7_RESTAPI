package com.nnk.poseidon.model;

import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.data.GlobalData;
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
 * BidTest is the unit test class managing the Bid
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
public class BidTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<Bid> testConstraintViolation;
    private Bid bid;
    private Timestamp currentTimestamp;

    @BeforeEach
    public void setUpBefore() {
        currentTimestamp = GlobalData.CURRENT_TIMESTAMP;

        testConstraintViolation = new TestConstraintViolation<>(validator);

        bid = BidData.getBidSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Bid objBuild = Bid.builder()
                            .build();
        Bid objNew = new Bid();
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
        bid.setAccount("Account Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getAccount()).isEqualTo("Account Test");
    }

    @Test
    public void account_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setAccount(" ");
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(bid, errorList);
    }

    @Test
    public void account_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setAccount("");
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(bid, errorList);
    }

    @Test
    public void account_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setAccount(null);
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(bid, errorList);
    }

    @Test
    public void account_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setAccount(StringUtils.repeat('a', 31));
        // THEN
        String[][] errorList = {{"account", "Maximum length of 30 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // type attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void type_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setType("Type Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getType()).isEqualTo("Type Test");
    }

    @Test
    public void type_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setType(" ");
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(bid, errorList);
    }

    @Test
    public void type_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setType("");
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(bid, errorList);
    }

    @Test
    public void type_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setType(null);
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(bid, errorList);
    }

    @Test
    public void type_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setType(StringUtils.repeat('a', 31));
        // THEN
        String[][] errorList = {{"type", "Maximum length of 30 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // bidQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void bidQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bid.setBidQuantity(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBidQuantity()).isEqualTo(10d);
    }

    @Test
    public void bidQuantity_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBidQuantity(null);
        // THEN
        String[][] errorList = {{"bidQuantity", "Bid quantity must not be null"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // askQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void askQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bid.setAskQuantity(11d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getAskQuantity()).isEqualTo(11d);
    }

    // -----------------------------------------------------------------------------------------------
    // bid attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void bid_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bid.setBid(12d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBid()).isEqualTo(12d);
    }

    // -----------------------------------------------------------------------------------------------
    // ask attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void ask_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bid.setAsk(13d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getAsk()).isEqualTo(13d);
    }

    // -----------------------------------------------------------------------------------------------
    // benchmark attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void benchmark_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBenchmark("Benchmark Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBenchmark()).isEqualTo("Benchmark Test");
    }

    @Test
    public void benchmark_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBenchmark(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"benchmark", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // bidListDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void bidListDate_normal() {
        // GIVEN
        // WHEN
        bid.setBidListDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBidListDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // commentary attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void commentary_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCommentary("Commentary Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getCommentary()).isEqualTo("Commentary Test");
    }

    @Test
    public void commentary_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCommentary(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"commentary", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // security attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void security_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSecurity("Security Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getSecurity()).isEqualTo("Security Test");
    }

    @Test
    public void security_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSecurity(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"security", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // status attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void status_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setStatus("Status");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getStatus()).isEqualTo("Status");
    }

    @Test
    public void status_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setStatus(StringUtils.repeat('a', 11));
        // THEN
        String[][] errorList = {{"status", "Maximum length of 10 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // trader attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void trader_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setTrader("Trader Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getTrader()).isEqualTo("Trader Test");
    }

    @Test
    public void trader_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setTrader(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"trader", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // book attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void book_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBook("Book Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBook()).isEqualTo("Book Test");
    }

    @Test
    public void book_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBook(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"book", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCreationName("CreationName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getCreationName()).isEqualTo("CreationName Test");
    }

    @Test
    public void creationName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCreationName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"creationName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationDate_normal() {
        // GIVEN
        // WHEN
        bid.setCreationDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getCreationDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // revisionName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void revisionName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setRevisionName("RevisionName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getRevisionName()).isEqualTo("RevisionName Test");
    }

    @Test
    public void revisionName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setRevisionName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"revisionName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // revisionDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void revisionDate_normal() {
        // GIVEN
        // WHEN
        bid.setRevisionDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getRevisionDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // dealName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void dealName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealName("DealName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getDealName()).isEqualTo("DealName Test");
    }

    @Test
    public void dealName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // dealType attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void dealType_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealType("DealType Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getDealType()).isEqualTo("DealType Test");
    }

    @Test
    public void dealType_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealType(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealType", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sourceListId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sourceListId_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSourceListId("SourceListId Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getSourceListId()).isEqualTo("SourceListId Test");
    }

    @Test
    public void sourceListId_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSourceListId(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sourceListId", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // side attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void side_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSide("Side Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getSide()).isEqualTo("Side Test");
    }

    @Test
    public void side_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSide(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"side", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bid, errorList);
    }
}
