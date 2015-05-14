-- 1.1.0
ALTER TABLE auctions ADD open BOOL;
UPDATE auctions SET open=true;
ALTER TABLE auctions ALTER open SET NOT NULL;

DROP TABLE IF EXISTS auctions_statuses;



ALTER TABLE addresses ALTER phone DROP NOT NULL ;

CREATE TABLE last_processed_deal (
  eventid BIGINT PRIMARY KEY
);
ALTER TABLE last_processed_deal OWNER TO alle;
