-- Insertar valores por defecto en la tabla role
INSERT INTO public.role (role_id, role_name) VALUES
                                                 ('1b952f6c-6edb-47d5-954b-e0f72d5fa4ca', 'ADMIN'),
                                                 ('1b952f6c-6edb-47d5-954b-e0f72d5fa4cb', 'USER'),
                                                 ('1b952f6c-6edb-47d5-954b-e0f72d5fa4cc', 'MODERATOR')
    ON CONFLICT DO NOTHING;

-- Insertar valores por defecto en la tabla permissions
INSERT INTO public.permissions (permission_id, permission_name) VALUES
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338bfa', 'CREATE_USER'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338bfb', 'READ_USER'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338bfc', 'UPDATE_USER'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338bfd', 'DELETE_USER')
    ON CONFLICT DO NOTHING;

-- Crear una tabla temporal para obtener UUIDs
WITH role AS (
    SELECT role_id, role_name FROM public.role
),
     permissions AS (
         SELECT permission_id, permission_name FROM public.permissions
     )
-- Insertar valores por defecto en role_permission
INSERT INTO public.role_permission (role_id, permission_id)
SELECT
    (SELECT role_id FROM role WHERE role_name = 'ADMIN'),
    (SELECT permission_id FROM permissions WHERE permission_name = 'CREATE_USER')
    ON CONFLICT DO NOTHING;

INSERT INTO public.role_permission (role_id, permission_id)
SELECT
    (SELECT role_id FROM role WHERE role_name = 'ADMIN'),
    (SELECT permission_id FROM permissions WHERE permission_name = 'READ_USER')
    ON CONFLICT DO NOTHING;

INSERT INTO public.role_permission (role_id, permission_id)
SELECT
    (SELECT role_id FROM role WHERE role_name = 'USER'),
    (SELECT permission_id FROM permissions WHERE permission_name = 'READ_USER')
    ON CONFLICT DO NOTHING;

-- Insertar valores por defecto en la tabla users
INSERT INTO public.users (user_id, avatar, email, is_deleted, password, username, customer_customer_id) VALUES
                                                                                                            ('b24845e8-c3e3-49bb-95e9-38e58fb1514a', 'default_avatar.png', 'admin@example.com', false, 'admin123', 'admin_user', NULL),
                                                                                                            ('b24845e8-c3e3-49bb-95e9-38e58fb1514b', 'default_avatar.png', 'user@example.com', false, 'user123', 'basic_user', NULL)
    ON CONFLICT DO NOTHING;

-- Relacionar role con usuarios en user_role
WITH role AS (
    SELECT role_id, role_name FROM public.role
),
     users AS (
         SELECT user_id, username FROM public.users
     )
INSERT INTO public.user_role (user_id, role_id)
SELECT
    (SELECT user_id FROM users WHERE username = 'admin_user'),
    (SELECT role_id FROM role WHERE role_name = 'ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO public.user_role (user_id, role_id)
SELECT
    (SELECT user_id FROM users WHERE username = 'basic_user'),
    (SELECT role_id FROM role WHERE role_name = 'USER')
    ON CONFLICT DO NOTHING;
