-- 2.4.0
ALTER TABLE items RENAME id TO formid;
ALTER TABLE items ADD formdealid BIGINT;
UPDATE items set formdealid = 0;
ALTER TABLE items ALTER formdealid SET NOT NULL ;

ALTER TABLE items DROP CONSTRAINT items_pkey;
ALTER TABLE items ADD PRIMARY KEY (formid, transactionid, formdealid);
