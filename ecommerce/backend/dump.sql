--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: API_address; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_address" (
    id integer NOT NULL,
    address character varying(255) NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_address_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_address_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_address_id_seq" OWNED BY public."API_address".id;


--
-- Name: API_admin; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_admin" (
    id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_admin_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_admin_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_admin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_admin_id_seq" OWNED BY public."API_admin".id;


--
-- Name: API_brand; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_brand" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


--
-- Name: API_brand_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_brand_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_brand_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_brand_id_seq" OWNED BY public."API_brand".id;


--
-- Name: API_category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_category" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


--
-- Name: API_category_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_category_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_category_id_seq" OWNED BY public."API_category".id;


--
-- Name: API_comment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_comment" (
    id integer NOT NULL,
    comment character varying(255) NOT NULL,
    comment_date timestamp with time zone NOT NULL,
    product_id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_comment_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_comment_id_seq" OWNED BY public."API_comment".id;


--
-- Name: API_customer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_customer" (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_customer_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_customer_id_seq" OWNED BY public."API_customer".id;


--
-- Name: API_product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_product" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    price integer NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    image_url character varying(255) NOT NULL,
    total_rating integer NOT NULL,
    rating_count integer NOT NULL,
    stock_amount integer NOT NULL,
    description character varying(255) NOT NULL,
    brand_id integer NOT NULL,
    subcategory_id integer NOT NULL,
    vendor_id integer NOT NULL
);


--
-- Name: API_product_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_product_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_product_id_seq" OWNED BY public."API_product".id;


--
-- Name: API_productlist; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_productlist" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_productlist_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_productlist_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_productlist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_productlist_id_seq" OWNED BY public."API_productlist".id;


--
-- Name: API_productlistitem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_productlistitem" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    product_id integer NOT NULL,
    product_list_id integer NOT NULL
);


--
-- Name: API_productlistitem_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_productlistitem_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_productlistitem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_productlistitem_id_seq" OWNED BY public."API_productlistitem".id;


--
-- Name: API_purchase; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_purchase" (
    id integer NOT NULL,
    amount integer NOT NULL,
    unit_price double precision NOT NULL,
    name character varying(255) NOT NULL,
    status integer NOT NULL,
    purchase_date timestamp with time zone NOT NULL,
    address_id integer NOT NULL,
    product_id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_purchase_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_purchase_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_purchase_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_purchase_id_seq" OWNED BY public."API_purchase".id;


--
-- Name: API_rating; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_rating" (
    id integer NOT NULL,
    rating integer NOT NULL,
    product_id integer NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_rating_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_rating_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_rating_id_seq" OWNED BY public."API_rating".id;


--
-- Name: API_shoppingcartitem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_shoppingcartitem" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    product_id integer NOT NULL,
    amount integer NOT NULL,
    customer_id integer NOT NULL
);


--
-- Name: API_shoppingcartitem_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_shoppingcartitem_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_shoppingcartitem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_shoppingcartitem_id_seq" OWNED BY public."API_shoppingcartitem".id;


--
-- Name: API_subcategory; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_subcategory" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    category_id integer NOT NULL
);


--
-- Name: API_subcategory_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_subcategory_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_subcategory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_subcategory_id_seq" OWNED BY public."API_subcategory".id;


--
-- Name: API_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_user" (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    password_salt character varying(255) NOT NULL,
    register_date timestamp with time zone NOT NULL,
    google_register boolean NOT NULL,
    username character varying(255) NOT NULL,
    is_verified boolean NOT NULL,
    role integer NOT NULL
);


--
-- Name: API_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_user_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_user_id_seq" OWNED BY public."API_user".id;


--
-- Name: API_vendor; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."API_vendor" (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    user_id integer NOT NULL
);


--
-- Name: API_vendor_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."API_vendor_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: API_vendor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."API_vendor_id_seq" OWNED BY public."API_vendor".id;


--
-- Name: auth_group; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_group (
    id integer NOT NULL,
    name character varying(150) NOT NULL
);


--
-- Name: auth_group_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.auth_group_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: auth_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.auth_group_id_seq OWNED BY public.auth_group.id;


--
-- Name: auth_group_permissions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_group_permissions (
    id integer NOT NULL,
    group_id integer NOT NULL,
    permission_id integer NOT NULL
);


--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.auth_group_permissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.auth_group_permissions_id_seq OWNED BY public.auth_group_permissions.id;


--
-- Name: auth_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_permission (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    content_type_id integer NOT NULL,
    codename character varying(100) NOT NULL
);


--
-- Name: auth_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.auth_permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: auth_permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.auth_permission_id_seq OWNED BY public.auth_permission.id;


