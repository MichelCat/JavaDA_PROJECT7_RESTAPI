package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.model.Bid;
import com.nnk.poseidon.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * BidListBusinessTest is a unit testing class of the bids service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class BidListBusinessTest {

    @Autowired
    private BidListBusiness bidListBusiness;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private BidRepository bidRepository;

    private TestMessageSource testMessageSource;
    private Bid bidSource;
    private Bid bidSave;
    public List<Bid> bidsList;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        bidSource = BidData.getBidSource();
        bidSave = BidData.getBidSave();

        bidsList = new ArrayList<>();
        bidsList.add(bidSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getBidsList method
    // -----------------------------------------------------------------------------------------------
    @Test
     void getBidsList_findAllNormal() {
        // GIVEN
        when(bidRepository.findAll()).thenReturn(bidsList);
        // WHEN
        assertThat(bidListBusiness.getBidsList()).isEqualTo(bidsList);
        // THEN
        verify(bidRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getBidsList_findAllEmpty() {
        // GIVEN
        when(bidRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(bidListBusiness.getBidsList()).isEmpty();
        // THEN
        verify(bidRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    void createBid_saveNormal() throws MyException {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(bidRepository.save(bidSource)).thenReturn(bidSave);
        // WHEN
        assertThat(bidListBusiness.createBid(bidSource)).isEqualTo(bidSave);
        // THEN
        verify(bidRepository, Mockito.times(1)).save(any(Bid.class));
    }

    @Test
    void createBid_nullBidParameter_returnNullPointer() {
        // GIVEN
        when(bidRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.createBid(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.nullBid", null);
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    void createBid_bidExist_returnMyException() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.createBid(bidSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.bidExists", new Object[] { bidSave.getBidListId() });
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getBidById method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getBidById_findByIdNormal() throws MyException {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        assertThat(bidListBusiness.getBidById(1)).isEqualTo(bidSave);
        // THEN
        verify(bidRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void getBidById_nullIdParameter_returnMyException() {
        // GIVEN
        when(bidRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.getBidById(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { null });
        verify(bidRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateBid_updateNormal() throws MyException {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        when(bidRepository.save(bidSave)).thenReturn(bidSave);
        // WHEN
        assertThat(bidListBusiness.updateBid(1, bidSource)).isEqualTo(bidSave);
        // THEN
        verify(bidRepository, Mockito.times(1)).save(any(Bid.class));
    }

    @Test
    void updateBid_bidNotExist_returnMyException() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.updateBid(2, bidSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { 2 });
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    void updateBid_nullIdParameter_returnMyException() {
        // GIVEN
        when(bidRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.updateBid(null, bidSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { null });
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    void updateBid_zeroIdParameter_returnMyException() {
        // GIVEN
        when(bidRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.updateBid(0, bidSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { 0 });
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    void updateBid_nullBidParameter_returnMyException() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.updateBid(1, null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.nullBid", null);
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    void deleteBid_deleteNormal() throws MyException {
        // GIVEN
        when(bidRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(bidRepository).deleteById(any(Integer.class));
        // WHEN
        bidListBusiness.deleteBid(1);
        // THEN
        verify(bidRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    void deleteBid_bidNotExist_returnMyException() {
        // GIVEN
        when(bidRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.deleteBid(2));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { 2 });
        verify(bidRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteBid_nullIdParameter_returnMyException() {
        // GIVEN
        when(bidRepository.existsById(null)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.deleteBid(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { null });
        verify(bidRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteBid_zeroIdParameter_returnMyException() {
        // GIVEN
        when(bidRepository.existsById(0)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> bidListBusiness.deleteBid(0));
        // THEN
        testMessageSource.compare(exception
                            , "exception.bid.unknown", new Object[] { 0 });
        verify(bidRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}
