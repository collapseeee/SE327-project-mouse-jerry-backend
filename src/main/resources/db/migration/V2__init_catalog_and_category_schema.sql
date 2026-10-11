-- V2: Create schema for categories, games, screenshots, and join tables

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(100) NOT NULL UNIQUE,
    is_visible BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS games (
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    cover_image TEXT,
    description TEXT,
    publisher VARCHAR(255),
    release_date VARCHAR(50),
    stock INT NOT NULL DEFAULT 0,
    is_highlight BOOLEAN NOT NULL DEFAULT FALSE,
    primary_category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS game_categories (
    game_id VARCHAR(100) NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (game_id, category_id)
);

CREATE TABLE IF NOT EXISTS game_screenshots (
    id BIGSERIAL PRIMARY KEY,
    game_id VARCHAR(100) NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    url TEXT NOT NULL,
    display_order INT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_categories_slug ON categories(slug);
CREATE INDEX IF NOT EXISTS idx_games_primary_category ON games(primary_category_id);
CREATE INDEX IF NOT EXISTS idx_games_is_highlight ON games(is_highlight);
CREATE INDEX IF NOT EXISTS idx_game_categories_category ON game_categories(category_id);
CREATE INDEX IF NOT EXISTS idx_game_screenshots_game ON game_screenshots(game_id);

-- Seed Categories
INSERT INTO categories (id, name, slug, is_visible) VALUES (1, 'Fighting', 'Fighting', true) ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (id, name, slug, is_visible) VALUES (2, 'Hack and slash/Beat ''em up', 'Hack and slash/Beat ''em up', true) ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (id, name, slug, is_visible) VALUES (3, 'Indie', 'Indie', true) ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (id, name, slug, is_visible) VALUES (4, 'Music', 'Music', true) ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (id, name, slug, is_visible) VALUES (5, 'Pinball', 'Pinball', true) ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (id, name, slug, is_visible) VALUES (6, 'Platform', 'Platform', true) ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (id, name, slug, is_visible) VALUES (7, 'Point-and-click', 'Point-and-click', true) ON CONFLICT (name) DO NOTHING;

SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));

