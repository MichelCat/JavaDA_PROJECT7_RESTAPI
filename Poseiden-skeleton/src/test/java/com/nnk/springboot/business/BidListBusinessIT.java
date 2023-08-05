package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MyExceptionBadRequestException;
import com.nnk.springboot.Exception.MyExceptionNotFoundException;
import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.domain.BidList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * BidListBusinessIT is a class of integration tests on bids.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BidListBusinessIT {

    @Autowired
    private BidListBusiness bidListBusiness;

    private BidList bidSource;
    private BidList bidSave;


    @BeforeEach
    public void setUpBefore() {
        bidSource = BidData.getBidSource();
        bidSave = BidData.getBidSave();
    }

    // -----------------------------------------------------------------------------------------------
    // getBidsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void getBidsList_findAllNormal() {
        // GIVEN
        // WHEN
        List<BidList> result = bidListBusiness.getBidsList();
        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(bidSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getBidsList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<BidList> result = bidListBusiness.getBidsList();
        // THEN
        assertThat(result.size()).isEqualTo(0);
    }

    // -----------------------------------------------------------------------------------------------
    // createBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void createBid_bidNotExist() {
        // GIVEN
        // WHEN
        BidList result = bidListBusiness.createBid(bidSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(bidSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void createBid_bidExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.createBid(bidSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getBidById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void getBidById_bidExist() {
        // GIVEN
        // WHEN
        assertThat(bidListBusiness.getBidById(bidSave.getBidListId())).isEqualTo(bidSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getBidById_bidNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.getBidById(bidSave.getBidListId()));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void updateBid_bidExist() {
        // GIVEN
        // WHEN
        BidList result = bidListBusiness.updateBid(bidSave.getBidListId(), bidSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("revisionDate")
                .isEqualTo(bidSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateBid_bidNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.updateBid(bidSave.getBidListId(), bidSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void deleteBid_bidExist() {
        // GIVEN
        // WHEN
        bidListBusiness.deleteBid(bidSave.getBidListId());
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.getBidById(bidSave.getBidListId()));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteBid_bidNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.deleteBid(bidSave.getBidListId()));
        // THEN
    }
}
