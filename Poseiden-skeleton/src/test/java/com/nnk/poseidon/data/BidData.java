package com.nnk.poseidon.data;

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

/**
 * BidData is the class containing the bid test data
 *
 * @author MC
 * @version 1.0
 */
public class BidData {

    public static Bid getBidSource() {
        return Bid.builder()
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .askQuantity(11d)
                .bid(12d)
                .ask(13d)
                .benchmark("Benchmark Test")
                .bidListDate(GlobalData.CURRENT_TIMESTAMP)
                .commentary("Commentary Test")
                .security("Security Test")
                .status("Status")
                .trader("Trader Test")
                .book("Book Test")
                .creationName("CreationName Test")
                .creationDate(GlobalData.CURRENT_TIMESTAMP)
                .revisionName("RevisionName Test")
                .revisionDate(GlobalData.CURRENT_TIMESTAMP)
                .dealName("DealName Test")
                .dealType("DealType Test")
                .sourceListId("SourceListId Test")
                .side("Side Test")
                .build();
    }

    public static Bid getBidSave() {
        Bid bid = getBidSource();
        bid.setBidListId(1);
        return bid;
    }

    public static MultiValueMap<String, String> getBidSourceController() {
        Bid bid = Bid.builder()
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .build();
        return MultiValueMapMapper.convert(bid);
    }

    public static MultiValueMap<String, String> getBidSaveController() {
        Bid bid = Bid.builder()
                .bidListId(1)
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .build();
        return MultiValueMapMapper.convert(bid);
    }

    public final static String scriptCreateBid = "/data/createBid.sql";
}
