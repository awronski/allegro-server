-- 0.1.0
CREATE USER alle;
ALTER ROLE alle PASSWORD 'haslo';
CREATE DATABASE alledb OWNER alle ENCODING = 'UTF-8';


DROP TABLE IF EXISTS auctions;
CREATE TABLE auctions (
  itemId              BIGINT NOT NULL PRIMARY KEY,
  itemTitle           VARCHAR(64) NOT NULL,
  itemThumbnailUrl    VARCHAR(128),
  itemStartQuantity   INTEGER NOT NULL,
  itemSoldQuantity    INTEGER NOT NULL,
  itemQuantityType    VARCHAR(5) NOT NULL,
  itemStartTime       TIMESTAMP NOT NULL,
  itemEndTime         TIMESTAMP,
  itemBiddersCounter  INTEGER NOT NULL,
  itemCategoryId      INTEGER NOT NULL,
  itemWatchersCounter INTEGER NOT NULL,
  itemViewsCounter    INTEGER NOT NULL,
  itemNote            VARCHAR(1024),
  special             BOOL NOT NULL,
  shop                BOOL NOT NULL,
  payu                BOOL NOT NULL,
  price               DOUBLE PRECISION NOT NULL,
  priceType           VARCHAR(8) NOT NULL
);
ALTER TABLE auctions OWNER TO alle;


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
  countryId INTEGER NOT NULL,
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
  amount        DOUBLE PRECISION NOT NULL,
  postageAmount DOUBLE PRECISION NOT NULL,
  paymentAmount DOUBLE PRECISION NOT NULL,
  withInvoice   BOOL NOT NULL,
  msg           TEXT,
  payId         BIGINT NOT NULL,
  payStatus     VARCHAR(32) NOT NULL,
  shipmentId    INTEGER NOT NULL,
  orderer_id    BIGINT REFERENCES addresses(id),
  receiver_id   BIGINT REFERENCES addresses(id) NOT NULL
);
ALTER TABLE postbuyforms OWNER TO alle;
