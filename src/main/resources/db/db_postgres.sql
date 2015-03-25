SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 175 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 175
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 957214)
-- Name: addresses; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE addresses (
    id integer NOT NULL,
    street character varying(128) NOT NULL,
    code character varying(16) NOT NULL,
    city character varying(64) NOT NULL,
    fullname character varying(128) NOT NULL,
    company character varying(256),
    phone character varying(32) NOT NULL,
    nip character varying(32),
    country character varying(32) NOT NULL
);


ALTER TABLE public.addresses OWNER TO alle;

--
-- TOC entry 170 (class 1259 OID 957212)
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
-- TOC entry 1962 (class 0 OID 0)
-- Dependencies: 170
-- Name: addresses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alle
--

ALTER SEQUENCE addresses_id_seq OWNED BY addresses.id;


--
-- TOC entry 168 (class 1259 OID 957188)
-- Name: auctions; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE auctions (
    itemid bigint NOT NULL,
    itemtitle character varying(64) NOT NULL,
    itemthumbnailurl character varying(128),
    itemstartquantity integer NOT NULL,
    itemsoldquantity integer NOT NULL,
    itemquantitytype character varying(5) NOT NULL,
    itemstarttime timestamp without time zone NOT NULL,
    itemendtime timestamp without time zone,
    itembidderscounter integer NOT NULL,
    itemcategoryid integer NOT NULL,
    itemwatcherscounter integer NOT NULL,
    itemviewscounter integer NOT NULL,
    itemnote character varying(1024),
    special boolean NOT NULL,
    shop boolean NOT NULL,
    payu boolean NOT NULL,
    price double precision NOT NULL,
    pricetype character varying(8) NOT NULL
);


ALTER TABLE public.auctions OWNER TO alle;

--
-- TOC entry 173 (class 1259 OID 957259)
-- Name: deals; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE deals (
    eventid bigint NOT NULL,
    dealtype character varying(12) NOT NULL,
    eventtime timestamp without time zone NOT NULL,
    dealid bigint NOT NULL,
    transactionid bigint,
    sellerid integer NOT NULL,
    itemid bigint NOT NULL,
    buyerid integer NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE public.deals OWNER TO alle;

--
-- TOC entry 174 (class 1259 OID 957264)
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
-- TOC entry 169 (class 1259 OID 957201)
-- Name: journals; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE journals (
    rowid bigint NOT NULL,
    itemid bigint NOT NULL,
    changetype character varying(11) NOT NULL,
    changedate timestamp without time zone NOT NULL,
    currentprice double precision
);


ALTER TABLE public.journals OWNER TO alle;

--
-- TOC entry 172 (class 1259 OID 957241)
-- Name: postbuyforms; Type: TABLE; Schema: public; Owner: alle; Tablespace: 
--

CREATE TABLE postbuyforms (
    transactionid bigint NOT NULL,
    buyerid bigint NOT NULL,
    email character varying(128) NOT NULL,
    amount double precision NOT NULL,
    postageamount double precision NOT NULL,
    paymentamount double precision NOT NULL,
    withinvoice boolean NOT NULL,
    msg text,
    payid bigint NOT NULL,
    paystatus character varying(32) NOT NULL,
    shipmentid integer NOT NULL,
    orderer_id bigint,
    receiver_id bigint NOT NULL
);


ALTER TABLE public.postbuyforms OWNER TO alle;

--
-- TOC entry 1939 (class 2604 OID 957217)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alle
--

ALTER TABLE ONLY addresses ALTER COLUMN id SET DEFAULT nextval('addresses_id_seq'::regclass);


--
-- TOC entry 1945 (class 2606 OID 957222)
-- Name: addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id);


--
-- TOC entry 1941 (class 2606 OID 957195)
-- Name: auctions_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_pkey PRIMARY KEY (itemid);


--
-- TOC entry 1949 (class 2606 OID 957263)
-- Name: deals_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY deals
    ADD CONSTRAINT deals_pkey PRIMARY KEY (eventid);


--
-- TOC entry 1951 (class 2606 OID 957268)
-- Name: items_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);


--
-- TOC entry 1943 (class 2606 OID 957205)
-- Name: journals_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY journals
    ADD CONSTRAINT journals_pkey PRIMARY KEY (rowid);


--
-- TOC entry 1947 (class 2606 OID 957248)
-- Name: postbuyforms_pkey; Type: CONSTRAINT; Schema: public; Owner: alle; Tablespace: 
--

ALTER TABLE ONLY postbuyforms
    ADD CONSTRAINT postbuyforms_pkey PRIMARY KEY (transactionid);


--
-- TOC entry 1952 (class 2606 OID 957249)
-- Name: postbuyforms_orderer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alle
--

ALTER TABLE ONLY postbuyforms
    ADD CONSTRAINT postbuyforms_orderer_id_fkey FOREIGN KEY (orderer_id) REFERENCES addresses(id);


--
-- TOC entry 1953 (class 2606 OID 957254)
-- Name: postbuyforms_receiver_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alle
--

ALTER TABLE ONLY postbuyforms
    ADD CONSTRAINT postbuyforms_receiver_id_fkey FOREIGN KEY (receiver_id) REFERENCES addresses(id);




