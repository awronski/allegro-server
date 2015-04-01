-- 1.1.0
ALTER TABLE auctions ADD open BOOL;
UPDATE auctions SET open=true;
ALTER TABLE auctions ALTER open SET NOT NULL;

DROP TABLE IF EXISTS auctions_statuses;