--
-- Name: auth_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_user (
    id integer NOT NULL,
    password character varying(128) NOT NULL,
    last_login timestamp with time zone,
    is_superuser boolean NOT NULL,
    username character varying(150) NOT NULL,
    first_name character varying(150) NOT NULL,
    last_name character varying(150) NOT NULL,
    email character varying(254) NOT NULL,
    is_staff boolean NOT NULL,
    is_active boolean NOT NULL,
    date_joined timestamp with time zone NOT NULL
);


--
-- Name: auth_user_groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_user_groups (
    id integer NOT NULL,
    user_id integer NOT NULL,
    group_id integer NOT NULL
);


--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.auth_user_groups_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.auth_user_groups_id_seq OWNED BY public.auth_user_groups.id;


--
-- Name: auth_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.auth_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: auth_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.auth_user_id_seq OWNED BY public.auth_user.id;


--
-- Name: auth_user_user_permissions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_user_user_permissions (
    id integer NOT NULL,
    user_id integer NOT NULL,
    permission_id integer NOT NULL
);


--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.auth_user_user_permissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.auth_user_user_permissions_id_seq OWNED BY public.auth_user_user_permissions.id;


--
-- Name: django_admin_log; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.django_admin_log (
    id integer NOT NULL,
    action_time timestamp with time zone NOT NULL,
    object_id text,
    object_repr character varying(200) NOT NULL,
    action_flag smallint NOT NULL,
    change_message text NOT NULL,
    content_type_id integer,
    user_id integer NOT NULL,
    CONSTRAINT django_admin_log_action_flag_check CHECK ((action_flag >= 0))
);


--
-- Name: django_admin_log_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.django_admin_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: django_admin_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.django_admin_log_id_seq OWNED BY public.django_admin_log.id;


--
-- Name: django_content_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.django_content_type (
    id integer NOT NULL,
    app_label character varying(100) NOT NULL,
    model character varying(100) NOT NULL
);


--
-- Name: django_content_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.django_content_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: django_content_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.django_content_type_id_seq OWNED BY public.django_content_type.id;


--
-- Name: django_migrations; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.django_migrations (
    id integer NOT NULL,
    app character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    applied timestamp with time zone NOT NULL
);


--
-- Name: django_migrations_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.django_migrations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: django_migrations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.django_migrations_id_seq OWNED BY public.django_migrations.id;


--
-- Name: django_session; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.django_session (
    session_key character varying(40) NOT NULL,
    session_data text NOT NULL,
    expire_date timestamp with time zone NOT NULL
);


--
-- Name: API_address id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_address" ALTER COLUMN id SET DEFAULT nextval('public."API_address_id_seq"'::regclass);


--
-- Name: API_admin id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_admin" ALTER COLUMN id SET DEFAULT nextval('public."API_admin_id_seq"'::regclass);


--
-- Name: API_brand id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_brand" ALTER COLUMN id SET DEFAULT nextval('public."API_brand_id_seq"'::regclass);


--
-- Name: API_category id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_category" ALTER COLUMN id SET DEFAULT nextval('public."API_category_id_seq"'::regclass);


--
-- Name: API_comment id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_comment" ALTER COLUMN id SET DEFAULT nextval('public."API_comment_id_seq"'::regclass);


--
-- Name: API_customer id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_customer" ALTER COLUMN id SET DEFAULT nextval('public."API_customer_id_seq"'::regclass);


--
-- Name: API_product id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_product" ALTER COLUMN id SET DEFAULT nextval('public."API_product_id_seq"'::regclass);


--
-- Name: API_productlist id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlist" ALTER COLUMN id SET DEFAULT nextval('public."API_productlist_id_seq"'::regclass);


--
-- Name: API_productlistitem id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlistitem" ALTER COLUMN id SET DEFAULT nextval('public."API_productlistitem_id_seq"'::regclass);


--
-- Name: API_purchase id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_purchase" ALTER COLUMN id SET DEFAULT nextval('public."API_purchase_id_seq"'::regclass);


--
-- Name: API_rating id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_rating" ALTER COLUMN id SET DEFAULT nextval('public."API_rating_id_seq"'::regclass);


--
-- Name: API_shoppingcartitem id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_shoppingcartitem" ALTER COLUMN id SET DEFAULT nextval('public."API_shoppingcartitem_id_seq"'::regclass);


--
-- Name: API_subcategory id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_subcategory" ALTER COLUMN id SET DEFAULT nextval('public."API_subcategory_id_seq"'::regclass);


--
-- Name: API_user id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_user" ALTER COLUMN id SET DEFAULT nextval('public."API_user_id_seq"'::regclass);


--
-- Name: API_vendor id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_vendor" ALTER COLUMN id SET DEFAULT nextval('public."API_vendor_id_seq"'::regclass);


