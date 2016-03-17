-- 2.4.0
ALTER TABLE auctions ADD extraOptions INTEGER;
UPDATE auctions set extraOptions = 2;                   --thumb
ALTER TABLE auctions ALTER extraOptions SET NOT NULL ;
