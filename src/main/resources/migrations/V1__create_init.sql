-- Table: public.role
CREATE TABLE IF NOT EXISTS public.role
(
    role_id uuid NOT NULL,
    role_name character varying(255),
    CONSTRAINT role_pkey PRIMARY KEY (role_id)
    );
ALTER TABLE public.role OWNER TO postgres;

-- Table: public.permissions
CREATE TABLE IF NOT EXISTS public.permissions
(
    permission_id uuid NOT NULL,
    permission_name character varying(255),
    CONSTRAINT permissions_pkey PRIMARY KEY (permission_id)
    );
ALTER TABLE public.permissions OWNER TO postgres;

-- Table: public.customer
CREATE TABLE IF NOT EXISTS public.customer
(
    customer_id uuid NOT NULL,
    birth_date timestamp(6) without time zone NOT NULL,
    door character varying(2),
    finger_print_data character varying(255),
    floor character varying(4),
    gender character varying(255) NOT NULL,
    house_number character varying(10),
    is_deleted boolean NOT NULL DEFAULT false,
    last_name character varying(50) NOT NULL,
    name character varying(50) NOT NULL,
    phone_number character varying(15),
    photo character varying(255),
    street character varying(50),
    CONSTRAINT customer_pkey PRIMARY KEY (customer_id),
    CONSTRAINT customer_gender_check CHECK (gender::text = ANY (ARRAY['MALE', 'FEMALE', 'OTHER']::text[]))
    );
ALTER TABLE public.customer OWNER TO postgres;

-- Table: public.users
CREATE TABLE IF NOT EXISTS public.users
(
    user_id uuid NOT NULL,
    avatar character varying(255),
    email character varying(255) NOT NULL,
    is_deleted boolean NOT NULL DEFAULT false,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    customer_customer_id uuid,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_customer_id)
    REFERENCES public.customer (customer_id)
    );
ALTER TABLE public.users OWNER TO postgres;

-- Update customer to add foreign key after users table exists
ALTER TABLE public.customer
    ADD COLUMN user_id uuid,
ADD CONSTRAINT fk_user FOREIGN KEY (user_id)
REFERENCES public.users (user_id);

-- Table: public.role_permission
CREATE TABLE IF NOT EXISTS public.role_permission
(
    role_id uuid NOT NULL,
    permission_id uuid NOT NULL,
    CONSTRAINT role_permission_unique UNIQUE (permission_id),
    CONSTRAINT fk_permissions FOREIGN KEY (permission_id)
    REFERENCES public.permissions (permission_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id)
    REFERENCES public.role (role_id)
    );
ALTER TABLE public.role_permission OWNER TO postgres;

-- Table: public.user_role
CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id)
    REFERENCES public.role (role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    );
ALTER TABLE public.user_role OWNER TO postgres;