--
-- Name: auth_group id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group ALTER COLUMN id SET DEFAULT nextval('public.auth_group_id_seq'::regclass);


--
-- Name: auth_group_permissions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group_permissions ALTER COLUMN id SET DEFAULT nextval('public.auth_group_permissions_id_seq'::regclass);


--
-- Name: auth_permission id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_permission ALTER COLUMN id SET DEFAULT nextval('public.auth_permission_id_seq'::regclass);


--
-- Name: auth_user id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user ALTER COLUMN id SET DEFAULT nextval('public.auth_user_id_seq'::regclass);


--
-- Name: auth_user_groups id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_groups ALTER COLUMN id SET DEFAULT nextval('public.auth_user_groups_id_seq'::regclass);


--
-- Name: auth_user_user_permissions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_user_permissions ALTER COLUMN id SET DEFAULT nextval('public.auth_user_user_permissions_id_seq'::regclass);


--
-- Name: django_admin_log id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_admin_log ALTER COLUMN id SET DEFAULT nextval('public.django_admin_log_id_seq'::regclass);


--
-- Name: django_content_type id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_content_type ALTER COLUMN id SET DEFAULT nextval('public.django_content_type_id_seq'::regclass);


--
-- Name: django_migrations id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_migrations ALTER COLUMN id SET DEFAULT nextval('public.django_migrations_id_seq'::regclass);


--
-- Data for Name: API_address; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_address" (id, address, user_id) FROM stdin;
\.


--
-- Data for Name: API_admin; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_admin" (id, user_id) FROM stdin;
\.


--
-- Data for Name: API_brand; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_brand" (id, name) FROM stdin;
1	Mavi
2	Hi5
3	Mosaic
\.


--
-- Data for Name: API_category; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_category" (id, name) FROM stdin;
1	Electronics
2	Health & Households
3	Home & Garden
4	Clothing
5	Hobbies
6	Others
\.


--
-- Data for Name: API_comment; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_comment" (id, comment, comment_date, product_id, user_id) FROM stdin;
\.


--
-- Data for Name: API_customer; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_customer" (id, first_name, last_name, user_id) FROM stdin;
\.


--
-- Data for Name: API_product; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_product" (id, name, price, creation_date, image_url, total_rating, rating_count, stock_amount, description, brand_id, subcategory_id, vendor_id) FROM stdin;
1	Mavi T-shirt	100	2019-08-20 10:22:34+03	image_url1	4	20	10	yaza özel	1	10	1
2	Hi5 Bebek Ayakkabısı	200	2020-08-20 17:13:26+03	image_url2	3	10	20	ferah	2	14	1
3	Mosaic Kamera	3000	2018-08-20 17:13:26+03	image_url3	5	30	10	HD	3	1	1
\.


--
-- Data for Name: API_productlist; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_productlist" (id, name, user_id) FROM stdin;
\.


--
-- Data for Name: API_productlistitem; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_productlistitem" (id, name, product_id, product_list_id) FROM stdin;
\.


--
-- Data for Name: API_purchase; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_purchase" (id, amount, unit_price, name, status, purchase_date, address_id, product_id, user_id) FROM stdin;
\.


--
-- Data for Name: API_rating; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_rating" (id, rating, product_id, user_id) FROM stdin;
\.


--
-- Data for Name: API_shoppingcartitem; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_shoppingcartitem" (id, name, product_id, amount, customer_id) FROM stdin;
1	name1	1	1	2
2	name1	2	2	2
3	name1	3	3	2
4	name2	1	1	3
5	name2	2	1	3
6	name2	3	1	3
\.


--
-- Data for Name: API_subcategory; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_subcategory" (id, name, category_id) FROM stdin;
1	Camera & Photo	1
2	Cell Phones & Accessories	1
3	Digital Videos	1
4	Software	1
5	Sports & Outdoor	2
6	Beauty & Personal Care	2
7	Luggage	3
8	Pet Supplies	3
9	Furniture	3
10	Men's Fashion	4
11	Women's Fashion	4
12	Boys' Fashion	4
13	Girls' Fashion	4
14	Baby	4
15	Books	5
16	Music & CDs	5
17	Movies & TVs	5
18	Toys & Games	5
19	Video Games	5
20	Arts & Crafts	5
21	Automotive	6
22	Industrial & Scientific	6
\.


