-- 2.1.0
ALTER TABLE items DROP CONSTRAINT items_transactionid_fkey;
ALTER TABLE items ADD sellerid bigint;
UPDATE items i set sellerid=(select sellerid from payments p where i.transactionid=p.transactionid);
ALTER TABLE items ALTER sellerid SET NOT NULL;

ALTER TABLE payments_processed DROP CONSTRAINT payments_processed_transactionid_fkey;
ALTER TABLE payments_processed ADD sellerid bigint;
UPDATE payments_processed i set sellerid=(select sellerid from payments p where i.transactionid=p.transactionid);
ALTER TABLE payments_processed ALTER sellerid SET NOT NULL;

ALTER TABLE payments DROP CONSTRAINT payments_pkey;
ALTER TABLE payments ADD PRIMARY KEY (transactionid, sellerid);

ALTER TABLE items ADD CONSTRAINT payments_transactionid_sellerid FOREIGN KEY (transactionid, sellerid) REFERENCES payments(transactionid, sellerid);
ALTER TABLE payments_processed ADD CONSTRAINT payments_transactionid_sellerid FOREIGN KEY (transactionid, sellerid) REFERENCES payments(transactionid, sellerid);

ALTER TABLE payments_processed DROP CONSTRAINT payments_processed_pkey;
ALTER TABLE payments_processed ADD PRIMARY KEY (transactionid, sellerid);

ALTER TABLE incoming_payments DROP CONSTRAINT incoming_payments_pkey;
ALTER TABLE incoming_payments ADD PRIMARY KEY (transactionid, sellerid);