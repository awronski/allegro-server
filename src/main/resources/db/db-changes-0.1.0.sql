-- 0.1.0
CREATE USER alle;
ALTER ROLE alle PASSWORD 'haslo';
CREATE DATABASE alledb OWNER alle ENCODING = 'UTF-8';


DROP TABLE IF EXISTS auctions;
CREATE TABLE auctions (
  id              BIGINT NOT NULL PRIMARY KEY,
  title           VARCHAR(64) NOT NULL,
  thumbnailUrl    VARCHAR(128),
  startQuantity   INTEGER NOT NULL,
  soldQuantity    INTEGER NOT NULL,
  quantityType    VARCHAR(5) NOT NULL,
  startTime       TIMESTAMP NOT NULL,
  endTime         TIMESTAMP,
  biddersCounter  INTEGER NOT NULL,
  categoryId      INTEGER NOT NULL,
  watchersCounter INTEGER NOT NULL,
  viewsCounter    INTEGER NOT NULL,
  note            VARCHAR(1024),
  special         BOOL NOT NULL,
  shop            BOOL NOT NULL,
  payu            BOOL NOT NULL,
  price           DOUBLE PRECISION NOT NULL,
  priceType       VARCHAR(8) NOT NULL
);
ALTER TABLE auctions OWNER TO alle;

DROP TABLE IF EXISTS auctions_statuses;
CREATE TABLE auctions_statuses (
  itemId      BIGINT NOT NULL PRIMARY KEY REFERENCES auctions(id),
  ref         VARCHAR(8) NOT NULL,
  status      VARCHAR(6) NOT NULL
);
ALTER TABLE auctions_statuses OWNER TO alle;

DROP TABLE IF EXISTS journals;
CREATE TABLE journals (
  rowId         BIGINT NOT NULL PRIMARY KEY,
  itemId        BIGINT NOT NULL,
  changeType    VARCHAR(10) NOT NULL,
  changeDate    TIMESTAMP NOt NULL,
  currentPrice  DOUBLE PRECISION
);
ALTER TABLE journals OWNER TO alle;

DROP TABLE IF EXISTS deals;
CREATE TABLE deals (
  eventId         BIGINT NOT NULL PRIMARY KEY,
  dealType        VARCHAR(12) NOT NULL,
  eventTime       TIMESTAMP NOT NULL,
  dealId          BIGINT NOT NULL,
  transactionId   BIGINT,
  sellerId        INTEGER NOT NULL,
  itemId          BIGINT NOT NULL,
  buyerId         INTEGER NOT NULL,
  quantity        INTEGER NOT NULL
);
ALTER TABLE deals OWNER TO alle;

DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses (
  id        SERIAL NOT NULL PRIMARY KEY,
  country   VARCHAR(32) NOT NULL,
  street    VARCHAR(128) NOT NULL,
  code      VARCHAR(16) NOT NULL,
  city      VARCHAR(64) NOT NULL,
  fullname  VARCHAR(128) NOT NULL,
  company   VARCHAR(256),
  phone     VARCHAR(32) NOT NULL,
  nip       VARCHAR(32)
);
ALTER TABLE addresses OWNER TO alle;

DROP TABLE IF EXISTS postbuyforms;
CREATE TABLE postbuyforms (
  transactionId BIGINT NOT NULL PRIMARY KEY,
  buyerId       BIGINT NOT NULL,
  email         VARCHAR(128) NOT NULL,
  "date"        TIMESTAMP NOT NULL,
  amount        DOUBLE PRECISION NOT NULL,
  postageAmount DOUBLE PRECISION NOT NULL,
  paymentAmount DOUBLE PRECISION NOT NULL,
  withInvoice   BOOL NOT NULL,
  msg           TEXT,
  payId         BIGINT NOT NULL,
  payStatus     VARCHAR(32) NOT NULL,
  shipment      VARCHAR(96) NOT NULL,
  orderer_id    BIGINT REFERENCES addresses(id),
  receiver_id   BIGINT REFERENCES addresses(id) NOT NULL
);
ALTER TABLE postbuyforms OWNER TO alle;

DROP TABLE IF EXISTS items;
CREATE TABLE items (
  id            BIGINT NOT NULL PRIMARY KEY,
  transactionId BIGINT NOT NULL,
  title         VARCHAR(64) NOT NULL,
  price         DOUBLE PRECISION NOT NULL,
  quantity      INTEGER NOT NULL,
  amount        DOUBLE PRECISION NOT NULL
);
ALTER TABLE items OWNER TO alle;

DROP TABLE IF EXISTS payments_processed;
CREATE TABLE payments_processed (
  transactionId   BIGINT NOT NULL PRIMARY KEY REFERENCES postbuyforms(transactionId),
  "date"          TIMESTAMP NOT NULL,
  ref             VARCHAR(16) NOT NULL
);
ALTER TABLE payments_processed OWNER TO alle;