--
-- Data for Name: API_user; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_user" (id, email, password_hash, password_salt, register_date, google_register, username, is_verified, role) FROM stdin;
2	furkan@gmail.com	P1C4Ed0mHWhCcaqsbXfw2utKSiBuFK8KMWFDJYoGe44=	tVRBpbuocXNPOhf82cJ4M3dlEZpwVfXs	2020-11-20 21:54:00.13177+03	f	furkan	f	1
3	umut@gmail.com	oOxDrcOScR/sOUMrGMKEhkybaVLHKXhM2v0S0YMSA98=	Bt68hCH3VflS3FjEeIKeT5YGTPGrIKTy	2020-11-20 22:05:22.411428+03	f	umut	f	1
4	bekir@gmail.com	s+q3K4wgzhrlQ37ZO/su8PbPsH5SlxOSe8AARg1dfyw=	EofQ0MOSVseqaY1rQhbdaTsxQAzmNvze	2020-11-20 22:22:23.629233+03	f	bekir	f	1
5	burak@gmail.com	2DrBa1nYGBJH7yV5jQxupex5mVowOY+JieK4e83kBX4=	Xu8fJAgM8UCKyfalhqea4BKTqAnUDm6w	2020-11-20 22:22:29.511645+03	f	burak	f	1
1	omer@gmail.com	A79zX+KNvBWOOG0U3lGscsdOEiu14X/snSwAydCpXTw=	j9ukV3EJoRKMUXtAryphbjdUl52Av7qs	2020-11-20 21:47:53.207377+03	f	omer	f	2
\.


--
-- Data for Name: API_vendor; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."API_vendor" (id, first_name, last_name, user_id) FROM stdin;
1	omerfaruk	deniz	1
\.


--
-- Data for Name: auth_group; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.auth_group (id, name) FROM stdin;
\.


--
-- Data for Name: auth_group_permissions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.auth_group_permissions (id, group_id, permission_id) FROM stdin;
\.


--
-- Data for Name: auth_permission; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.auth_permission (id, name, content_type_id, codename) FROM stdin;
1	Can add log entry	1	add_logentry
2	Can change log entry	1	change_logentry
3	Can delete log entry	1	delete_logentry
4	Can view log entry	1	view_logentry
5	Can add permission	2	add_permission
6	Can change permission	2	change_permission
7	Can delete permission	2	delete_permission
8	Can view permission	2	view_permission
9	Can add group	3	add_group
10	Can change group	3	change_group
11	Can delete group	3	delete_group
12	Can view group	3	view_group
13	Can add user	4	add_user
14	Can change user	4	change_user
15	Can delete user	4	delete_user
16	Can view user	4	view_user
17	Can add content type	5	add_contenttype
18	Can change content type	5	change_contenttype
19	Can delete content type	5	delete_contenttype
20	Can view content type	5	view_contenttype
21	Can add session	6	add_session
22	Can change session	6	change_session
23	Can delete session	6	delete_session
24	Can view session	6	view_session
25	Can add address	7	add_address
26	Can change address	7	change_address
27	Can delete address	7	delete_address
28	Can view address	7	view_address
29	Can add brand	8	add_brand
30	Can change brand	8	change_brand
31	Can delete brand	8	delete_brand
32	Can view brand	8	view_brand
33	Can add category	9	add_category
34	Can change category	9	change_category
35	Can delete category	9	delete_category
36	Can view category	9	view_category
37	Can add product	10	add_product
38	Can change product	10	change_product
39	Can delete product	10	delete_product
40	Can view product	10	view_product
41	Can add product list	11	add_productlist
42	Can change product list	11	change_productlist
43	Can delete product list	11	delete_productlist
44	Can view product list	11	view_productlist
45	Can add user	12	add_user
46	Can change user	12	change_user
47	Can delete user	12	delete_user
48	Can view user	12	view_user
49	Can add vendor	13	add_vendor
50	Can change vendor	13	change_vendor
51	Can delete vendor	13	delete_vendor
52	Can view vendor	13	view_vendor
53	Can add subcategory	14	add_subcategory
54	Can change subcategory	14	change_subcategory
55	Can delete subcategory	14	delete_subcategory
56	Can view subcategory	14	view_subcategory
57	Can add shopping cart item	15	add_shoppingcartitem
58	Can change shopping cart item	15	change_shoppingcartitem
59	Can delete shopping cart item	15	delete_shoppingcartitem
60	Can view shopping cart item	15	view_shoppingcartitem
61	Can add rating	16	add_rating
62	Can change rating	16	change_rating
63	Can delete rating	16	delete_rating
64	Can view rating	16	view_rating
65	Can add purchase	17	add_purchase
66	Can change purchase	17	change_purchase
67	Can delete purchase	17	delete_purchase
68	Can view purchase	17	view_purchase
69	Can add product list item	18	add_productlistitem
70	Can change product list item	18	change_productlistitem
71	Can delete product list item	18	delete_productlistitem
72	Can view product list item	18	view_productlistitem
73	Can add customer	19	add_customer
74	Can change customer	19	change_customer
75	Can delete customer	19	delete_customer
76	Can view customer	19	view_customer
77	Can add comment	20	add_comment
78	Can change comment	20	change_comment
79	Can delete comment	20	delete_comment
80	Can view comment	20	view_comment
81	Can add admin	21	add_admin
82	Can change admin	21	change_admin
83	Can delete admin	21	delete_admin
84	Can view admin	21	view_admin
\.


