package com.apwglobal.allegro.server.db;

import com.apwglobal.nice.domain.Auction;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuctionMapper {

    @Select("select * from auctions")
    List<Auction> getAllAuctions();

}
