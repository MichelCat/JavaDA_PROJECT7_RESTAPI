package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.model.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * BidListBusinessIT is a class of integration tests on bids service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BidListBusinessIT {

    @Autowired
    private BidListBusiness bidListBusiness;
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;
    private Bid bidSource;
    private Bid bidSave;
    private Integer bidId;

    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        bidSource = BidData.getBidSource();
        bidSave = BidData.getBidSave();

        bidId = bidSave.getBidListId();
    }

    // -----------------------------------------------------------------------------------------------
    // getBidsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void getBidsList_findAllNormal() {
        // GIVEN
        // WHEN
        List<Bid> result = bidListBusiness.getBidsList();
        // THEN
        assertThat(result).hasSize(1)
                            .contains(bidSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getBidsList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Bid> result = bidListBusiness.getBidsList();
        // THEN
        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------------------------------
    // createBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void createBid_bidNotExist() throws MyException {
        // GIVEN
        // WHEN
        Bid result = bidListBusiness.createBid(bidSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(bidSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void createBid_bidExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.createBid(bidSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.bidExists", new Object[] { bidSave.getBidListId() });
    }

    // -----------------------------------------------------------------------------------------------
    // getBidById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void getBidById_bidExist() throws MyException {
        // GIVEN
        // WHEN
        assertThat(bidListBusiness.getBidById(bidId)).isEqualTo(bidSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getBidById_bidNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.getBidById(bidId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { bidId });
    }

    // -----------------------------------------------------------------------------------------------
    // updateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void updateBid_bidExist() throws MyException {
        // GIVEN
        // WHEN
        Bid result = bidListBusiness.updateBid(bidId, bidSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("revisionDate")
                .isEqualTo(bidSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateBid_bidNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.updateBid(bidId, bidSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { bidId });
    }

    // -----------------------------------------------------------------------------------------------
    // deleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void deleteBid_bidExist() throws MyException {
        // GIVEN
        // WHEN
        bidListBusiness.deleteBid(bidId);
        // THEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.getBidById(bidId));
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { bidId });
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteBid_bidNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.deleteBid(bidId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { bidId });
    }
}
