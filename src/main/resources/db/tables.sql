-- This script was generated by a beta version of the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE public.card_orders
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    type character varying(20) NOT NULL,
    customer_id integer NOT NULL,
    created_at date NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.cards
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    card_number character varying(16) NOT NULL,
    expiry_date date NOT NULL,
    customer_id integer NOT NULL,
    balance double precision NOT NULL,
    is_blocked boolean NOT NULL,
    is_expired boolean NOT NULL,
    card_type character varying(10),
    PRIMARY KEY (id)
);

CREATE TABLE public.customer_phone_numbers
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    phone_number character varying(25) NOT NULL,
    is_registered boolean NOT NULL,
    message_code character varying(6),
    is_message_code_allowed boolean,
    is_signup_allowed boolean,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    birth_date date NOT NULL,
    message_code_attempt integer,
    reset_password_message_code character varying(6),
    is_reset_password_message_code_allowed boolean,
    reset_password_message_code_attempt integer,
    is_password_reset_allowed boolean,
    PRIMARY KEY (id)
);

CREATE TABLE public.customers
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    first_name character varying(50),
    last_name character varying(50),
    email character varying(80) NOT NULL,
    birth_date date,
    city character varying(50),
    address character varying(50),
    zip character varying(15),
    phone_number character varying(50),
    password character varying(100),
    role_id integer,
    PRIMARY KEY (id)
);

CREATE TABLE public.loan_notifications
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    amount double precision NOT NULL,
    due_to date NOT NULL,
    interest_rate integer NOT NULL,
    customer_id integer NOT NULL,
    type character varying NOT NULL,
    is_paid boolean NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.loan_requests
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    type character varying(20) NOT NULL,
    amount double precision NOT NULL,
    period integer NOT NULL,
    customer_id integer NOT NULL,
    status character varying(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.roles
(
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.transactions
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    amount double precision NOT NULL,
    type character varying(20) NOT NULL,
    description character varying(50),
    created_at_date date NOT NULL,
    created_at_time time without time zone NOT NULL,
    customer_id integer,
    PRIMARY KEY (id)
);

ALTER TABLE public.card_orders
    ADD FOREIGN KEY (customer_id)
    REFERENCES public.customers (id)
    NOT VALID;


ALTER TABLE public.cards
    ADD FOREIGN KEY (customer_id)
    REFERENCES public.customers (id)
    NOT VALID;


ALTER TABLE public.customers
    ADD FOREIGN KEY (role_id)
    REFERENCES public.roles (id)
    NOT VALID;


ALTER TABLE public.loan_notifications
    ADD FOREIGN KEY (customer_id)
    REFERENCES public.customers (id)
    NOT VALID;


ALTER TABLE public.loan_requests
    ADD FOREIGN KEY (customer_id)
    REFERENCES public.customers (id)
    NOT VALID;


ALTER TABLE public.transactions
    ADD FOREIGN KEY (customer_id)
    REFERENCES public.customers (id)
    NOT VALID;

END;