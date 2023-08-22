package com.nnk.poseidon.model;

import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.data.GlobalData;
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
 * BidTest is the unit test class managing the Bid
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
class BidTest {

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
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Bid objBuild = Bid.builder()
                            .build();
        Bid objNew = new Bid();
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
        bid.setAccount("Account Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getAccount()).isEqualTo("Account Test");
    }

    private static Stream<Arguments> listOfAccountToTest() {
        String[][] errorSpace = {{"account", "{constraint.notBlank.bid.account}"}};
        String[][] errorEmpty = {{"account", "{constraint.notBlank.bid.account}"}};
        String[][] errorNull = {{"account", "{constraint.notBlank.bid.account}"}};
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
        bid.setAccount(account);
        // THEN
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // type attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void type_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setType("Type Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getType()).isEqualTo("Type Test");
    }

    private static Stream<Arguments> listOfTypeToTest() {
        String[][] errorSpace = {{"type", "{constraint.notBlank.bid.type}"}};
        String[][] errorEmpty = {{"type", "{constraint.notBlank.bid.type}"}};
        String[][] errorNull = {{"type", "{constraint.notBlank.bid.type}"}};
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
        bid.setType(type);
        // THEN
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // bidQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void bidQuantity_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        bid.setBidQuantity(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBidQuantity()).isEqualTo(10d);
    }

    @Test
    void bidQuantity_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBidQuantity(null);
        // THEN
        String[][] errorList = {{"bidQuantity", "{constraint.notNull.bid.bidQuantity}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // askQuantity attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void askQuantity_normal_thenNoConstraintViolations() {
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
    void bid_normal_thenNoConstraintViolations() {
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
    void ask_normal_thenNoConstraintViolations() {
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
    void benchmark_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBenchmark("Benchmark Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBenchmark()).isEqualTo("Benchmark Test");
    }

    @Test
    void benchmark_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBenchmark(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"benchmark", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // bidListDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void bidListDate_normal() {
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
    void commentary_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCommentary("Commentary Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getCommentary()).isEqualTo("Commentary Test");
    }

    @Test
    void commentary_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCommentary(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"commentary", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // security attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void security_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSecurity("Security Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getSecurity()).isEqualTo("Security Test");
    }

    @Test
    void security_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSecurity(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"security", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // status attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void status_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setStatus("Status");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getStatus()).isEqualTo("Status");
    }

    @Test
    void status_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setStatus(StringUtils.repeat('a', 11));
        // THEN
        String[][] errorList = {{"status", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // trader attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void trader_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setTrader("Trader Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getTrader()).isEqualTo("Trader Test");
    }

    @Test
    void trader_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setTrader(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"trader", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // book attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void book_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBook("Book Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getBook()).isEqualTo("Book Test");
    }

    @Test
    void book_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setBook(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"book", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationName attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void creationName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCreationName("CreationName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getCreationName()).isEqualTo("CreationName Test");
    }

    @Test
    void creationName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setCreationName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"creationName", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void creationDate_normal() {
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
    void revisionName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setRevisionName("RevisionName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getRevisionName()).isEqualTo("RevisionName Test");
    }

    @Test
    void revisionName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setRevisionName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"revisionName", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // revisionDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void revisionDate_normal() {
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
    void dealName_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealName("DealName Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getDealName()).isEqualTo("DealName Test");
    }

    @Test
    void dealName_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealName", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // dealType attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void dealType_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealType("DealType Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getDealType()).isEqualTo("DealType Test");
    }

    @Test
    void dealType_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setDealType(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"dealType", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sourceListId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sourceListId_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSourceListId("SourceListId Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getSourceListId()).isEqualTo("SourceListId Test");
    }

    @Test
    void sourceListId_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSourceListId(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sourceListId", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // side attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void side_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSide("Side Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(bid, errorList);
        assertThat(bid.getSide()).isEqualTo("Side Test");
    }

    @Test
    void side_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        bid.setSide(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"side", "{constraint.size.global}"}};
        testConstraintViolation.checking(bid, errorList);
    }
}
