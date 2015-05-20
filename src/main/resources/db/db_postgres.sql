--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.4
-- Dumped by pg_dump version 9.2.4
-- Started on 2015-05-19 11:45:24

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 178 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 178
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 168 (class 1259 OID 1043138)
-- Name: addresses; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE addresses (
    id integer NOT NULL,
    street character varying(128) NOT NULL,
    code character varying(16) NOT NULL,
    city character varying(64) NOT NULL,
    fullname character varying(128) NOT NULL,
    company character varying(256),
    phone character varying(32),
    nip character varying(32),
    country character varying(32) NOT NULL
);


ALTER TABLE public.addresses OWNER TO alle;

--
-- TOC entry 169 (class 1259 OID 1043144)
-- Name: addresses_id_seq; Type: SEQUENCE; Schema: public; Owner: alle
--

CREATE SEQUENCE addresses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.addresses_id_seq OWNER TO alle;

--
-- TOC entry 1984 (class 0 OID 0)
-- Dependencies: 169
-- Name: addresses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alle
--

ALTER SEQUENCE addresses_id_seq OWNED BY addresses.id;


--
-- TOC entry 170 (class 1259 OID 1043146)
-- Name: auctions; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE auctions (
    id bigint NOT NULL,
    title character varying(64) NOT NULL,
    thumbnailurl character varying(128),
    startquantity integer NOT NULL,
    soldquantity integer NOT NULL,
    quantitytype character varying(5) NOT NULL,
    starttime timestamp without time zone NOT NULL,
    endtime timestamp without time zone,
    bidderscounter integer NOT NULL,
    categoryid integer NOT NULL,
    watcherscounter integer NOT NULL,
    viewscounter integer NOT NULL,
    note character varying(1024),
    special boolean NOT NULL,
    shop boolean NOT NULL,
    payu boolean NOT NULL,
    price double precision NOT NULL,
    pricetype character varying(8) NOT NULL,
    open boolean NOT NULL,
    sellerid bigint NOT NULL
);


ALTER TABLE public.auctions OWNER TO alle;

--
-- TOC entry 177 (class 1259 OID 1399845)
-- Name: clients; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE clients (
    clientid bigint NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(64) NOT NULL,
    key character varying(64) NOT NULL
);


ALTER TABLE public.clients OWNER TO alle;

--
-- TOC entry 171 (class 1259 OID 1043152)
-- Name: deals; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE deals (
    eventid bigint NOT NULL,
    dealtype character varying(12) NOT NULL,
    eventtime timestamp without time zone NOT NULL,
    dealid bigint NOT NULL,
    transactionid bigint,
    sellerid bigint NOT NULL,
    itemid bigint NOT NULL,
    buyerid integer NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE public.deals OWNER TO alle;

--
-- TOC entry 172 (class 1259 OID 1043155)
-- Name: items; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE items (
    id bigint NOT NULL,
    transactionid bigint NOT NULL,
    title character varying(64) NOT NULL,
    price double precision NOT NULL,
    quantity integer NOT NULL,
    amount double precision NOT NULL
);


ALTER TABLE public.items OWNER TO alle;

--
-- TOC entry 173 (class 1259 OID 1043158)
-- Name: journals; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE journals (
    rowid bigint NOT NULL,
    itemid bigint NOT NULL,
    changetype character varying(11) NOT NULL,
    changedate timestamp without time zone NOT NULL,
    currentprice double precision,
    sellerid bigint NOT NULL
);


ALTER TABLE public.journals OWNER TO alle;

--
-- TOC entry 176 (class 1259 OID 1395537)
-- Name: last_processed_deal; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE last_processed_deal (
    eventid bigint NOT NULL,
    sellerid bigint NOT NULL
);


ALTER TABLE public.last_processed_deal OWNER TO alle;

--
-- TOC entry 174 (class 1259 OID 1043161)
-- Name: payments; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE payments (
    transactionid bigint NOT NULL,
    buyerid bigint NOT NULL,
    email character varying(128) NOT NULL,
    date timestamp without time zone NOT NULL,
    amount double precision NOT NULL,
    postageamount double precision NOT NULL,
    paymentamount double precision NOT NULL,
    withinvoice boolean NOT NULL,
    msg text,
    payid bigint NOT NULL,
    paystatus character varying(32) NOT NULL,
    shipment character varying(96) NOT NULL,
    processed boolean NOT NULL,
    orderer_id bigint,
    receiver_id bigint NOT NULL,
    sellerid bigint NOT NULL
);


ALTER TABLE public.payments OWNER TO alle;

--
-- TOC entry 175 (class 1259 OID 1043167)
-- Name: payments_processed; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE payments_processed (
    transactionid bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    ref character varying(16) NOT NULL
);


ALTER TABLE public.payments_processed OWNER TO alle;

--
-- TOC entry 1951 (class 2604 OID 1307546)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alle
--

ALTER TABLE ONLY addresses ALTER COLUMN id SET DEFAULT nextval('addresses_id_seq'::regclass);


--
-- TOC entry 1953 (class 2606 OID 1043172)
-- Name: addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id);


--
-- TOC entry 1955 (class 2606 OID 1043174)
-- Name: auctions_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_pkey PRIMARY KEY (id);


--
-- TOC entry 1969 (class 2606 OID 1399849)
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (clientid);


--
-- TOC entry 1971 (class 2606 OID 1399851)
-- Name: clients_username_key; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_username_key UNIQUE (username);


--
-- TOC entry 1957 (class 2606 OID 1043176)
-- Name: deals_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY deals
    ADD CONSTRAINT deals_pkey PRIMARY KEY (eventid);


--
-- TOC entry 1959 (class 2606 OID 1043178)
-- Name: items_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id, transactionid);


--
-- TOC entry 1961 (class 2606 OID 1043180)
-- Name: journals_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY journals
    ADD CONSTRAINT journals_pkey PRIMARY KEY (rowid);


--
-- TOC entry 1967 (class 2606 OID 1399857)
-- Name: last_processed_deal_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY last_processed_deal
    ADD CONSTRAINT last_processed_deal_pkey PRIMARY KEY (sellerid);


--
-- TOC entry 1963 (class 2606 OID 1043182)
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (transactionid);


--
-- TOC entry 1965 (class 2606 OID 1043184)
-- Name: payments_processed_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY payments_processed
    ADD CONSTRAINT payments_processed_pkey PRIMARY KEY (transactionid);


--
-- TOC entry 1972 (class 2606 OID 1043185)
-- Name: items_transactionid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alle
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_transactionid_fkey FOREIGN KEY (transactionid) REFERENCES payments(transactionid);


--
-- TOC entry 1973 (class 2606 OID 1043190)
-- Name: payments_orderer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alle
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_orderer_id_fkey FOREIGN KEY (orderer_id) REFERENCES addresses(id);


--
-- TOC entry 1975 (class 2606 OID 1043195)
-- Name: payments_processed_transactionid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alle
--

ALTER TABLE ONLY payments_processed
    ADD CONSTRAINT payments_processed_transactionid_fkey FOREIGN KEY (transactionid) REFERENCES payments(transactionid);


--
-- TOC entry 1974 (class 2606 OID 1043200)
-- Name: payments_receiver_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alle
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_receiver_id_fkey FOREIGN KEY (receiver_id) REFERENCES addresses(id);


--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-05-19 11:45:25

--
-- PostgreSQL database dump complete
--