--
-- Data for Name: auth_user; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) FROM stdin;
\.


--
-- Data for Name: auth_user_groups; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.auth_user_groups (id, user_id, group_id) FROM stdin;
\.


--
-- Data for Name: auth_user_user_permissions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.auth_user_user_permissions (id, user_id, permission_id) FROM stdin;
\.


--
-- Data for Name: django_admin_log; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.django_admin_log (id, action_time, object_id, object_repr, action_flag, change_message, content_type_id, user_id) FROM stdin;
\.


--
-- Data for Name: django_content_type; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.django_content_type (id, app_label, model) FROM stdin;
1	admin	logentry
2	auth	permission
3	auth	group
4	auth	user
5	contenttypes	contenttype
6	sessions	session
7	API	address
8	API	brand
9	API	category
10	API	product
11	API	productlist
12	API	user
13	API	vendor
14	API	subcategory
15	API	shoppingcartitem
16	API	rating
17	API	purchase
18	API	productlistitem
19	API	customer
20	API	comment
21	API	admin
\.


--
-- Data for Name: django_migrations; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.django_migrations (id, app, name, applied) FROM stdin;
1	API	0001_initial	2020-11-20 20:45:26.244771+03
2	API	0002_user_is_verified	2020-11-20 20:45:26.330113+03
3	API	0003_user_role	2020-11-20 20:45:26.341158+03
4	contenttypes	0001_initial	2020-11-20 20:45:26.352344+03
5	auth	0001_initial	2020-11-20 20:45:26.38982+03
6	admin	0001_initial	2020-11-20 20:45:26.43613+03
7	admin	0002_logentry_remove_auto_add	2020-11-20 20:45:26.450968+03
8	admin	0003_logentry_add_action_flag_choices	2020-11-20 20:45:26.459843+03
9	contenttypes	0002_remove_content_type_name	2020-11-20 20:45:26.486199+03
10	auth	0002_alter_permission_name_max_length	2020-11-20 20:45:26.494506+03
11	auth	0003_alter_user_email_max_length	2020-11-20 20:45:26.503458+03
12	auth	0004_alter_user_username_opts	2020-11-20 20:45:26.513419+03
13	auth	0005_alter_user_last_login_null	2020-11-20 20:45:26.526316+03
14	auth	0006_require_contenttypes_0002	2020-11-20 20:45:26.529335+03
15	auth	0007_alter_validators_add_error_messages	2020-11-20 20:45:26.542372+03
16	auth	0008_alter_user_username_max_length	2020-11-20 20:45:26.558235+03
17	auth	0009_alter_user_last_name_max_length	2020-11-20 20:45:26.571602+03
18	auth	0010_alter_group_name_max_length	2020-11-20 20:45:26.585888+03
19	auth	0011_update_proxy_permissions	2020-11-20 20:45:26.607889+03
20	auth	0012_alter_user_first_name_max_length	2020-11-20 20:45:26.616108+03
21	sessions	0001_initial	2020-11-20 20:45:26.623543+03
\.


--
-- Data for Name: django_session; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.django_session (session_key, session_data, expire_date) FROM stdin;
\.


--
-- Name: API_address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_address_id_seq"', 1, false);


--
-- Name: API_admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_admin_id_seq"', 1, false);


--
-- Name: API_brand_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_brand_id_seq"', 1, false);


--
-- Name: API_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_category_id_seq"', 1, false);


--
-- Name: API_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_comment_id_seq"', 1, false);


--
-- Name: API_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_customer_id_seq"', 1, false);


--
-- Name: API_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_product_id_seq"', 1, false);


--
-- Name: API_productlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_productlist_id_seq"', 1, false);


--
-- Name: API_productlistitem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_productlistitem_id_seq"', 1, false);


--
-- Name: API_purchase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_purchase_id_seq"', 1, false);


--
-- Name: API_rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_rating_id_seq"', 1, false);


--
-- Name: API_shoppingcartitem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_shoppingcartitem_id_seq"', 1, false);


--
-- Name: API_subcategory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_subcategory_id_seq"', 1, false);


--
-- Name: API_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_user_id_seq"', 5, true);


--
-- Name: API_vendor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."API_vendor_id_seq"', 1, false);


--
-- Name: auth_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_group_id_seq', 1, false);


--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_group_permissions_id_seq', 1, false);


--
-- Name: auth_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_permission_id_seq', 84, true);


--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_user_groups_id_seq', 1, false);


--
-- Name: auth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_user_id_seq', 1, false);


