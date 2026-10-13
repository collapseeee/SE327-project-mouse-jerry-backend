-- V8: Seed customer user 'user' if not present and reassign all admin-owned orders to 'user'

INSERT INTO users (username, email, password_hash, role)
SELECT
    'user',
    'user@mousejerry.com',
    '$2a$10$2UXux71CFs/..2vqbonW..8GOJaWDeoawVD80wcyy0qTyF58Vz8Z.',
    'ROLE_CUSTOMER'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'user'
);

-- Reassign all orders belonging to admin (or users with ROLE_ADMIN) to user 'user'
UPDATE orders
SET user_id = (SELECT id FROM users WHERE username = 'user' LIMIT 1)
WHERE user_id IN (SELECT id FROM users WHERE username = 'admin' OR role = 'ROLE_ADMIN');

-- Clean up any cart items belonging to admin
DELETE FROM cart_items
WHERE user_id IN (SELECT id FROM users WHERE username = 'admin' OR role = 'ROLE_ADMIN');
