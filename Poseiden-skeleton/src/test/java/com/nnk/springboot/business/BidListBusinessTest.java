package com.nnk.springboot.business;


import com.nnk.springboot.Exception.MyExceptionBadRequestException;
import com.nnk.springboot.Exception.MyExceptionNotFoundException;
import com.nnk.springboot.data.BidData;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BidListBusinessTest {

    @Autowired
    private BidListBusiness bidListBusiness;

    @MockBean
    private BidListRepository bidListRepository;

    private BidList bidSource;
    private BidList bidSave;
    public List<BidList> bidsList;


    @Before
    public void setUpBefore() {
        bidSource = BidData.getBidSource();
        bidSave = BidData.getBidSave();

        bidsList = new ArrayList<>();
        bidsList.add(bidSave);
    }

    // -----------------------------------------------------------------------------------------------
    // GetBidsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getBidsList_findAllNormal() {
        // GIVEN
        when(bidListRepository.findAll()).thenReturn(bidsList);
        // WHEN
        assertThat(bidListBusiness.getBidsList()).isEqualTo(bidsList);
        // THEN
        verify(bidListRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getBidsList_findAllEmpty() {
        // GIVEN
        when(bidListRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(bidListBusiness.getBidsList()).isEmpty();
        // THEN
        verify(bidListRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // CreateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void createBid_saveNormal() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(bidListRepository.save(bidSource)).thenReturn(bidSave);
        // WHEN
        assertThat(bidListBusiness.createBid(bidSource)).isEqualTo(bidSave);
        // THEN
        verify(bidListRepository, Mockito.times(1)).save(any(BidList.class));
    }

    @Test
    public void createBid_nullBidParameter_returnBadRequest() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.createBid(null));
        // THEN
        verify(bidListRepository, Mockito.times(0)).save(any(BidList.class));
    }

    @Test
    public void createBid_bidExist_returnBadRequest() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.createBid(bidSave));
        // THEN
        verify(bidListRepository, Mockito.times(0)).save(any(BidList.class));
    }

    // -----------------------------------------------------------------------------------------------
    // GetBidById method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getBidById_findByIdNormal() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        // WHEN
        assertThat(bidListBusiness.getBidById(1)).isEqualTo(bidSave);
        // THEN
        verify(bidListRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void getBidById_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.getBidById(null));
        // THEN
        verify(bidListRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // UpdateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateBid_updateNormal() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        when(bidListRepository.save(bidSave)).thenReturn(bidSave);
        // WHEN
        assertThat(bidListBusiness.updateBid(1, bidSource)).isEqualTo(bidSave);
        // THEN
        verify(bidListRepository, Mockito.times(1)).save(any(BidList.class));
    }

    @Test
    public void updateBid_bidNotExist_returnNotFound() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.updateBid(2, bidSource));
        // THEN
        verify(bidListRepository, Mockito.times(0)).save(any(BidList.class));
    }

    @Test
    public void updateBid_nullIdParameter_returnBadRequest() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.updateBid(null, bidSource));
        // THEN
        verify(bidListRepository, Mockito.times(0)).save(any(BidList.class));
    }

    @Test
    public void updateBid_zeroIdParameter_returnBadRequest() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.updateBid(0, bidSource));
        // THEN
        verify(bidListRepository, Mockito.times(0)).save(any(BidList.class));
    }

    @Test
    public void updateBid_nullBidParameter_returnBadRequest() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.updateBid(1, null));
        // THEN
        verify(bidListRepository, Mockito.times(0)).save(any(BidList.class));
    }

    // -----------------------------------------------------------------------------------------------
    // DeleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void deleteBid_deleteNormal() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidSave));
        doNothing().when(bidListRepository).deleteById(any(Integer.class));
        // WHEN
        bidListBusiness.deleteBid(1);
        // THEN
        verify(bidListRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteBid_bidNotExist_returnNotFound() {
        // GIVEN
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> bidListBusiness.deleteBid(2));
        // THEN
        verify(bidListRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteBid_nullIdParameter_returnBadRequest() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.deleteBid(null));
        // THEN
        verify(bidListRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteBid_zeroIdParameter_returnBadRequest() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> bidListBusiness.deleteBid(0));
        // THEN
        verify(bidListRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}