--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_user_user_permissions_id_seq', 1, false);


--
-- Name: django_admin_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.django_admin_log_id_seq', 1, false);


--
-- Name: django_content_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.django_content_type_id_seq', 21, true);


--
-- Name: django_migrations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.django_migrations_id_seq', 21, true);


--
-- Name: API_address API_address_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_address"
    ADD CONSTRAINT "API_address_pkey" PRIMARY KEY (id);


--
-- Name: API_admin API_admin_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_admin"
    ADD CONSTRAINT "API_admin_pkey" PRIMARY KEY (id);


--
-- Name: API_brand API_brand_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_brand"
    ADD CONSTRAINT "API_brand_pkey" PRIMARY KEY (id);


--
-- Name: API_category API_category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_category"
    ADD CONSTRAINT "API_category_pkey" PRIMARY KEY (id);


--
-- Name: API_comment API_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_comment"
    ADD CONSTRAINT "API_comment_pkey" PRIMARY KEY (id);


--
-- Name: API_customer API_customer_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_customer"
    ADD CONSTRAINT "API_customer_pkey" PRIMARY KEY (id);


--
-- Name: API_product API_product_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_product"
    ADD CONSTRAINT "API_product_pkey" PRIMARY KEY (id);


--
-- Name: API_productlist API_productlist_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlist"
    ADD CONSTRAINT "API_productlist_pkey" PRIMARY KEY (id);


--
-- Name: API_productlistitem API_productlistitem_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlistitem"
    ADD CONSTRAINT "API_productlistitem_pkey" PRIMARY KEY (id);


--
-- Name: API_purchase API_purchase_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_purchase"
    ADD CONSTRAINT "API_purchase_pkey" PRIMARY KEY (id);


--
-- Name: API_rating API_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_rating"
    ADD CONSTRAINT "API_rating_pkey" PRIMARY KEY (id);


--
-- Name: API_shoppingcartitem API_shoppingcartitem_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_shoppingcartitem"
    ADD CONSTRAINT "API_shoppingcartitem_pkey" PRIMARY KEY (id);


--
-- Name: API_subcategory API_subcategory_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_subcategory"
    ADD CONSTRAINT "API_subcategory_pkey" PRIMARY KEY (id);


--
-- Name: API_user API_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_user"
    ADD CONSTRAINT "API_user_pkey" PRIMARY KEY (id);


--
-- Name: API_vendor API_vendor_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_vendor"
    ADD CONSTRAINT "API_vendor_pkey" PRIMARY KEY (id);


--
-- Name: auth_group auth_group_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_name_key UNIQUE (name);


--
-- Name: auth_group_permissions auth_group_permissions_group_id_permission_id_0cd325b0_uniq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_permission_id_0cd325b0_uniq UNIQUE (group_id, permission_id);


--
-- Name: auth_group_permissions auth_group_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_group auth_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_pkey PRIMARY KEY (id);


--
-- Name: auth_permission auth_permission_content_type_id_codename_01ab375a_uniq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_codename_01ab375a_uniq UNIQUE (content_type_id, codename);


--
-- Name: auth_permission auth_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups auth_user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups auth_user_groups_user_id_group_id_94350c0c_uniq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_group_id_94350c0c_uniq UNIQUE (user_id, group_id);


--
-- Name: auth_user auth_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions auth_user_user_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions auth_user_user_permissions_user_id_permission_id_14a6b632_uniq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_permission_id_14a6b632_uniq UNIQUE (user_id, permission_id);


--
-- Name: auth_user auth_user_username_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_username_key UNIQUE (username);


--
-- Name: django_admin_log django_admin_log_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_pkey PRIMARY KEY (id);


--
-- Name: django_content_type django_content_type_app_label_model_76bd3d3b_uniq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_content_type
    ADD CONSTRAINT django_content_type_app_label_model_76bd3d3b_uniq UNIQUE (app_label, model);


--
-- Name: django_content_type django_content_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_content_type
    ADD CONSTRAINT django_content_type_pkey PRIMARY KEY (id);


--
-- Name: django_migrations django_migrations_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_migrations
    ADD CONSTRAINT django_migrations_pkey PRIMARY KEY (id);


--
-- Name: django_session django_session_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_session
    ADD CONSTRAINT django_session_pkey PRIMARY KEY (session_key);


--
-- Name: API_address_user_id_d791bd85; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_address_user_id_d791bd85" ON public."API_address" USING btree (user_id);


--
-- Name: API_admin_user_id_d756faf7; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_admin_user_id_d756faf7" ON public."API_admin" USING btree (user_id);


--
-- Name: API_comment_product_id_659fc917; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_comment_product_id_659fc917" ON public."API_comment" USING btree (product_id);


