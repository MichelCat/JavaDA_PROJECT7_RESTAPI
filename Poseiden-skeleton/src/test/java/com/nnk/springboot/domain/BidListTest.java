package com.nnk.springboot.domain;

import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.GlobalData;
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
 * BidListTest is the unit test class managing the BidList
 *
 * @author MC
 * @version 1.0
 */
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class BidListTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<BidList> testConstraintViolation;
    private BidList bidList;
    private Timestamp currentTimestamp;

    @BeforeEach
    public void setUpBefore() {
        currentTimestamp = GlobalData.CURRENT_TIMESTAMP;

        testConstraintViolation = new TestConstraintViolation<>(validator);

        bidList = BidData.getBidSource();
    }

    // -----------------------------------------------------------------------------------------------
    // Id attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void id_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        BidList objBuild = BidList.builder()
                            .build();
        BidList objNew = new BidList();
        // THEN
        assertThat(objBuild).usingRecursiveComparison().isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // Account attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void account_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount("Account Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getAccount()).isEqualTo("Account Test");
    }

    @Test
    public void account_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount(" ");
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    @Test
    public void account_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount("");
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    @Test
    public void account_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount(null);
        // THEN
        String[][] errorList = {{"account", "Account is mandatory"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    @Test
    public void account_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount(StringUtils.repeat('a', 31));
        // THEN
        String[][] errorList = {{"account", "Maximum length of 30 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Type attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void type_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType("Type Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getType()).isEqualTo("Type Test");
    }

    @Test
    public void type_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType(" ");
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    @Test
    public void type_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType("");
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    @Test
    public void type_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType(null);
        // THEN
        String[][] errorList = {{"type", "Type is mandatory"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    @Test
    public void type_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType(StringUtils.repeat('a', 31));
        // THEN
        String[][] errorList = {{"type", "Maximum length of 30 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Type bidQuantity
    // -----------------------------------------------------------------------------------------------
    @Test
    public void bidQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bidList.setBidQuantity(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getBidQuantity()).isEqualTo(10d);
    }

    @Test
    public void bidQuantity_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBidQuantity(null);
        // THEN
        String[][] errorList = {{"bidQuantity", "Bid quantity cannot be null"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // AskQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void askQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bidList.setAskQuantity(11d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getAskQuantity()).isEqualTo(11d);
    }

    // -----------------------------------------------------------------------------------------------
    // Bid attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void bid_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bidList.setBid(12d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getBid()).isEqualTo(12d);
    }

    // -----------------------------------------------------------------------------------------------
    // Ask attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void ask_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bidList.setAsk(13d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getAsk()).isEqualTo(13d);
    }

    // -----------------------------------------------------------------------------------------------
    // Benchmark attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void benchmark_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBenchmark("Benchmark Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getBenchmark()).isEqualTo("Benchmark Test");
    }

    @Test
    public void benchmark_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBenchmark(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"benchmark", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // BidListDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void bidListDate_normal() {
        // GIVEN
        // WHEN
        bidList.setBidListDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getBidListDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // Commentary attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void commentary_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setCommentary("Commentary Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getCommentary()).isEqualTo("Commentary Test");
    }

    @Test
    public void commentary_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setCommentary(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"commentary", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Security attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void security_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSecurity("Security Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getSecurity()).isEqualTo("Security Test");
    }

    @Test
    public void security_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSecurity(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"security", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Status attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void status_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setStatus("Status");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getStatus()).isEqualTo("Status");
    }

    @Test
    public void status_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setStatus(StringUtils.repeat('a', 11));
        // THEN
        String[][] errorList = {{"status", "Maximum length of 10 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Trader attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void trader_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setTrader("Trader Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getTrader()).isEqualTo("Trader Test");
    }

    @Test
    public void trader_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setTrader(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"trader", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Book attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void book_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBook("Book Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getBook()).isEqualTo("Book Test");
    }

    @Test
    public void book_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBook(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"book", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // CreationName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setCreationName("CreationName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getCreationName()).isEqualTo("CreationName Test");
    }

    @Test
    public void creationName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setCreationName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"creationName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // CreationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationDate_normal() {
        // GIVEN
        // WHEN
        bidList.setCreationDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getCreationDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // RevisionName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void revisionName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setRevisionName("RevisionName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getRevisionName()).isEqualTo("RevisionName Test");
    }

    @Test
    public void revisionName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setRevisionName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"revisionName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // RevisionDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void revisionDate_normal() {
        // GIVEN
        // WHEN
        bidList.setRevisionDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getRevisionDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // DealName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void dealName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setDealName("DealName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getDealName()).isEqualTo("DealName Test");
    }

    @Test
    public void dealName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setDealName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealName", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // DealType attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void dealType_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setDealType("DealType Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getDealType()).isEqualTo("DealType Test");
    }

    @Test
    public void dealType_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setDealType(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealType", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // SourceListId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sourceListId_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSourceListId("SourceListId Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getSourceListId()).isEqualTo("SourceListId Test");
    }

    @Test
    public void sourceListId_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSourceListId(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sourceListId", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Side attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void side_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSide("Side Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bidList, errorList);
        assertThat(bidList.getSide()).isEqualTo("Side Test");
    }

    @Test
    public void side_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSide(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"side", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(bidList, errorList);
    }
}
