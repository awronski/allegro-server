-- 0.1.0
CREATE USER alle;
ALTER ROLE alle PASSWORD 'haslo';
CREATE DATABASE alledb OWNER alle ENCODING = 'UTF-8';

DROP TABLE IF EXISTS auctions;
CREATE TABLE auctions (
  itemId              BIGINT NOT NULL PRIMARY KEY,
  itemTitle           VARCHAR(64) NOT NULL,
  itemThumbnailUrl    VARCHAR(128),
  itemStartQuantity   TIMESTAMP,
  itemSoldQuantity    INTEGER,
  itemQuantityType    INTEGER,
  itemStartTime       TIMESTAMP,
  itemEndTime         TIMESTAMP,
  itemBiddersCounter  INTEGER,
  itemCategoryId      INTEGER,
  itemWatchersCounter INTEGER,
  itemViewsCounter    INTEGER,
  itemNote            VARCHAR(1024),
  special             BOOL,
  shop                BOOL,
  payu                BOOL
);
ALTER TABLE auction OWNER TO alle;