--
-- Name: API_comment_user_id_ec370815; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_comment_user_id_ec370815" ON public."API_comment" USING btree (user_id);


--
-- Name: API_customer_user_id_1d90cfb0; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_customer_user_id_1d90cfb0" ON public."API_customer" USING btree (user_id);


--
-- Name: API_product_brand_id_fa088085; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_product_brand_id_fa088085" ON public."API_product" USING btree (brand_id);


--
-- Name: API_product_subcategory_id_503993c1; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_product_subcategory_id_503993c1" ON public."API_product" USING btree (subcategory_id);


--
-- Name: API_product_vendor_id_a575c65f; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_product_vendor_id_a575c65f" ON public."API_product" USING btree (vendor_id);


--
-- Name: API_productlist_user_id_c793478f; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_productlist_user_id_c793478f" ON public."API_productlist" USING btree (user_id);


--
-- Name: API_productlistitem_product_id_8d48fa75; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_productlistitem_product_id_8d48fa75" ON public."API_productlistitem" USING btree (product_id);


--
-- Name: API_productlistitem_product_list_id_9894a92e; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_productlistitem_product_list_id_9894a92e" ON public."API_productlistitem" USING btree (product_list_id);


--
-- Name: API_purchase_address_id_758414ac; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_purchase_address_id_758414ac" ON public."API_purchase" USING btree (address_id);


--
-- Name: API_purchase_product_id_bd130dfd; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_purchase_product_id_bd130dfd" ON public."API_purchase" USING btree (product_id);


--
-- Name: API_purchase_user_id_2f9ba145; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_purchase_user_id_2f9ba145" ON public."API_purchase" USING btree (user_id);


--
-- Name: API_rating_product_id_3318665f; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_rating_product_id_3318665f" ON public."API_rating" USING btree (product_id);


--
-- Name: API_rating_user_id_c5070fdf; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_rating_user_id_c5070fdf" ON public."API_rating" USING btree (user_id);


--
-- Name: API_shoppingcartitem_customer_id_791fcb5c; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_shoppingcartitem_customer_id_791fcb5c" ON public."API_shoppingcartitem" USING btree (customer_id);


--
-- Name: API_subcategory_category_id_da64e0ba; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_subcategory_category_id_da64e0ba" ON public."API_subcategory" USING btree (category_id);


--
-- Name: API_vendor_user_id_80507f12; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "API_vendor_user_id_80507f12" ON public."API_vendor" USING btree (user_id);


--
-- Name: auth_group_name_a6ea08ec_like; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_group_name_a6ea08ec_like ON public.auth_group USING btree (name varchar_pattern_ops);


--
-- Name: auth_group_permissions_group_id_b120cbf9; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_group_permissions_group_id_b120cbf9 ON public.auth_group_permissions USING btree (group_id);


--
-- Name: auth_group_permissions_permission_id_84c5c92e; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_group_permissions_permission_id_84c5c92e ON public.auth_group_permissions USING btree (permission_id);


--
-- Name: auth_permission_content_type_id_2f476e4b; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_permission_content_type_id_2f476e4b ON public.auth_permission USING btree (content_type_id);


--
-- Name: auth_user_groups_group_id_97559544; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_user_groups_group_id_97559544 ON public.auth_user_groups USING btree (group_id);


--
-- Name: auth_user_groups_user_id_6a12ed8b; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_user_groups_user_id_6a12ed8b ON public.auth_user_groups USING btree (user_id);


--
-- Name: auth_user_user_permissions_permission_id_1fbb5f2c; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_user_user_permissions_permission_id_1fbb5f2c ON public.auth_user_user_permissions USING btree (permission_id);


--
-- Name: auth_user_user_permissions_user_id_a95ead1b; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_user_user_permissions_user_id_a95ead1b ON public.auth_user_user_permissions USING btree (user_id);


--
-- Name: auth_user_username_6821ab7c_like; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX auth_user_username_6821ab7c_like ON public.auth_user USING btree (username varchar_pattern_ops);


--
-- Name: django_admin_log_content_type_id_c4bce8eb; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX django_admin_log_content_type_id_c4bce8eb ON public.django_admin_log USING btree (content_type_id);


--
-- Name: django_admin_log_user_id_c564eba6; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX django_admin_log_user_id_c564eba6 ON public.django_admin_log USING btree (user_id);


--
-- Name: django_session_expire_date_a5c62663; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX django_session_expire_date_a5c62663 ON public.django_session USING btree (expire_date);


--
-- Name: django_session_session_key_c0390e0f_like; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX django_session_session_key_c0390e0f_like ON public.django_session USING btree (session_key varchar_pattern_ops);


