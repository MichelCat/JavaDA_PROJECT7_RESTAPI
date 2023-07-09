package com.nnk.springboot.domain;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<BidList> testConstraintViolation;
    private BidList bidList;

    @Before
    public void setUpPerTest() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        bidList = BidList.builder()
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .build();
    }

    // -----------------------------------------------------------------------------------------------
    // Account attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNotBlankAccount_thenNoConstraintViolation() {
        // GIVEN
        bidList.setAccount("Account Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenBlankAccount_thenOneConstraintViolation() {
        // GIVEN
        bidList.setAccount(" ");
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Account is mandatory");
    }

    @Test
    public void whenEmptyAccount_thenOneConstraintViolation() {
        // GIVEN
        bidList.setAccount("");
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Account is mandatory");
    }

    @Test
    public void whenNullAccount_thenOneConstraintViolation() {
        // GIVEN
        bidList.setAccount(null);
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Account is mandatory");
    }

    @Test
    public void whenNormalSizeAccount_thenNoConstraintViolation() {
        // GIVEN
        bidList.setAccount("Account Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeAccountTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setAccount(StringUtils.repeat('a', 31));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "account", "Maximum length of 30 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Type attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNotBlankType_thenNoConstraintViolation() {
        // GIVEN
        bidList.setType("Type Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenBlankType_thenOneConstraintViolation() {
        // GIVEN
        bidList.setType(" ");
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Type is mandatory");
    }

    @Test
    public void whenEmptyType_thenOneConstraintViolation() {
        // GIVEN
        bidList.setType("");
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Type is mandatory");
    }

    @Test
    public void whenNullType_thenOneConstraintViolation() {
        // GIVEN
        bidList.setType(null);
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Type is mandatory");
    }

    @Test
    public void whenNormalSizeType_thenNoConstraintViolation() {
        // GIVEN
        bidList.setType("Account Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeTypeTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setType(StringUtils.repeat('a', 31));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "type", "Maximum length of 30 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Type bidQuantity
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNotNullBidQuantity_thenNoConstraintViolations() {
        // GIVEN
        bidList.setBidQuantity(10d);
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenNullBidQuantity_thenOneConstraintViolation() {
        // GIVEN
        bidList.setBidQuantity(null);
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "bidQuantity", "Bid quantity cannot be null");
    }

    // -----------------------------------------------------------------------------------------------
    // Benchmark attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeBenchmark_thenNoConstraintViolation() {
        // GIVEN
        bidList.setBenchmark("Benchmark Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeBenchmarkTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setBenchmark(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "benchmark", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Commentary attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeCommentary_thenNoConstraintViolation() {
        // GIVEN
        bidList.setCommentary("Commentary Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeCommentaryTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setCommentary(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "commentary", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Security attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeSecurity_thenNoConstraintViolation() {
        // GIVEN
        bidList.setSecurity("Security Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeSecurityTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setSecurity(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "security", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Status attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeStatus_thenNoConstraintViolation() {
        // GIVEN
        bidList.setStatus("Status");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeStatusTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setStatus(StringUtils.repeat('a', 11));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "status", "Maximum length of 10 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Trader attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeTrader_thenNoConstraintViolation() {
        // GIVEN
        bidList.setTrader("Trader Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeTraderTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setTrader(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "trader", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Book attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeBook_thenNoConstraintViolation() {
        // GIVEN
        bidList.setBook("Book Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeBookTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setBook(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "book", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // CreationName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeCreationName_thenNoConstraintViolation() {
        // GIVEN
        bidList.setCreationName("CreationName Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeCreationNameTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setCreationName(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "creationName", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // RevisionName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeRevisionName_thenNoConstraintViolation() {
        // GIVEN
        bidList.setRevisionName("RevisionName Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeRevisionNameTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setRevisionName(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "revisionName", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // DealName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeDealName_thenNoConstraintViolation() {
        // GIVEN
        bidList.setDealName("DealName Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeDealNameTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setDealName(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "dealName", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // DealType attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeDealType_thenNoConstraintViolation() {
        // GIVEN
        bidList.setDealType("DealType Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeDealTypeTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setDealType(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "dealType", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // SourceListId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeSourceListId_thenNoConstraintViolation() {
        // GIVEN
        bidList.setSourceListId("SourceListId Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeSourceListIdTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setSourceListId(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "sourceListId", "Maximum length of 125 characters");
    }

    // -----------------------------------------------------------------------------------------------
    // Side attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSizeSide_thenNoConstraintViolation() {
        // GIVEN
        bidList.setSide("Side Test");
        // WHEN
        testConstraintViolation.noConstraintViolation(bidList);
    }

    @Test
    public void whenSizeSideTooBig_thenOneConstraintViolation() {
        // GIVEN
        bidList.setSide(StringUtils.repeat('a', 126));
        // WHEN
        testConstraintViolation.oneConstraintViolation(bidList, "side", "Maximum length of 125 characters");
    }
}
