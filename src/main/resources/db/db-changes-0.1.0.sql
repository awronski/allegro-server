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