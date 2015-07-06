-- 2.0.0
-- Converting from single to multi user server
-- XX - your allegro client id

CREATE TABLE clients (
  clientId BIGINT PRIMARY KEY,
  username VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  key      VARCHAR(64) NOT NULL
);
ALTER TABLE clients OWNER TO alle;

ALTER TABLE auctions ADD sellerId BIGINT;
--UPDATE auctions SET sellerId=XX;
ALTER TABLE auctions ALTER sellerId SET NOT NULL;

ALTER TABLE journals ADD sellerId BIGINT;
--UPDATE journals SET sellerId=XX;
ALTER TABLE journals ALTER sellerId SET NOT NULL;

ALTER TABLE payments ADD sellerId BIGINT;
--UPDATE payments SET sellerId=XX;
ALTER TABLE payments ALTER sellerId SET NOT NULL;

ALTER TABLE last_processed_deal ADD sellerId BIGINT;
--UPDATE last_processed_deal SET sellerId=XX;
ALTER TABLE last_processed_deal ALTER sellerId SET NOT NULL;
ALTER TABLE last_processed_deal DROP CONSTRAINT last_processed_deal_pkey;
ALTER TABLE last_processed_deal ADD PRIMARY KEY (sellerid);

ALTER TABLE deals ALTER sellerid TYPE BIGINT;



-- incoming payments
CREATE TABLE incoming_payments (
  transactionId BIGINT PRIMARY KEY,
  sellerId BIGINT NOT NULL,
  buyerId BIGINT NOT NULL,
  status VARCHAR(16) NOT NULL,
  amount double precision NOT NULL,
  receiveDate TIMESTAMP NOT NULL,
  price double precision NOT NULL,
  postageAmount double precision NOT NULL,
  itemsCounter INTEGER NOT NULL,
  incomplete BOOLEAN NOT NULL,
  mainTransactionId BIGINT
);
ALTER TABLE incoming_payments OWNER TO alle;


CREATE TABLE last_processed_journal (
  eventid bigint NOT NULL,
  sellerid bigint NOT NULL,
  CONSTRAINT last_processed_journal_pkey PRIMARY KEY (sellerid)
);
ALTER TABLE last_processed_journal OWNER TO alle;
