package com.nnk.springboot.domain;

import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.GlobalData;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BidListTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<BidList> testConstraintViolation;
    private BidList bidList;

    private Timestamp currentTimestamp;

    @Before
    public void setUpBefore() {
        currentTimestamp = GlobalData.CURRENT_TIMESTAMP;

        testConstraintViolation = new TestConstraintViolation<>(validator);

        bidList = BidData.getBidSource();
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getAccount()).isEqualTo("Account Test");
    }

    @Test
    public void account_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount(" ");
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Account is mandatory");
    }

    @Test
    public void account_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount("");
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Account is mandatory");
    }

    @Test
    public void account_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount(null);
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Account is mandatory");
    }

    @Test
    public void account_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setAccount(StringUtils.repeat('a', 31));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Maximum length of 30 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getType()).isEqualTo("Type Test");
    }

    @Test
    public void type_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType(" ");
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Type is mandatory");
    }

    @Test
    public void type_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType("");
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Type is mandatory");
    }

    @Test
    public void type_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType(null);
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Type is mandatory");
    }

    @Test
    public void type_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setType(StringUtils.repeat('a', 31));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Maximum length of 30 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getBidQuantity()).isEqualTo(10d);
    }

    @Test
    public void bidQuantity_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBidQuantity(null);
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "bidQuantity", "Bid quantity cannot be null");
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
        testConstraintViolation.noConstraintViolation(bidList);
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
        testConstraintViolation.noConstraintViolation(bidList);
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
        testConstraintViolation.noConstraintViolation(bidList);
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getBenchmark()).isEqualTo("Benchmark Test");
    }

    @Test
    public void benchmark_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBenchmark(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "benchmark", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getCommentary()).isEqualTo("Commentary Test");
    }

    @Test
    public void commentary_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setCommentary(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "commentary", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getSecurity()).isEqualTo("Security Test");
    }

    @Test
    public void security_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSecurity(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "security", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getStatus()).isEqualTo("Status");
    }

    @Test
    public void status_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setStatus(StringUtils.repeat('a', 11));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "status", "Maximum length of 10 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getTrader()).isEqualTo("Trader Test");
    }

    @Test
    public void trader_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setTrader(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "trader", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getBook()).isEqualTo("Book Test");
    }

    @Test
    public void book_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setBook(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "book", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getCreationName()).isEqualTo("CreationName Test");
    }

    @Test
    public void creationName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setCreationName(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "creationName", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getRevisionName()).isEqualTo("RevisionName Test");
    }

    @Test
    public void revisionName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setRevisionName(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "revisionName", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getDealName()).isEqualTo("DealName Test");
    }

    @Test
    public void dealName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setDealName(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "dealName", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getDealType()).isEqualTo("DealType Test");
    }

    @Test
    public void dealType_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setDealType(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "dealType", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getSourceListId()).isEqualTo("SourceListId Test");
    }

    @Test
    public void sourceListId_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSourceListId(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "sourceListId", "Maximum length of 125 characters");
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
        testConstraintViolation.noConstraintViolation(bidList);
        assertThat(bidList.getSide()).isEqualTo("Side Test");
    }

    @Test
    public void side_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bidList.setSide(StringUtils.repeat('a', 126));
        // THEN
        testConstraintViolation.oneConstraintViolation(bidList, "side", "Maximum length of 125 characters");
    }
}
