INSERT INTO public.permissions (permission_id, permission_name) VALUES
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338bfe', 'CREATE_ROLE'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338bff', 'READ_ROLE'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338c00', 'UPDATE_ROLE'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338c01', 'DELETE_ROLE'),

                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338c02', 'CREATE_PERMISSION'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338c03', 'READ_PERMISSION'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338c04', 'UPDATE_PERMISSION'),
                                                                    ('6bf50e9c-5f89-49a5-b915-33382c338c05', 'DELETE_PERMISSION')
    ON CONFLICT DO NOTHING;

INSERT INTO public.role_permission (role_id, permission_id)
SELECT (SELECT role_id FROM public.role WHERE role_name = 'ADMIN'), permission_id
FROM public.permissions
WHERE permission_name IN ('CREATE_USER', 'READ_USER', 'UPDATE_USER', 'DELETE_USER',
                          'CREATE_ROLE', 'READ_ROLE', 'UPDATE_ROLE', 'DELETE_ROLE',
                          'CREATE_PERMISSION', 'READ_PERMISSION', 'UPDATE_PERMISSION', 'DELETE_PERMISSION')
    ON CONFLICT DO NOTHING;

INSERT INTO public.role_permission (role_id, permission_id)
SELECT (SELECT role_id FROM public.role WHERE role_name = 'USER'), permission_id
FROM public.permissions
WHERE permission_name IN ('READ_ROLE', 'READ_PERMISSION')
    ON CONFLICT DO NOTHING;

INSERT INTO public.role_permission (role_id, permission_id)
SELECT (SELECT role_id FROM public.role WHERE role_name = 'MODERATOR'), permission_id
FROM public.permissions
WHERE permission_name IN ('READ_USER', 'UPDATE_USER', 'READ_ROLE', 'READ_PERMISSION', 'UPDATE_PERMISSION')
    ON CONFLICT DO NOTHING;