-- Seed Games
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('clone-drone', 'Clone Drone in the Hyperdome', 1702, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2l0q.jpg', 'Clone Drone in the Hyperdome is a physics-based sword combat game where any part of your body can be sliced off.', 'Doborog Games', '2021-07-27', 12, false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('towerfall-ascension', 'Towerfall Ascension: Dark World', 683, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1r3q.jpg', 'TowerFall Ascension is the definitive version of the hit archery combat game.', 'Extremely OK Games', '2014-03-11', 25, false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('superfighters-deluxe', 'Superfighters Deluxe', 1283, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2k3e.jpg', 'Superfighters Deluxe is a unique action game with a little bit of retro and a whole lot of chaos.', 'MythoLogic Interactive', '2018-11-30', 8, false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('fatal-fury', 'Fatal Fury: City of the Wolves', 1912, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7u9q.jpg', 'SNK beloved Fatal Fury series kicks off with a brand-new entry featuring the revolutionary REV System.', 'SNK', '2025-04-24', 18, false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('mortal-kombat-11', 'Mortal Kombat 11: Ultimate', 1289, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co21m2.jpg', 'The definitive MK11 experience. Take control of the protectors of Earthrealm in two critically acclaimed story campaigns.', 'Warner Bros. Games', '2020-11-17', 15, false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('battlefield-3', 'Battlefield 3: Premium Edition', 1969, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1x4c.jpg', 'Battlefield 3 leaps ahead of its time with the power of Frostbite 2, the next instalment of DICE cutting-edge game engine.', 'Electronic Arts', '2011-10-25', 20, true, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('vampire-survivors-emergency-meeting', 'Vampire Survivors: Emergency Meeting', 1788, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7v2e.jpg', 'The Survivors are desperate to hunt vampires, but are running out of places to look. Only one option remains: bullet hell in space! Gather your trustiest crewmates and prepare to eject thousands of aliens in this Emergency Meeting expansion.', 'poncle', '2023-12-18', 50, false, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('elden-ring', 'Elden Ring', 1790, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4jni.jpg', 'THE NEW FANTASY ACTION RPG. Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring.', 'Bandai Namco Entertainment', '2022-02-25', 30, false, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('beast-battle-simulator', 'Beast Battle Simulator', 1373, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2oau.jpg', 'Beast Battle Simulator is a physics based battle-simulation sandbox game featuring dinosaurs and beasts armed with lasers and miniguns.', 'Dogwood Gaming', '2017-08-11', 14, false, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('headquarters-wwii', 'Headquarters World War II', 1277, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co3p2b.jpg', 'Headquarters: World War II is a fast paced turn-based strategy game where battlefield tactics are as important as army management.', 'Slitherine Software', '2024-04-11', 10, false, 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('fivem', 'FiveM', 837, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co41mu.jpg', 'FiveM is a multiplayer modification framework for GTA V, allowing you to play on customized dedicated servers.', 'Cfx.re', '2015-05-01', 99, false, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('tropico-4', 'Tropico 4: Modern Times', 243, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co26vi.jpg', 'The times are changing and the island nation of Tropico is evolving. The Internet, a new world order, and modern technologies come into play.', 'Kalypso Media', '2012-03-27', 40, false, 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('1', 'Among Us', 99, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co5vmg.jpg', 'An online and local party game of teamwork and betrayal for 4-15 players... in space!', 'Innersloth', '2018-11-16', 100, false, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('2', 'Planet Zoo: Aquatic Pack', 350, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2oau.jpg', 'Expand your zoo into a marine wonderland with Planet Zoo Aquatic Pack!', 'Frontier Developments', '2020-12-08', 35, false, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('3', 'Blake: The Visual Novel', 189, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co3p2b.jpg', 'A psychological thriller visual novel following Blake through mystery and moral decisions.', 'Oriplay', '2021-08-31', 15, false, 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('4', 'Motor City Online', 450, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1x4c.jpg', 'Classic muscle car racing and modification MMO from EA.', 'EA', '2001-10-30', 5, false, 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('5', 'Bubsy in: Claws Encounters of the Furred Kind', 150, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co26vi.jpg', 'A classic 16-bit platformer starring the wisecracking bobcat Bubsy.', 'Accolade', '1993-05-01', 22, false, 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('6', 'Cyberpunk 2077', 1399, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co41mu.jpg', 'An open-world, action-adventure RPG set in the dark future of Night City.', 'CD PROJEKT RED', '2020-12-10', 60, false, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('8', 'Hollow Knight', 315, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1rgi.jpg', 'Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes.', 'Team Cherry', '2017-02-24', 75, false, 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('pinball-fx3', 'Pinball FX3: Williams Pinball', 499, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2oau.jpg', 'Pinball FX3 is the biggest, most community focused pinball game ever created.', 'Zen Studios', '2017-09-26', 45, false, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('demon-tilt', 'DEMON TILT: Occult Pinball Action', 369, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1x4c.jpg', 'Turbo-Charged Pinball is back! Updated with bigger sprites, more baddies, more secrets and of course more bullets!', 'FLARB LLC', '2019-12-13', 30, false, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('beat-saber', 'Beat Saber', 799, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2l0q.jpg', 'Beat Saber is a VR rhythm game where you slash the beats of adrenaline-pumping music as they fly towards you.', 'Beat Games', '2019-05-21', 40, false, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('rhythm-doctor', 'Rhythm Doctor', 319, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co3p2b.jpg', 'Heal patients with your rhythm! Rhythm Doctor is a rhythm game where you heal patients by defibrillating on downbeats.', '7th Beat Games', '2021-02-26', 25, false, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('celeste', 'Celeste', 369, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1r3q.jpg', 'Help Madeline survive her inner demons on her journey to the top of Celeste Mountain.', 'Maddy Makes Games', '2018-01-25', 50, false, 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('dead-cells', 'Dead Cells', 499, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co21m2.jpg', 'Dead Cells is a rogue-lite, metroidvania inspired, action-platformer.', 'Motion Twin', '2018-08-07', 35, false, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('hades', 'Hades', 529, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1rgi.jpg', 'Defy the god of the dead as you hack and slash out of the Underworld in this rogue-like dungeon crawler.', 'Supergiant Games', '2020-09-17', 45, false, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('monkey-island', 'Return to Monkey Island', 469, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co5vmg.jpg', 'Return to Monkey Island is an unexpected, thrilling return of series creator Ron Gilbert that continues the story of the legendary adventure games.', 'Devolver Digital', '2022-09-19', 20, false, 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('machinarium', 'Machinarium', 249, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2oau.jpg', 'Machinarium is the award-winning adventure game developed by the makers of Samorost and Botanicula.', 'Amanita Design', '2009-10-16', 30, false, 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('guilty-gear-strive', 'Guilty Gear -Strive-', 1399, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7u9q.jpg', 'Discover the Smell of the Game with Guilty Gear -Strive-! Immerse yourself into new gameplay mechanics designed to be simple and welcoming.', 'Arc System Works', '2021-06-11', 28, false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id) VALUES ('street-fighter-6', 'Street Fighter 6', 1899, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2k3e.jpg', 'Here comes Capcom newest challenger! Street Fighter 6 spans across three distinct game modes: World Tour, Fighting Ground and Battle Hub.', 'Capcom', '2023-06-02', 32, false, 1) ON CONFLICT (id) DO NOTHING;

-- Seed Game Categories
INSERT INTO game_categories (game_id, category_id) VALUES ('clone-drone', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('clone-drone', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('towerfall-ascension', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('towerfall-ascension', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('towerfall-ascension', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('superfighters-deluxe', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('superfighters-deluxe', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('fatal-fury', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('mortal-kombat-11', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('battlefield-3', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('vampire-survivors-emergency-meeting', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('vampire-survivors-emergency-meeting', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('elden-ring', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('beast-battle-simulator', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('headquarters-wwii', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('fivem', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('tropico-4', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('1', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('2', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('3', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('3', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('4', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('5', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('6', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('8', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('8', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('pinball-fx3', 5) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('demon-tilt', 5) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('beat-saber', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('rhythm-doctor', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('rhythm-doctor', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('celeste', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('celeste', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('dead-cells', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('dead-cells', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('hades', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('monkey-island', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('machinarium', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('guilty-gear-strive', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('street-fighter-6', 1) ON CONFLICT DO NOTHING;

-- Seed Game Screenshots
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc6gqg.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc6gqh.jpg', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc6gqi.jpg', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc6gqj.jpg', 3);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('towerfall-ascension', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('towerfall-ascension', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('superfighters-deluxe', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fatal-fury', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('mortal-kombat-11', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('battlefield-3', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('battlefield-3', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://images.igdb.com/igdb/image/upload/t_1080p/co7v2e.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 3);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc6gqg.jpg', 4);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc6gqh.jpg', 5);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('elden-ring', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('elden-ring', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beast-battle-simulator', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beast-battle-simulator', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('headquarters-wwii', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fivem', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tropico-4', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('1', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('2', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('3', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('4', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('5', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('6', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('8', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx3', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('demon-tilt', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beat-saber', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('rhythm-doctor', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('celeste', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dead-cells', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hades', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('monkey-island', 'https://images.igdb.com/igdb/image/upload/t_1080p/sclh7y.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('machinarium', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('guilty-gear-strive', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('street-fighter-6', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc7x7t.jpg', 0);
