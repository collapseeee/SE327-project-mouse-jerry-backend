-- V7: Seed admin account with ROLE_ADMIN or promote existing admin user to ROLE_ADMIN
INSERT INTO users (username, email, password_hash, role)
VALUES (
    'admin',
    'admin@mousejerry.com',
    '$2a$10$2UXux71CFs/..2vqbonW..8GOJaWDeoawVD80wcyy0qTyF58Vz8Z.',
    'ROLE_ADMIN'
)
ON CONFLICT (username) DO UPDATE
SET role = 'ROLE_ADMIN',
    password_hash = EXCLUDED.password_hash;
