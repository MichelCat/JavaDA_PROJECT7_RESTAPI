package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
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
public class BidListBusinessTest {

    @Autowired
    private BidListBusiness bidListBusiness;

    @MockBean
    private BidRepository bidRepository;

    private Bid bidSource;
    private Bid bidSave;
    public List<Bid> bidsList;


    @BeforeEach
    public void setUpBefore() {
        bidSource = BidData.getBidSource();
        bidSave = BidData.getBidSave();

        bidsList = new ArrayList<>();
        bidsList.add(bidSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getBidsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getBidsList_findAllNormal() {
        // GIVEN
        when(bidRepository.findAll()).thenReturn(bidsList);
        // WHEN
        assertThat(bidListBusiness.getBidsList()).isEqualTo(bidsList);
        // THEN
        verify(bidRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getBidsList_findAllEmpty() {
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
    public void createBid_saveNormal() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(bidRepository.save(bidSource)).thenReturn(bidSave);
        // WHEN
        assertThat(bidListBusiness.createBid(bidSource)).isEqualTo(bidSave);
        // THEN
        verify(bidRepository, Mockito.times(1)).save(any(Bid.class));
    }

    @Test
    public void createBid_nullBidParameter_returnNullPointer() {
        // GIVEN
        when(bidRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.createBid(null));
        // THEN
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    public void createBid_bidExist_returnBadRequest() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.createBid(bidSave));
        // THEN
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getBidById method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getBidById_findByIdNormal() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        assertThat(bidListBusiness.getBidById(1)).isEqualTo(bidSave);
        // THEN
        verify(bidRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void getBidById_nullIdParameter_returnNotFound() {
        // GIVEN
        when(bidRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.getBidById(null));
        // THEN
        verify(bidRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateBid_updateNormal() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        when(bidRepository.save(bidSave)).thenReturn(bidSave);
        // WHEN
        assertThat(bidListBusiness.updateBid(1, bidSource)).isEqualTo(bidSave);
        // THEN
        verify(bidRepository, Mockito.times(1)).save(any(Bid.class));
    }

    @Test
    public void updateBid_bidNotExist_returnNotFound() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.updateBid(2, bidSource));
        // THEN
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    public void updateBid_nullIdParameter_returnNotFound() {
        // GIVEN
        when(bidRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.updateBid(null, bidSource));
        // THEN
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    public void updateBid_zeroIdParameter_returnNotFound() {
        // GIVEN
        when(bidRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.updateBid(0, bidSource));
        // THEN
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    @Test
    public void updateBid_nullBidParameter_returnNotFound() {
        // GIVEN
        when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.updateBid(1, null));
        // THEN
        verify(bidRepository, Mockito.times(0)).save(any(Bid.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void deleteBid_deleteNormal() {
        // GIVEN
        when(bidRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(bidRepository).deleteById(any(Integer.class));
        // WHEN
        bidListBusiness.deleteBid(1);
        // THEN
        verify(bidRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteBid_bidNotExist_returnNotFound() {
        // GIVEN
        when(bidRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.deleteBid(2));
        // THEN
        verify(bidRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteBid_nullIdParameter_returnNotFound() {
        // GIVEN
        when(bidRepository.existsById(null)).thenReturn(false);
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.deleteBid(null));
        // THEN
        verify(bidRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteBid_zeroIdParameter_returnNotFound() {
        // GIVEN
        when(bidRepository.existsById(0)).thenReturn(false);
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.deleteBid(0));
        // THEN
        verify(bidRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}