--
-- Name: API_address API_address_user_id_d791bd85_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_address"
    ADD CONSTRAINT "API_address_user_id_d791bd85_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_admin API_admin_user_id_d756faf7_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_admin"
    ADD CONSTRAINT "API_admin_user_id_d756faf7_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_comment API_comment_product_id_659fc917_fk_API_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_comment"
    ADD CONSTRAINT "API_comment_product_id_659fc917_fk_API_product_id" FOREIGN KEY (product_id) REFERENCES public."API_product"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_comment API_comment_user_id_ec370815_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_comment"
    ADD CONSTRAINT "API_comment_user_id_ec370815_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_customer API_customer_user_id_1d90cfb0_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_customer"
    ADD CONSTRAINT "API_customer_user_id_1d90cfb0_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_product API_product_brand_id_fa088085_fk_API_brand_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_product"
    ADD CONSTRAINT "API_product_brand_id_fa088085_fk_API_brand_id" FOREIGN KEY (brand_id) REFERENCES public."API_brand"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_product API_product_subcategory_id_503993c1_fk_API_subcategory_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_product"
    ADD CONSTRAINT "API_product_subcategory_id_503993c1_fk_API_subcategory_id" FOREIGN KEY (subcategory_id) REFERENCES public."API_subcategory"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_product API_product_vendor_id_a575c65f_fk_API_vendor_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_product"
    ADD CONSTRAINT "API_product_vendor_id_a575c65f_fk_API_vendor_id" FOREIGN KEY (vendor_id) REFERENCES public."API_vendor"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_productlist API_productlist_user_id_c793478f_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlist"
    ADD CONSTRAINT "API_productlist_user_id_c793478f_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_productlistitem API_productlistitem_product_id_8d48fa75_fk_API_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlistitem"
    ADD CONSTRAINT "API_productlistitem_product_id_8d48fa75_fk_API_product_id" FOREIGN KEY (product_id) REFERENCES public."API_product"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_productlistitem API_productlistitem_product_list_id_9894a92e_fk_API_produ; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_productlistitem"
    ADD CONSTRAINT "API_productlistitem_product_list_id_9894a92e_fk_API_produ" FOREIGN KEY (product_list_id) REFERENCES public."API_productlist"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_purchase API_purchase_address_id_758414ac_fk_API_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_purchase"
    ADD CONSTRAINT "API_purchase_address_id_758414ac_fk_API_address_id" FOREIGN KEY (address_id) REFERENCES public."API_address"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_purchase API_purchase_product_id_bd130dfd_fk_API_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_purchase"
    ADD CONSTRAINT "API_purchase_product_id_bd130dfd_fk_API_product_id" FOREIGN KEY (product_id) REFERENCES public."API_product"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_purchase API_purchase_user_id_2f9ba145_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_purchase"
    ADD CONSTRAINT "API_purchase_user_id_2f9ba145_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_rating API_rating_product_id_3318665f_fk_API_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_rating"
    ADD CONSTRAINT "API_rating_product_id_3318665f_fk_API_product_id" FOREIGN KEY (product_id) REFERENCES public."API_product"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_rating API_rating_user_id_c5070fdf_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_rating"
    ADD CONSTRAINT "API_rating_user_id_c5070fdf_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_shoppingcartitem API_shoppingcartitem_customer_id_791fcb5c_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_shoppingcartitem"
    ADD CONSTRAINT "API_shoppingcartitem_customer_id_791fcb5c_fk_API_user_id" FOREIGN KEY (customer_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_subcategory API_subcategory_category_id_da64e0ba_fk_API_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_subcategory"
    ADD CONSTRAINT "API_subcategory_category_id_da64e0ba_fk_API_category_id" FOREIGN KEY (category_id) REFERENCES public."API_category"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: API_vendor API_vendor_user_id_80507f12_fk_API_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."API_vendor"
    ADD CONSTRAINT "API_vendor_user_id_80507f12_fk_API_user_id" FOREIGN KEY (user_id) REFERENCES public."API_user"(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permissions auth_group_permissio_permission_id_84c5c92e_fk_auth_perm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissio_permission_id_84c5c92e_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permissions auth_group_permissions_group_id_b120cbf9_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_b120cbf9_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_permission auth_permission_content_type_id_2f476e4b_fk_django_co; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_2f476e4b_fk_django_co FOREIGN KEY (content_type_id) REFERENCES public.django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups auth_user_groups_group_id_97559544_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_group_id_97559544_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups auth_user_groups_user_id_6a12ed8b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_6a12ed8b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log django_admin_log_content_type_id_c4bce8eb_fk_django_co; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_content_type_id_c4bce8eb_fk_django_co FOREIGN KEY (content_type_id) REFERENCES public.django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log django_admin_log_user_id_c564eba6_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_user_id_c564eba6_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- PostgreSQL database dump complete
--

