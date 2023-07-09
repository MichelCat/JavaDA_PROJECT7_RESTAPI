package com.nnk.springboot.business;

import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListBusiness {

    @Autowired
    private BidListRepository bidListRepository;

}
