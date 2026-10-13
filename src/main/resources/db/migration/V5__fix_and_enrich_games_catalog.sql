-- V5: Correct mismatched game images and enrich catalog data

-- Game: Among Us (1)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('1', 'Among Us', 3850, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/945360/library_600x900.jpg', 'An online and local party game of teamwork and betrayal for 4-15 players...in space!', 'Innersloth', '16 Nov, 2018', 29, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '1';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('1', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/945360/ss_b7374128e5b786a302a716bca038d604b00ffe46.1920x1080.jpg?t=1787088490', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('1', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/945360/ss_719484b5e0314cc2ae43793786448e032056a31d.1920x1080.jpg?t=1787088490', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('1', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/945360/ss_ffd9c8926cbd7fce3ca5e5efb4399c47bb196bc8.1920x1080.jpg?t=1787088490', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('1', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/945360/ss_1fbd9dc9fd6a2710d233a088ef426a6d78fcf884.1920x1080.jpg?t=1787088490', 3);
DELETE FROM game_categories WHERE game_id = '1';
INSERT INTO game_categories (game_id, category_id) VALUES ('1', 3) ON CONFLICT DO NOTHING;

-- Game: Planet Zoo: Aquatic Pack (2)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('2', 'Planet Zoo: Aquatic Pack', 12427, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1471590/header.jpg?t=1706278417', 'Expand your zoo into a marine menagerie with Planet Zoo''s Aquatic Pack! Adopt five magnificent new animals including King Penguins and Giant Otters, construct beautiful scenery from 170+ new pieces, and master the aquatic-themed challenge zoo set in Oregon, USA.', 'Frontier Developments', '8 Dec, 2020', 31, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '2';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('2', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1471590/ss_ad0511722ea1a3b9c4791fd36f4069b1a4c04bf3.1920x1080.jpg?t=1706278417', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('2', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1471590/ss_aacb93274e83e7eb624c67801e682336ca0532db.1920x1080.jpg?t=1706278417', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('2', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1471590/ss_3546b1d671401b7731d0c20174d24b3e39f8c96a.1920x1080.jpg?t=1706278417', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('2', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1471590/ss_e03a0114295f45f1bc5c5e7250edfb1b7589f744.1920x1080.jpg?t=1706278417', 3);
DELETE FROM game_categories WHERE game_id = '2';
INSERT INTO game_categories (game_id, category_id) VALUES ('2', 3) ON CONFLICT DO NOTHING;

-- Game: Blake: The Visual Novel (3)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('3', 'Blake: The Visual Novel', 599, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1538680/library_600x900.jpg', 'Graphic Novel meets Visual Novel in this story-rich, choice-driven thriller. Experience Blake’s journey as he encounters a clandestine nemesis, sparking a series of events that force him to choose between losing his sanity or rising to the challenge. Make the right decisions and save New Stone.', 'LegendOri Productions', '31 Aug, 2021', 40, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '3';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1538680/ss_99a64f6bd41d2a4b54106dc272afadb7bfd9df7c.1920x1080.jpg?t=1702056413', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1538680/ss_fac4f48d11b07373c7510289c8ab1f480068b86a.1920x1080.jpg?t=1702056413', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1538680/ss_f3d91e679c1ab77f866fae24f764c7f7b8edcdd7.1920x1080.jpg?t=1702056413', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1538680/ss_590d83e29a79bc82a2361ded6d0409e4482fe344.1920x1080.jpg?t=1702056413', 3);
DELETE FROM game_categories WHERE game_id = '3';
INSERT INTO game_categories (game_id, category_id) VALUES ('3', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('3', 3) ON CONFLICT DO NOTHING;

-- Game: Motor City Online (4)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('4', 'Motor City Online', 450, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2k3e.jpg', 'Classic muscle car racing and car modification online game by Electronic Arts.', 'Electronic Arts', '2001-10-30', 15, false, 6)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '4';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('4', 'https://images.igdb.com/igdb/image/upload/t_1080p/sc66l2.jpg', 0);
DELETE FROM game_categories WHERE game_id = '4';
INSERT INTO game_categories (game_id, category_id) VALUES ('4', 6) ON CONFLICT DO NOTHING;

-- Game: Bubsy in: Claws Encounters of the Furred Kind (5)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('5', 'Bubsy in: Claws Encounters of the Furred Kind', 1015, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/326180/header.jpg?t=1789037468', 'Who would name their town Sinister City? Vampires, of course! When John discovers his bride-to-be has been kidnapped by Sinister City''s number one bloodsucker, there''s only one thing to do - search for hidden objects! And kill vampires! Or at least insult them until they give your girl back.', 'Jetdogs Studios', '20 Oct, 2014', 22, false, 6)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '5';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/326180/ss_13a246b226176b0790b97c332c647b4e0e1365e8.1920x1080.jpg?t=1789037468', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/326180/ss_5b68ebd6fa165439fe3055730cbfa1bdfe6a0dd5.1920x1080.jpg?t=1789037468', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/326180/ss_44d4b4bdab1ca1610ea4d9ea66931fcaa66ebaac.1920x1080.jpg?t=1789037468', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/326180/ss_4429e9850b015ac77ff0a1f5228dd498f56a0816.1920x1080.jpg?t=1789037468', 3);
DELETE FROM game_categories WHERE game_id = '5';
INSERT INTO game_categories (game_id, category_id) VALUES ('5', 6) ON CONFLICT DO NOTHING;

-- Game: Cyberpunk 2077 (6)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('6', 'Cyberpunk 2077', 62965, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1091500/library_600x900.jpg', 'Cyberpunk 2077 is an open-world, action-adventure RPG set in the dark future of Night City — a dangerous megalopolis obsessed with power, glamor, and ceaseless body modification.', 'CD PROJEKT RED', '9 Dec, 2020', 40, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '6';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/ss_2f649b68d579bf87011487d29bc4ccbfdd97d34f.1920x1080.jpg?t=1784714077', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/ss_0e64170751e1ae20ff8fdb7001a8892fd48260e7.1920x1080.jpg?t=1784714077', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/ss_af2804aa4bf35d4251043744412ce3b359a125ef.1920x1080.jpg?t=1784714077', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/ss_7924f64b6e5d586a80418c9896a1c92881a7905b.1920x1080.jpg?t=1784714077', 3);
DELETE FROM game_categories WHERE game_id = '6';
INSERT INTO game_categories (game_id, category_id) VALUES ('6', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('6', 2) ON CONFLICT DO NOTHING;

-- Game: Hollow Knight (8)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('8', 'Hollow Knight', 290500, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/367520/library_600x900.jpg', 'Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes. Explore twisting caverns, battle tainted creatures and befriend bizarre bugs, all in a classic, hand-drawn 2D style.', 'Team Cherry', '24 Feb, 2017', 22, false, 6)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = '8';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/367520/ss_5384f9f8b96a0b9934b2bc35a4058376211636d2.1920x1080.jpg?t=1776125684', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/367520/ss_d5b6edd94e77ba6db31c44d8a3c09d807ab27751.1920x1080.jpg?t=1776125684', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/367520/ss_a81e4231cc8d55f58b51a4a938898af46503cae5.1920x1080.jpg?t=1776125684', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/367520/ss_62e10cf506d461e11e050457b08aa0e2a1c078d0.1920x1080.jpg?t=1776125684', 3);
DELETE FROM game_categories WHERE game_id = '8';
INSERT INTO game_categories (game_id, category_id) VALUES ('8', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('8', 3) ON CONFLICT DO NOTHING;

-- Game: Battlefield 3™ (battlefield-3)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('battlefield-3', 'Battlefield 3™', 38465, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1238820/library_600x900.jpg', 'Enjoy total freedom to fight the way you want. Explore 29 massive multiplayer maps and use loads of vehicles, weapons, and gadgets to help you turn up the heat. Every second of battle gets you closer to unlocking tons of extras and moving up in the Ranks. So get in the action.', 'Electronic Arts', '11 Jun, 2020', 38, true, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'battlefield-3';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('battlefield-3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1238820/ss_3e59b961834a4ef2d36e600db373049927cb2f0f.1920x1080.jpg?t=1777326004', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('battlefield-3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1238820/ss_8fce61feee86b183dd6c0b38270ea445017c2794.1920x1080.jpg?t=1777326004', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('battlefield-3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1238820/ss_2ee2d601c10fb9db2d9adf31f42183e7465927be.1920x1080.jpg?t=1777326004', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('battlefield-3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1238820/ss_c378ec3b0951e274244f337cf6fd42cd0ce875ed.1920x1080.jpg?t=1777326004', 3);
DELETE FROM game_categories WHERE game_id = 'battlefield-3';
INSERT INTO game_categories (game_id, category_id) VALUES ('battlefield-3', 1) ON CONFLICT DO NOTHING;

-- Game: Beast Battle Simulator (beast-battle-simulator)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('beast-battle-simulator', 'Beast Battle Simulator', 7665, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/671620/header.jpg?t=1519435286', 'Beast Battle Simulator is a physics based battle-simulation sandbox game featuring dinosaurs, animals, and humans. Kamikaze Zebras VS. T-Rex with laser guns, Lions VS. medieval knights; anything is possible! Watch as the beasts dismember and rip each other apart in gory detail!', 'DOG HOGGLER', '22 Feb, 2018', 48, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'beast-battle-simulator';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beast-battle-simulator', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/671620/ss_d1f5ca82c08fd36559e6fe5ac8e00d8fa884ff18.1920x1080.jpg?t=1519435286', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beast-battle-simulator', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/671620/ss_e94cf319c931bbff97fe473c70fa3196330585f5.1920x1080.jpg?t=1519435286', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beast-battle-simulator', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/671620/ss_02b1d6b6c4d6b5f5d767dd6d31c5f72fce4039fa.1920x1080.jpg?t=1519435286', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beast-battle-simulator', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/671620/ss_0bdb3ae7ad16789f9d886a0efe7681d2e0dccd6f.1920x1080.jpg?t=1519435286', 3);
DELETE FROM game_categories WHERE game_id = 'beast-battle-simulator';
INSERT INTO game_categories (game_id, category_id) VALUES ('beast-battle-simulator', 3) ON CONFLICT DO NOTHING;

-- Game: Beat Saber (beat-saber)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('beat-saber', 'Beat Saber', 22225, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/620980/library_600x900.jpg', 'Slash the beats, get moving, and experience the world''s most popular VR rhythm game!* Duck, dodge, and flow through 300+ handcrafted songs from artists like Coldplay, Imagine Dragons, and Metallica. Play solo, compete online, or pass the headset at your next party. Jump in with 60+ songs included!', 'Beat Games', '21 May, 2019', 41, false, 4)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'beat-saber';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beat-saber', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/620980/6204b8e313db035cbcaf30ad02ed8ee7cbdcfb18/ss_6204b8e313db035cbcaf30ad02ed8ee7cbdcfb18.1920x1080.jpg?t=1790687477', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beat-saber', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/620980/ss_7df971fd7781d69dc455b15a400a6973ed7d3f36.1920x1080.jpg?t=1790687477', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beat-saber', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/620980/ss_e1b161014deff483fa6bd8147a102b83b053e201.1920x1080.jpg?t=1790687477', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('beat-saber', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/620980/ss_b39d40cd4c53c9fd737ffde535cfdffc17662862.1920x1080.jpg?t=1790687477', 3);
DELETE FROM game_categories WHERE game_id = 'beat-saber';
INSERT INTO game_categories (game_id, category_id) VALUES ('beat-saber', 4) ON CONFLICT DO NOTHING;

-- Game: Celeste (celeste)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('celeste', 'Celeste', 13965, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/504230/library_600x900.jpg', 'Help Madeline survive her inner demons on her journey to the top of Celeste Mountain, in this super-tight platformer from the creators of TowerFall. Brave hundreds of hand-crafted challenges, uncover devious secrets, and piece together the mystery of the mountain.', 'Maddy Makes Games Inc.', '25 Jan, 2018', 56, false, 6)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'celeste';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('celeste', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/504230/ss_1ad297c2044cdcf450ee83e56350cafb590da755.1920x1080.jpg?t=1714089525', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('celeste', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/504230/ss_03bfe6bd5ddac7f747c8d2aa1a4f82cfd53c6dcb.1920x1080.jpg?t=1714089525', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('celeste', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/504230/ss_4b0f0222341b64a37114033aca9994551f27c161.1920x1080.jpg?t=1714089525', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('celeste', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/504230/ss_1012b11ad364ad6c138a25a654108de28de56c5f.1920x1080.jpg?t=1714089525', 3);
DELETE FROM game_categories WHERE game_id = 'celeste';
INSERT INTO game_categories (game_id, category_id) VALUES ('celeste', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('celeste', 3) ON CONFLICT DO NOTHING;

-- Game: Clone Drone in the Danger Zone (clone-drone)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('clone-drone', 'Clone Drone in the Danger Zone', 10115, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/597170/library_600x900.jpg', 'Clone Drone in the Danger Zone is a robot voxel slice-em-up where any part of your body can be sliced off. With your mind downloaded into a robot gladiator, you must survive the sinister trials of the arena.', 'Doborog Games', '27 Jul, 2021', 35, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'clone-drone';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/597170/ss_b0e2f164f94d531f6c49785598f2a444d3e965a4.1920x1080.jpg?t=1784069375', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/597170/96ebab7085fb8651ce237ee32dfa8c67b8dc7f6c/ss_96ebab7085fb8651ce237ee32dfa8c67b8dc7f6c.1920x1080.jpg?t=1784069375', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/597170/ss_735de402cde4c061b74c0b7bc0f4c7762e1eff74.1920x1080.jpg?t=1784069375', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('clone-drone', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/597170/f081fb0e9475a85dc9e077600a222f6ab83b4282/ss_f081fb0e9475a85dc9e077600a222f6ab83b4282.1920x1080.jpg?t=1784069375', 3);
DELETE FROM game_categories WHERE game_id = 'clone-drone';
INSERT INTO game_categories (game_id, category_id) VALUES ('clone-drone', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('clone-drone', 3) ON CONFLICT DO NOTHING;

-- Game: Dead Cells (dead-cells)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('dead-cells', 'Dead Cells', 13615, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/588650/library_600x900.jpg', 'Dead Cells is a roguelite, metroidvania inspired, action-platformer. You''ll explore a sprawling, ever-changing castle... assuming you’re able to fight your way past its keepers in 2D souls-lite combat. No checkpoints. Kill, die, learn, repeat.', 'Motion Twin', '6 Aug, 2018', 18, false, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'dead-cells';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dead-cells', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/588650/ss_ac28000ade40cc2fe5c128f32ac98ba33c008a7a.1920x1080.jpg?t=1779086887', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dead-cells', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/588650/ss_7bde51ea6c8f6289e85ea1d8c1c941e1f8bfee91.1920x1080.jpg?t=1779086887', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dead-cells', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/588650/ss_e87e72a247918d8493892e035d5e1b4b84470d2f.1920x1080.jpg?t=1779086887', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dead-cells', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/588650/ss_a099416b9f3e09d47c42f87667e6ad6f394ba652.1920x1080.jpg?t=1779086887', 3);
DELETE FROM game_categories WHERE game_id = 'dead-cells';
INSERT INTO game_categories (game_id, category_id) VALUES ('dead-cells', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('dead-cells', 6) ON CONFLICT DO NOTHING;

-- Game: DEMON''S TILT (demon-tilt)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('demon-tilt', 'DEMON''S TILT', 10115, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/422510/library_600x900.jpg', 'TURBO CHARGED PINBALL RETURNS! Now with bigger sprites, more baddies, more secrets &amp; MORE BULLETS! Demon''s Tilt pushes the limits of the Video Pinball genre with SHMUP &amp; Hack N'' Slash elements.', 'FLARB LLC', '13 Dec, 2019', 12, false, 5)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'demon-tilt';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('demon-tilt', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/422510/ss_708d1de6bf30bb979382e2b19e78adc8b713a565.1920x1080.jpg?t=1736882887', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('demon-tilt', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/422510/ss_2063b1f984dece9ed1acec414ad53db31dc17de0.1920x1080.jpg?t=1736882887', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('demon-tilt', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/422510/ss_cab962bca110e9cd82663c661576bd74d7ec7add.1920x1080.jpg?t=1736882887', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('demon-tilt', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/422510/ss_7bc1cb4f983613e58ddb7a48588b32c185417dde.1920x1080.jpg?t=1736882887', 3);
DELETE FROM game_categories WHERE game_id = 'demon-tilt';
INSERT INTO game_categories (game_id, category_id) VALUES ('demon-tilt', 5) ON CONFLICT DO NOTHING;

-- Game: ELDEN RING (elden-ring)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('elden-ring', 'ELDEN RING', 2100, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1245620/library_600x900.jpg', 'THE CRITICALLY ACCLAIMED FANTASY ACTION RPG. Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between.', 'FromSoftware, Inc.', '24 Feb, 2022', 26, true, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'elden-ring';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('elden-ring', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/ss_943bf6fe62352757d9070c1d33e50b92fe8539f1.1920x1080.jpg?t=1790290043', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('elden-ring', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/ss_dcdac9e4b26ac0ee5248bfd2967d764fd00cdb42.1920x1080.jpg?t=1790290043', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('elden-ring', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/ss_3c41384a24d86dddd58a8f61db77f9dc0bfda8b5.1920x1080.jpg?t=1790290043', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('elden-ring', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/ss_e0316c76f8197405c1312d072b84331dd735d60b.1920x1080.jpg?t=1790290043', 3);
DELETE FROM game_categories WHERE game_id = 'elden-ring';
INSERT INTO game_categories (game_id, category_id) VALUES ('elden-ring', 2) ON CONFLICT DO NOTHING;

-- Game: FATAL FURY: City of the Wolves (fatal-fury)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('fatal-fury', 'FATAL FURY: City of the Wolves', 18900, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/2492040/library_600x900.jpg', '26 years on, a brand-new entry in the Fatal Fury series is set to arrive on the scene! FATAL FURY: City of the Wolves features a unique art style that stimulates the senses and an innovative REV System that supercharges the excitement. Here, at long last, a new legend is about to unfold…', 'SNK CORPORATION', '22 Jan, 2026', 17, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'fatal-fury';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fatal-fury', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2492040/ss_ccc4b1fb9b32b96894e9e44b1ee76b6f89edf8a7.1920x1080.jpg?t=1790572698', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fatal-fury', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2492040/ss_3333481955af641377d60e5c0724d16bc1e1a01e.1920x1080.jpg?t=1790572698', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fatal-fury', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2492040/ss_b0be13dcea64db30c829f142e17d4433d3751504.1920x1080.jpg?t=1790572698', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fatal-fury', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2492040/ss_6ead10e288ffeb952b65cfa98a9fc293144d3520.1920x1080.jpg?t=1790572698', 3);
DELETE FROM game_categories WHERE game_id = 'fatal-fury';
INSERT INTO game_categories (game_id, category_id) VALUES ('fatal-fury', 1) ON CONFLICT DO NOTHING;

-- Game: FiveM (Grand Theft Auto V) (fivem)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('fivem', 'FiveM (Grand Theft Auto V)', 599, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/271590/library_600x900.jpg', 'Grand Theft Auto V for PC offers players the option to explore the award-winning world of Los Santos and Blaine County in resolutions of up to 4k and beyond, as well as the chance to experience the game running at 60 frames per second.', 'Rockstar Games', '13 Apr, 2015', 38, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'fivem';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fivem', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/271590/ss_32aa18ab3175e3002217862dd5917646d298ab6b.1920x1080.jpg?t=1765387725', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fivem', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/271590/ss_2744f112fa060320d191a50e8b3a92441a648a56.1920x1080.jpg?t=1765387725', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fivem', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/271590/ss_da39c16db175f6973770bae6b91d411251763152.1920x1080.jpg?t=1765387725', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('fivem', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/271590/ss_bd5db78286be0a7c6b2c62519099a9e27e6b06f3.1920x1080.jpg?t=1765387725', 3);
DELETE FROM game_categories WHERE game_id = 'fivem';
INSERT INTO game_categories (game_id, category_id) VALUES ('fivem', 3) ON CONFLICT DO NOTHING;

-- Game: GUILTY GEAR -STRIVE- (guilty-gear-strive)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('guilty-gear-strive', 'GUILTY GEAR -STRIVE-', 46550, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1384160/library_600x900.jpg', 'The cutting-edge 2D/3D hybrid graphics pioneered in the Guilty Gear series have been raised to the next level in “GUILTY GEAR -STRIVE-“. The new artistic direction and improved character animations will go beyond anything you’ve seen before in a fighting game!', 'Arc System Works', '11 Jun, 2021', 33, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'guilty-gear-strive';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('guilty-gear-strive', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1384160/ss_0f29aebacb81b972782382dbf4c0079f0d37b330.1920x1080.jpg?t=1788400087', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('guilty-gear-strive', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1384160/ss_2561a4ae822856797b5fb231502b820a962336e0.1920x1080.jpg?t=1788400087', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('guilty-gear-strive', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1384160/ss_104e16788c2ea35e1dac31d96be8d4f1df00f330.1920x1080.jpg?t=1788400087', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('guilty-gear-strive', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1384160/ss_c290682ecdf413e368d9a66cd4a7a9430860ca1d.1920x1080.jpg?t=1788400087', 3);
DELETE FROM game_categories WHERE game_id = 'guilty-gear-strive';
INSERT INTO game_categories (game_id, category_id) VALUES ('guilty-gear-strive', 1) ON CONFLICT DO NOTHING;

-- Game: Hades (hades)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('hades', 'Hades', 455000, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1145360/library_600x900.jpg', 'Defy the god of the dead as you hack and slash out of the Underworld in this rogue-like dungeon crawler from the creators of Bastion, Transistor, and Pyre.', 'Supergiant Games', '17 Sep, 2020', 28, true, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'hades';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hades', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1145360/ss_c0fed447426b69981cf1721756acf75369801b31.1920x1080.jpg?t=1758127023', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hades', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1145360/ss_8a9f0953e8a014bd3df2789c2835cb787cd3764d.1920x1080.jpg?t=1758127023', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hades', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1145360/ss_68300459a8c3daacb2ec687adcdbf4442fcc4f47.1920x1080.jpg?t=1758127023', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hades', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1145360/ss_bcb499a0dd001f4101823f99ec5094d2872ba6ee.1920x1080.jpg?t=1758127023', 3);
DELETE FROM game_categories WHERE game_id = 'hades';
INSERT INTO game_categories (game_id, category_id) VALUES ('hades', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('hades', 3) ON CONFLICT DO NOTHING;

-- Game: Headquarters: World War II (headquarters-wwii)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('headquarters-wwii', 'Headquarters: World War II', 25165, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1840800/library_600x900.jpg', 'Headquarters: World War II is a fast-paced turn-based strategy game where your battlefield tactics are as important as your army management skills. Experience both sides of the war, battling in Europe as the USA, the UK, or Germany. Storm bunkers, occupy houses, win tank duels.', 'Slitherine Ltd.', '11 Apr, 2024', 44, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'headquarters-wwii';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('headquarters-wwii', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1840800/ss_9635de221cb8f3b8739ff54b6d7a7a649d15a86a.1920x1080.jpg?t=1773910651', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('headquarters-wwii', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1840800/ss_c9f08575b6e1967c99eb2d90e7e0a0829ec0a3ca.1920x1080.jpg?t=1773910651', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('headquarters-wwii', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1840800/ss_931049b55c66d010e8b569bff8d2b3912925c0b3.1920x1080.jpg?t=1773910651', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('headquarters-wwii', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1840800/ss_a8341d5fa3491cf74ada42da743de83a18187227.1920x1080.jpg?t=1773910651', 3);
DELETE FROM game_categories WHERE game_id = 'headquarters-wwii';
INSERT INTO game_categories (game_id, category_id) VALUES ('headquarters-wwii', 7) ON CONFLICT DO NOTHING;

-- Game: Machinarium (machinarium)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('machinarium', 'Machinarium', 13965, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/40700/library_600x900.jpg', 'Machinarium is the award-winning adventure game from the makers of Phonopolis, CHUCHEL and the Samorost series. Help Josef the robot save his girlfriend Berta, kidnapped by the Black Cap Brotherhood gang!', 'Amanita Design', '16 Oct, 2009', 17, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'machinarium';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('machinarium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/40700/f44684bc76b27d5b2b2ad4b9d5a02f50ce95da70/ss_f44684bc76b27d5b2b2ad4b9d5a02f50ce95da70.1920x1080.jpg?t=1791501193', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('machinarium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/40700/d3d083f8514e6298a164e0a3fe03a76f619942ea/ss_d3d083f8514e6298a164e0a3fe03a76f619942ea.1920x1080.jpg?t=1791501193', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('machinarium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/40700/80f825bcb9c71fcfef60ab6847b545b7b4928a21/ss_80f825bcb9c71fcfef60ab6847b545b7b4928a21.1920x1080.jpg?t=1791501193', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('machinarium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/40700/4e977c41d3e5b2b9e2e7d045c8b9768e0031d38f/ss_4e977c41d3e5b2b9e2e7d045c8b9768e0031d38f.1920x1080.jpg?t=1791501193', 3);
DELETE FROM game_categories WHERE game_id = 'machinarium';
INSERT INTO game_categories (game_id, category_id) VALUES ('machinarium', 7) ON CONFLICT DO NOTHING;

-- Game: Return to Monkey Island (monkey-island)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('monkey-island', 'Return to Monkey Island', 17325, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/2060130/library_600x900.jpg', 'Return to Monkey Island is an unexpected, thrilling return of series creator Ron Gilbert that continues the story of the legendary adventure games.', 'Devolver Digital', '19 Sep, 2022', 26, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'monkey-island';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('monkey-island', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2060130/ss_bd989f1025e559eec8d6993666323c9d06181a04.1920x1080.jpg?t=1711536318', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('monkey-island', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2060130/ss_2f7e407b460e4053d142dd1dbae050407d84fe4d.1920x1080.jpg?t=1711536318', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('monkey-island', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2060130/ss_df2537e4ebf236fb9782baea0ab5f782ca344327.1920x1080.jpg?t=1711536318', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('monkey-island', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2060130/ss_2d2ec4d82af7ef0df626b7867adca9cdd7cb698d.1920x1080.jpg?t=1711536318', 3);
DELETE FROM game_categories WHERE game_id = 'monkey-island';
INSERT INTO game_categories (game_id, category_id) VALUES ('monkey-island', 7) ON CONFLICT DO NOTHING;

-- Game: Mortal Kombat 11 (mortal-kombat-11)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('mortal-kombat-11', 'Mortal Kombat 11', 34650, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/976310/library_600x900.jpg', 'Mortal Kombat is back and better than ever in the next evolution of the iconic franchise.', 'Warner Bros. Games', '23 Apr, 2019', 27, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'mortal-kombat-11';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('mortal-kombat-11', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/976310/ss_e5cd8debd74027dbfafd9729fc32986a63393333.1920x1080.jpg?t=1780429525', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('mortal-kombat-11', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/976310/ss_4fa4fd2ea1b7ff6c6b699dc9eb717986f80845a4.1920x1080.jpg?t=1780429525', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('mortal-kombat-11', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/976310/ss_aa70f659fe14e3c07033474249096b60c17021b3.1920x1080.jpg?t=1780429525', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('mortal-kombat-11', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/976310/ss_b0e8bcfcebf910a6606a7903d2e23ec589c0c45b.1920x1080.jpg?t=1780429525', 3);
DELETE FROM game_categories WHERE game_id = 'mortal-kombat-11';
INSERT INTO game_categories (game_id, category_id) VALUES ('mortal-kombat-11', 1) ON CONFLICT DO NOTHING;

-- Game: Pinball FX Classic (pinball-fx3)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('pinball-fx3', 'Pinball FX Classic', 599, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/442120/library_600x900.jpg', 'Pinball FX Classic is the biggest, most community focused pinball game ever created. Multiplayer matchups, user generated tournaments and league play create endless opportunity for pinball competition.', 'Zen Studios', '26 Sep, 2017', 37, false, 5)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'pinball-fx3';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/442120/ss_c91140ba838b9d2ed28138824b60a7a629bd5c28.1920x1080.jpg?t=1782985617', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/442120/ss_3169b66383843f3331eb9756501fba2467f82b38.1920x1080.jpg?t=1782985617', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/442120/ss_8495fc9cf9cd125bee5a1df33b7f5d8251fd12a1.1920x1080.jpg?t=1782985617', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx3', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/442120/ss_03467b16b2619b1b562176ed8c22108111e0434d.1920x1080.jpg?t=1782985617', 3);
DELETE FROM game_categories WHERE game_id = 'pinball-fx3';
INSERT INTO game_categories (game_id, category_id) VALUES ('pinball-fx3', 5) ON CONFLICT DO NOTHING;

-- Game: Rhythm Doctor (rhythm-doctor)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('rhythm-doctor', 'Rhythm Doctor', 14000, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/774181/library_600x900.jpg', 'Save patients with your rhythm mastery! Rhythm Doctor is a rhythm game where you heal patients by defibrillating in time to their heartbeats. Learn each patient''s unique heartbeat and defeat boss viruses trying to sabotage your rhythm, all set to heart-pumping, soul-soothing music.', '7th Beat Games', '6 Dec, 2025', 23, false, 4)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'rhythm-doctor';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('rhythm-doctor', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774181/ss_e361a4f2de7594952b61522bb21a66713fe3f808.1920x1080.jpg?t=1786548815', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('rhythm-doctor', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774181/ss_a8427224faf8062ed4eea2d3af9637934914a85c.1920x1080.jpg?t=1786548815', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('rhythm-doctor', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774181/ss_0ad870eb7a1cc4315b17c2aaa2e54c0b5f220684.1920x1080.jpg?t=1786548815', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('rhythm-doctor', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774181/ss_cb3a0ab6aaaf1bfb929e32bd8df05a3ffd7140e3.1920x1080.jpg?t=1786548815', 3);
DELETE FROM game_categories WHERE game_id = 'rhythm-doctor';
INSERT INTO game_categories (game_id, category_id) VALUES ('rhythm-doctor', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('rhythm-doctor', 3) ON CONFLICT DO NOTHING;

-- Game: Street Fighter™ 6 (street-fighter-6)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('street-fighter-6', 'Street Fighter™ 6', 39550, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1364780/library_600x900.jpg', 'Here comes Capcom’s newest challenger! Street Fighter™ 6 launches worldwide on June 2nd, 2023 and represents the next evolution of the Street Fighter™ series! Street Fighter 6 spans three distinct game modes, including World Tour, Fighting Ground and Battle Hub.', 'CAPCOM Co., Ltd.', '1 Jun, 2023', 21, true, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'street-fighter-6';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('street-fighter-6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1364780/ss_387137f8cccb048c35a8685634372e97785d40aa.1920x1080.jpg?t=1789611360', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('street-fighter-6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1364780/ss_a381f1b3b450c18900d47b991ce8e7456e9cdba5.1920x1080.jpg?t=1789611360', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('street-fighter-6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1364780/ss_f62ce93269a6d8e0027853358af4d6368e2c4b93.1920x1080.jpg?t=1789611360', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('street-fighter-6', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1364780/ss_d186566a92ada8cdb08b04769a8c95cd1e380006.1920x1080.jpg?t=1789611360', 3);
DELETE FROM game_categories WHERE game_id = 'street-fighter-6';
INSERT INTO game_categories (game_id, category_id) VALUES ('street-fighter-6', 1) ON CONFLICT DO NOTHING;

-- Game: Superfighters Deluxe (superfighters-deluxe)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('superfighters-deluxe', 'Superfighters Deluxe', 6615, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/855860/library_600x900.jpg', 'Superfighters Deluxe is a unique action game that combines brawling, shooting and platforming in dynamic sandboxy 2D levels. Lots of weapons and fun gameplay systems interlock to create absurd action-movie chaos.', 'MythoLogic Interactive', '30 Nov, 2018', 24, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'superfighters-deluxe';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('superfighters-deluxe', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/855860/ss_0f1d0d077ce556fc003e2f2cd330d0fb8ce8dd7c.1920x1080.jpg?t=1774470652', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('superfighters-deluxe', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/855860/ss_39b7617d6633dc66f8cbf8b1976932be3f4f9d6e.1920x1080.jpg?t=1774470652', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('superfighters-deluxe', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/855860/ss_73c58781f733204a830be850da154ae39d54d78c.1920x1080.jpg?t=1774470652', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('superfighters-deluxe', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/855860/ss_38dafe33271642faa2e9261e7856f501ee489b03.1920x1080.jpg?t=1774470652', 3);
DELETE FROM game_categories WHERE game_id = 'superfighters-deluxe';
INSERT INTO game_categories (game_id, category_id) VALUES ('superfighters-deluxe', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('superfighters-deluxe', 3) ON CONFLICT DO NOTHING;

-- Game: TowerFall Ascension (towerfall-ascension)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('towerfall-ascension', 'TowerFall Ascension', 10465, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/251470/library_600x900.jpg', 'TowerFall Ascension is the definitive version of the hit archery combat game. Inspired by classics from the golden age of couch multiplayer, it''s a 4-player local party game centering around hilarious, intense versus matches. The core mechanics are simple and accessible, but hard to master and combat is fierce.', 'Maddy Makes Games Inc.', '11 Mar, 2014', 57, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'towerfall-ascension';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('towerfall-ascension', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/251470/ss_5f5782e7f91f99dbeb7f9a561771378f850ae872.1920x1080.jpg?t=1715114308', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('towerfall-ascension', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/251470/ss_aa0f804052ab6b93994c5893837445c789bf35ea.1920x1080.jpg?t=1715114308', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('towerfall-ascension', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/251470/ss_01a805ebff71a1b5fb392f7c09ee0b8f73707ed7.1920x1080.jpg?t=1715114308', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('towerfall-ascension', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/251470/ss_eebc6b6dbf2ceec96946b4e67e3ddd31fd8a50b0.1920x1080.jpg?t=1715114308', 3);
DELETE FROM game_categories WHERE game_id = 'towerfall-ascension';
INSERT INTO game_categories (game_id, category_id) VALUES ('towerfall-ascension', 1) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('towerfall-ascension', 3) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('towerfall-ascension', 6) ON CONFLICT DO NOTHING;

-- Game: Tropico 4 (tropico-4)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('tropico-4', 'Tropico 4', 14875, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/57690/library_600x900.jpg', 'The world is changing and Tropico is moving with the times - geographical powers rise and fall and the world market is dominated by new players with new demands and offers - and you, as El Presidente, face a whole new set of challenges.', 'Kalypso Media Digital', '1 Sep, 2011', 49, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'tropico-4';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tropico-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/57690/ss_833be037c67d21af2777a0066d278891d0f2adbb.1920x1080.jpg?t=1755702264', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tropico-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/57690/ss_8b9cb3571d92cbe2efbf091a6c192c366dd81a74.1920x1080.jpg?t=1755702264', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tropico-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/57690/ss_8b95fda44ffdbb4fb76b1629fdd1d0cc4bf288dd.1920x1080.jpg?t=1755702264', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tropico-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/57690/ss_850d6465eef278d3a4dc44121b25d338720ff35d.1920x1080.jpg?t=1755702264', 3);
DELETE FROM game_categories WHERE game_id = 'tropico-4';
INSERT INTO game_categories (game_id, category_id) VALUES ('tropico-4', 7) ON CONFLICT DO NOTHING;

-- Game: Vampire Survivors: Emergency Meeting (vampire-survivors-emergency-meeting)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('vampire-survivors-emergency-meeting', 'Vampire Survivors: Emergency Meeting', 1960, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2690330/header.jpg?t=1730984326', 'Gather your Crewmates, eject Impostors, and be the bullet hell in Vampire Survivors: Emergency Meeting.', 'poncle', '18 Dec, 2023', 14, false, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'vampire-survivors-emergency-meeting';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2690330/ss_ea7bda01fc54bfb90e389a0eb784ad1971155776.1920x1080.jpg?t=1730984326', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2690330/ss_79095b9bd58d3530124f450d557e5664da7e2ce4.1920x1080.jpg?t=1730984326', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2690330/ss_59de1e6c91762e65b1363ea12adf568c1d19ae00.1920x1080.jpg?t=1730984326', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('vampire-survivors-emergency-meeting', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2690330/ss_4bfa407c04ba78e36c8e0c71bd5bc814804373a2.1920x1080.jpg?t=1730984326', 3);
DELETE FROM game_categories WHERE game_id = 'vampire-survivors-emergency-meeting';
INSERT INTO game_categories (game_id, category_id) VALUES ('vampire-survivors-emergency-meeting', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('vampire-survivors-emergency-meeting', 3) ON CONFLICT DO NOTHING;

-- Game: TEKKEN 8 (tekken-8)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('tekken-8', 'TEKKEN 8', 41650, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1778820/library_600x900.jpg', 'TEKKEN 8 is the latest addition to the TEKKEN series, the pinnacle of 3D fighting games. Join the fight with over 32 characters and bear witness to the next installment in this epic saga. Get ready for the biggest and best TEKKEN game ever made!', 'Bandai Namco Entertainment', '25 Jan, 2024', 12, true, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'tekken-8';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tekken-8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1778820/ss_62e6ec252bc1a641e8e42dba07f23631d5da85e6.1920x1080.jpg?t=1787180720', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tekken-8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1778820/ss_99dabe34abc0f92adc39d7099908c9540be7eb3f.1920x1080.jpg?t=1787180720', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tekken-8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1778820/ss_bbd38a5fe748ec966cbc85ffcd4931f0f2da8ffd.1920x1080.jpg?t=1787180720', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tekken-8', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1778820/ss_7c55021d3ba8e4f14c6d9dbea9f19d9b7665d5f0.1920x1080.jpg?t=1787180720', 3);
DELETE FROM game_categories WHERE game_id = 'tekken-8';
INSERT INTO game_categories (game_id, category_id) VALUES ('tekken-8', 1) ON CONFLICT DO NOTHING;

-- Game: DRAGON BALL FighterZ (dragon-ball-fighterz)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('dragon-ball-fighterz', 'DRAGON BALL FighterZ', 48650, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/678950/library_600x900.jpg', 'DRAGON BALL FighterZ is born from what makes the DRAGON BALL series so loved and famous: endless spectacular fights with its all-powerful fighters.', 'Bandai Namco Entertainment', '26 Jan, 2018', 50, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'dragon-ball-fighterz';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dragon-ball-fighterz', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/678950/ss_3d48baf2e7aaf087afbab1c4268637df528eb2c8.1920x1080.jpg?t=1776898545', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dragon-ball-fighterz', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/678950/ss_1702e9bc7ba4ce2dce2555c8d63612aafb5c757a.1920x1080.jpg?t=1776898545', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dragon-ball-fighterz', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/678950/ss_3f18dbe6561ea68c5acb055e3eaa97f480022c24.1920x1080.jpg?t=1776898545', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('dragon-ball-fighterz', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/678950/ss_bcced8fb8be5b0ba2ec2495ae73c6827ccd611e8.1920x1080.jpg?t=1776898545', 3);
DELETE FROM game_categories WHERE game_id = 'dragon-ball-fighterz';
INSERT INTO game_categories (game_id, category_id) VALUES ('dragon-ball-fighterz', 1) ON CONFLICT DO NOTHING;

-- Game: THE KING OF FIGHTERS XV (kof-xv)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('kof-xv', 'THE KING OF FIGHTERS XV', 34650, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1498570/library_600x900.jpg', 'SHATTER ALL EXPECTATIONS! Transcend beyond your limits with KOF XV!', 'SNK CORPORATION', '12 Dec, 2024', 56, false, 1)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'kof-xv';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('kof-xv', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1498570/ss_a78f803bb251e541225b0d82b8c7ce30fc823bdd.1920x1080.jpg?t=1742873915', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('kof-xv', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1498570/ss_67261dc701d58d1cdb581e155fb7185c7e8d6c90.1920x1080.jpg?t=1742873915', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('kof-xv', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1498570/ss_303a91a683131f7deb6e07c304c48ccaf1c4edf1.1920x1080.jpg?t=1742873915', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('kof-xv', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1498570/ss_a564ef3489b58dbd2ebeb37846325fffeecff949.1920x1080.jpg?t=1742873915', 3);
DELETE FROM game_categories WHERE game_id = 'kof-xv';
INSERT INTO game_categories (game_id, category_id) VALUES ('kof-xv', 1) ON CONFLICT DO NOTHING;

-- Game: Devil May Cry 5 (devil-may-cry-5)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('devil-may-cry-5', 'Devil May Cry 5', 28350, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/601150/library_600x900.jpg', 'The ultimate Devil Hunter is back in style, in the game action fans have been waiting for.', 'CAPCOM Co., Ltd.', '7 Mar, 2019', 23, true, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'devil-may-cry-5';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('devil-may-cry-5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/601150/ss_4410bada2565843dae693b03ac3a50256ff5dd66.1920x1080.jpg?t=1790215341', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('devil-may-cry-5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/601150/ss_4ce180ed8979a51c72de51f985e9e9ba13500508.1920x1080.jpg?t=1790215341', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('devil-may-cry-5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/601150/ss_e2be70565f94a7f6c392cccddce08c67f2f87612.1920x1080.jpg?t=1790215341', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('devil-may-cry-5', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/601150/ss_d1e0b403f593f17ad195c5382a7788d71c6f406a.1920x1080.jpg?t=1790215341', 3);
DELETE FROM game_categories WHERE game_id = 'devil-may-cry-5';
INSERT INTO game_categories (game_id, category_id) VALUES ('devil-may-cry-5', 2) ON CONFLICT DO NOTHING;

-- Game: NieR:Automata™ (nier-automata)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('nier-automata', 'NieR:Automata™', 48650, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/524220/library_600x900.jpg', 'NieR: Automata tells the story of androids 2B, 9S and A2 and their battle to reclaim the machine-driven dystopia overrun by powerful machines.', 'Square Enix', '17 Mar, 2017', 23, false, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'nier-automata';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('nier-automata', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/524220/ss_d0314b4c134329a483b5e43af951f60274abc66b.1920x1080.jpg?t=1785182957', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('nier-automata', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/524220/ss_8b29f7e1ce9a8b9313dc3eb50dbe76a4cf94eef9.1920x1080.jpg?t=1785182957', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('nier-automata', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/524220/ss_2c265df38c3d2d393d74ee8e74d79bdafa16b143.1920x1080.jpg?t=1785182957', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('nier-automata', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/524220/ss_831e0e7c9d514393b711e9ed1d6796042521a80c.1920x1080.jpg?t=1785182957', 3);
DELETE FROM game_categories WHERE game_id = 'nier-automata';
INSERT INTO game_categories (game_id, category_id) VALUES ('nier-automata', 2) ON CONFLICT DO NOTHING;

-- Game: Teenage Mutant Ninja Turtles: Shredder''s Revenge (tmnt-shredders-revenge)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('tmnt-shredders-revenge', 'Teenage Mutant Ninja Turtles: Shredder''s Revenge', 17325, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1361510/library_600x900.jpg', 'Teenage Mutant Ninja Turtles: Shredder’s Revenge reunites Leonardo, Michelangelo, Donatello and Raphael as they kick shell in a beautifully realized beat ’em up.', 'Dotemu', '16 Jun, 2022', 56, false, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'tmnt-shredders-revenge';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tmnt-shredders-revenge', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1361510/ss_3c0c96a66b61231bfa9f0689fb9c9b38f9c88fd8.1920x1080.jpg?t=1782982752', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tmnt-shredders-revenge', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1361510/ss_b02e0168a86d43489e65f8be98cd001f7c0491f0.1920x1080.jpg?t=1782982752', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tmnt-shredders-revenge', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1361510/ss_7ca10cb8a7de33eb53af128d333d2dd1a65bec27.1920x1080.jpg?t=1782982752', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('tmnt-shredders-revenge', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1361510/ss_fc85a357383fc35dc1e63c87af8a7e7e7834770e.1920x1080.jpg?t=1782982752', 3);
DELETE FROM game_categories WHERE game_id = 'tmnt-shredders-revenge';
INSERT INTO game_categories (game_id, category_id) VALUES ('tmnt-shredders-revenge', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('tmnt-shredders-revenge', 3) ON CONFLICT DO NOTHING;

-- Game: Streets of Rage 4 (streets-of-rage-4)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('streets-of-rage-4', 'Streets of Rage 4', 17325, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/985890/library_600x900.jpg', 'Amongst the best beat’em up series ever created, jammin’ ‘90s beats and over the top street fighting, the iconic series Streets of Rage comes back with a masterful tribute to and revitalization of the classic action fans adore.', 'Dotemu', '30 Apr, 2020', 48, false, 2)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'streets-of-rage-4';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('streets-of-rage-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/985890/ss_0cbc51b8ec12859e9f180217bee8b973f9b99924.1920x1080.jpg?t=1763718423', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('streets-of-rage-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/985890/ss_1f48943ea994936e2a75463b46ea635f72ba3a0a.1920x1080.jpg?t=1763718423', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('streets-of-rage-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/985890/ss_de2ffc96c3640f57e84094c6cb2df3d9db0e3bc1.1920x1080.jpg?t=1763718423', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('streets-of-rage-4', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/985890/ss_9b520e2d15aa78d3834e2bbbd25090b3f6ca1404.1920x1080.jpg?t=1763718423', 3);
DELETE FROM game_categories WHERE game_id = 'streets-of-rage-4';
INSERT INTO game_categories (game_id, category_id) VALUES ('streets-of-rage-4', 2) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('streets-of-rage-4', 3) ON CONFLICT DO NOTHING;

-- Game: Stardew Valley (stardew-valley)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('stardew-valley', 'Stardew Valley', 525, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/413150/library_600x900.jpg', 'You''ve inherited your grandfather''s old farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin your new life. Can you learn to live off the land and turn these overgrown fields into a thriving home?', 'ConcernedApe', '26 Feb, 2016', 53, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'stardew-valley';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('stardew-valley', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/ss_b887651a93b0525739049eb4194f633de2df75be.1920x1080.jpg?t=1786554168', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('stardew-valley', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/ss_9ac899fe2cda15d48b0549bba77ef8c4a090a71c.1920x1080.jpg?t=1786554168', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('stardew-valley', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/ss_4fa0866709ede3753fdf2745349b528d5e8c4054.1920x1080.jpg?t=1786554168', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('stardew-valley', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/ss_d836f0a5b0447fb6a2bdb0a6ac5f954949d3c41e.1920x1080.jpg?t=1786554168', 3);
DELETE FROM game_categories WHERE game_id = 'stardew-valley';
INSERT INTO game_categories (game_id, category_id) VALUES ('stardew-valley', 3) ON CONFLICT DO NOTHING;

-- Game: Balatro (balatro)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('balatro', 'Balatro', 11025, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/2379780/library_600x900.jpg', 'The poker roguelike. Balatro is a hypnotically satisfying deckbuilder where you play illegal poker hands, discover game-changing jokers, and trigger adrenaline-pumping, outrageous combos.', 'Playstack', '20 Feb, 2024', 13, true, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'balatro';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('balatro', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2379780/96208723dbedef49d71bf1b0a74aee1689018c50/ss_96208723dbedef49d71bf1b0a74aee1689018c50.1920x1080.jpg?t=1790693638', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('balatro', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2379780/ss_4862112e5030f74a5818cd4c31347d699ac5adf3.1920x1080.jpg?t=1790693638', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('balatro', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2379780/ss_3be65a7dd3be072d567e11883d208861a7e959fa.1920x1080.jpg?t=1790693638', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('balatro', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2379780/ss_e32ac94d7d1d6be7dd015d78f2b52aeb4cc282ed.1920x1080.jpg?t=1790693638', 3);
DELETE FROM game_categories WHERE game_id = 'balatro';
INSERT INTO game_categories (game_id, category_id) VALUES ('balatro', 3) ON CONFLICT DO NOTHING;

-- Game: Slay the Spire (slay-the-spire)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('slay-the-spire', 'Slay the Spire', 98000, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/646570/library_600x900.jpg', 'We fused card games and roguelikes together to make the best single player deckbuilder we could. Craft a unique deck, encounter bizarre creatures, discover relics of immense power, and Slay the Spire!', 'Mega Crit', '23 Jan, 2019', 42, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'slay-the-spire';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('slay-the-spire', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/646570/ss_c171816f7ecd35b5b46d2fa27532f4c5b8ca3cc5.1920x1080.jpg?t=1774015376', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('slay-the-spire', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/646570/ss_01aa3e7759e457bfbf2422f31c325d7b3ba8a6eb.1920x1080.jpg?t=1774015376', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('slay-the-spire', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/646570/ss_b757436d08ba08292796bfed9c60e7cc99d5f2c3.1920x1080.jpg?t=1774015376', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('slay-the-spire', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/646570/ss_1299d7fe55771cf564848c5046fbef7936178440.1920x1080.jpg?t=1774015376', 3);
DELETE FROM game_categories WHERE game_id = 'slay-the-spire';
INSERT INTO game_categories (game_id, category_id) VALUES ('slay-the-spire', 3) ON CONFLICT DO NOTHING;

-- Game: Undertale (undertale)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('undertale', 'Undertale', 192500, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/391540/library_600x900.jpg', 'UNDERTALE! The RPG game where you don''t have to destroy anyone.', 'tobyfox', '15 Sep, 2015', 52, false, 3)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'undertale';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('undertale', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/391540/ss_6ded97a2c98473ac1e8a2b3c1419d93fb31b1186.1920x1080.jpg?t=1757349115', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('undertale', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/391540/ss_b9018c41cea2bdfb150609bedfca99b16a5af02a.1920x1080.jpg?t=1757349115', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('undertale', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/391540/ss_b9ec4c53a8ed37f764649c970757c0a1f4948ec1.1920x1080.jpg?t=1757349115', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('undertale', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/391540/ss_edab41f7c9fa287b0d90ebfa3b9219fec6e1b3ed.1920x1080.jpg?t=1757349115', 3);
DELETE FROM game_categories WHERE game_id = 'undertale';
INSERT INTO game_categories (game_id, category_id) VALUES ('undertale', 3) ON CONFLICT DO NOTHING;

-- Game: Hi-Fi RUSH (hi-fi-rush)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('hi-fi-rush', 'Hi-Fi RUSH', 36715, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1817230/library_600x900.jpg', 'Test your abilities in two new game modes, uncover new rewards and even a few secrets in the Arcade Challenge! Update! Feel the beat as wannabe rockstar Chai and his ragtag team fight against an evil megacorp in a world that syncs to the music.', 'KRAFTON, Inc.', '25 Jan, 2023', 37, false, 4)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'hi-fi-rush';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hi-fi-rush', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1817230/ss_382e9d5abb00498eee6a86b0366a2db7826268b7.1920x1080.jpg?t=1764916407', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hi-fi-rush', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1817230/ss_8a13650ffe930da77593dae80f0118588c46a5ca.1920x1080.jpg?t=1764916407', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hi-fi-rush', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1817230/ss_62a7ff68fff7850be6a0784eff41edafd33b2099.1920x1080.jpg?t=1764916407', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('hi-fi-rush', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1817230/ss_7dfaee4616fbed65b2b0fcb82d55dc0a957fd8a5.1920x1080.jpg?t=1764916407', 3);
DELETE FROM game_categories WHERE game_id = 'hi-fi-rush';
INSERT INTO game_categories (game_id, category_id) VALUES ('hi-fi-rush', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('hi-fi-rush', 2) ON CONFLICT DO NOTHING;

-- Game: A Dance of Fire and Ice (a-dance-of-fire-and-ice)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('a-dance-of-fire-and-ice', 'A Dance of Fire and Ice', 4165, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/977950/library_600x900.jpg', 'A Dance of Fire and Ice is a strict rhythm game. Keep your focus as you guide two orbiting planets along a winding path without breaking their perfect equilibrium.', '7th Beat Games', '24 Jan, 2019', 54, false, 4)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'a-dance-of-fire-and-ice';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('a-dance-of-fire-and-ice', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/977950/ss_703110d680fa21cff6278a9e64713f37ea5078b1.1920x1080.jpg?t=1791568731', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('a-dance-of-fire-and-ice', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/977950/ss_5789377fe01b0f2daf0091b7fb3c3ec41f733c6e.1920x1080.jpg?t=1791568731', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('a-dance-of-fire-and-ice', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/977950/ss_2d3ba4cae82f2f245d1f5901fa391b3b6126a157.1920x1080.jpg?t=1791568731', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('a-dance-of-fire-and-ice', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/977950/ss_1b16c74c5351fa5bb5282dedbac220d56ba825c2.1920x1080.jpg?t=1791568731', 3);
DELETE FROM game_categories WHERE game_id = 'a-dance-of-fire-and-ice';
INSERT INTO game_categories (game_id, category_id) VALUES ('a-dance-of-fire-and-ice', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('a-dance-of-fire-and-ice', 3) ON CONFLICT DO NOTHING;

-- Game: Pinball FX (pinball-fx)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('pinball-fx', 'Pinball FX', 599, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/2328760/library_600x900.jpg', 'Pinball FX is digital pinball evolved. Get the highest score on tables inspired by the biggest brands in entertainment. New tables arrive frequently!', 'Zen Studios', '13 Apr, 2023', 19, false, 5)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'pinball-fx';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2328760/ss_90287ee6270a7b4263fff8328963f767770defd9.1920x1080.jpg?t=1787145654', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2328760/ss_3868b70fa1729c60ce6d5e30e53644ebb917c7cf.1920x1080.jpg?t=1787145654', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2328760/ss_939a33fd37d5e2a51658aee7be380380ae79f9ca.1920x1080.jpg?t=1787145654', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('pinball-fx', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2328760/ss_a95ee0296620520999e2ee56c86f8d6ffe1a4718.1920x1080.jpg?t=1787145654', 3);
DELETE FROM game_categories WHERE game_id = 'pinball-fx';
INSERT INTO game_categories (game_id, category_id) VALUES ('pinball-fx', 5) ON CONFLICT DO NOTHING;

-- Game: Yoku''s Island Express (yokus-island-express)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('yokus-island-express', 'Yoku''s Island Express', 21000, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/334940/library_600x900.jpg', 'Meet Yoku, the pint-sized postman protagonist of Yoku''s Island Express! Use a unique blend of pinball mechanics, platforming and open world exploration to unlock the secrets of Mokumana Island, help the locals, awaken ancient deities and much more in this amazing new tropical adventure!', 'Team17 Digital Ltd', '29 May, 2018', 15, false, 5)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'yokus-island-express';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('yokus-island-express', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/334940/ss_a23eaefb9d299e5f10e7eb721fe4abc7e8ab9bbb.1920x1080.jpg?t=1763040310', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('yokus-island-express', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/334940/ss_f386401fb8151eb9b48caa2182f29b886a4a3cf2.1920x1080.jpg?t=1763040310', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('yokus-island-express', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/334940/ss_79752273e90e872597f31ba9bb9bd0cf0c7f23de.1920x1080.jpg?t=1763040310', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('yokus-island-express', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/334940/ss_0131a0791cac53b6274a3b4111d77b78ce0307d7.1920x1080.jpg?t=1763040310', 3);
DELETE FROM game_categories WHERE game_id = 'yokus-island-express';
INSERT INTO game_categories (game_id, category_id) VALUES ('yokus-island-express', 5) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('yokus-island-express', 6) ON CONFLICT DO NOTHING;

-- Game: Cuphead (cuphead)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('cuphead', 'Cuphead', 332500, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/268910/library_600x900.jpg', 'Cuphead is a classic run and gun action game heavily focused on boss battles. Inspired by cartoons of the 1930s, the visuals and audio are painstakingly created with the same techniques of the era, i.e. traditional hand drawn cel animation, watercolor backgrounds, and original jazz recordings.', 'Studio MDHR Entertainment Inc.', '29 Sep, 2017', 27, false, 6)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'cuphead';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('cuphead', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/268910/ss_e3096a5555cb77d88db165c83d5ef3a24af1354a.1920x1080.jpg?t=1709068852', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('cuphead', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/268910/ss_615455299355eaf552c638c7ea5b24a8b46e02dd.1920x1080.jpg?t=1709068852', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('cuphead', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/268910/ss_483fb089be0093beeef03525276803a9ca4f66a1.1920x1080.jpg?t=1709068852', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('cuphead', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/268910/ss_48477e4a865827aa0be6a44f00944d8d2a3e5eb9.1920x1080.jpg?t=1709068852', 3);
DELETE FROM game_categories WHERE game_id = 'cuphead';
INSERT INTO game_categories (game_id, category_id) VALUES ('cuphead', 6) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('cuphead', 3) ON CONFLICT DO NOTHING;

-- Game: Ori and the Will of the Wisps (ori-will-of-the-wisps)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('ori-will-of-the-wisps', 'Ori and the Will of the Wisps', 13265, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1057090/library_600x900.jpg', 'Play the critically acclaimed masterpiece. Embark on a new journey in a vast, exotic world where you’ll encounter towering enemies and challenging puzzles on your quest to unravel Ori’s destiny.', 'Xbox Game Studios', '10 Mar, 2020', 31, false, 6)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'ori-will-of-the-wisps';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('ori-will-of-the-wisps', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1057090/ss_0cf0ec6681ae5771173790dbc99ddb3bf3b886ad.1920x1080.jpg?t=1759530749', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('ori-will-of-the-wisps', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1057090/ss_f8ac174b7949e89fe1939a7bd2c471785dee55f0.1920x1080.jpg?t=1759530749', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('ori-will-of-the-wisps', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1057090/ss_3ae04b208aaff3869f69617c47449ec1d7a06513.1920x1080.jpg?t=1759530749', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('ori-will-of-the-wisps', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1057090/ss_608e317acbb76258bedcf1a79e6673a61475908f.1920x1080.jpg?t=1759530749', 3);
DELETE FROM game_categories WHERE game_id = 'ori-will-of-the-wisps';
INSERT INTO game_categories (game_id, category_id) VALUES ('ori-will-of-the-wisps', 6) ON CONFLICT DO NOTHING;

-- Game: Disco Elysium - The Final Cut (disco-elysium)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('disco-elysium', 'Disco Elysium - The Final Cut', 735000, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/632470/library_600x900.jpg', 'Disco Elysium - The Final Cut is a groundbreaking role playing game. You’re a detective with a unique skill system at your disposal and a whole city to carve your path across. Interrogate unforgettable characters, crack murders or take bribes. Become a hero or an absolute disaster of a human being.', 'ZA/UM', '15 Oct, 2019', 10, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'disco-elysium';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('disco-elysium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/632470/ss_b3694e99ffdb686d1bbbbe16a540d3d2ccd509c4.1920x1080.jpg?t=1780913406', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('disco-elysium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/632470/ss_9125a718ee9ba85386ae5d4eb820f3266073fc97.1920x1080.jpg?t=1780913406', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('disco-elysium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/632470/ss_4f5fdc3cf42feca8dafb1f7d2910ef96e62708a2.1920x1080.jpg?t=1780913406', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('disco-elysium', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/632470/ss_fc6969799ebf19fd2a2c8a986c9419e053606a17.1920x1080.jpg?t=1780913406', 3);
DELETE FROM game_categories WHERE game_id = 'disco-elysium';
INSERT INTO game_categories (game_id, category_id) VALUES ('disco-elysium', 7) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('disco-elysium', 3) ON CONFLICT DO NOTHING;

-- Game: Grim Fandango Remastered (grim-fandango-remastered)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('grim-fandango-remastered', 'Grim Fandango Remastered', 11025, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/316790/library_600x900.jpg', 'One of the most acclaimed adventure games of all time is now back, better than ever. Grim Fandango Remastered has the all the beautiful art and engaging story fans remember, but has also been remastered to look, sound, and control even better than the award-winning original release.', 'Double Fine Productions', '26 Jan, 2015', 32, false, 7)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'grim-fandango-remastered';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('grim-fandango-remastered', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/316790/ss_e57e9c5775d7f3b6748dea9388df83a0201155c8.1920x1080.jpg?t=1731602112', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('grim-fandango-remastered', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/316790/ss_94c4a6ae4fcd2a1d44b767821b6378d7ad2e0516.1920x1080.jpg?t=1731602112', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('grim-fandango-remastered', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/316790/ss_26a12b801d833e3f5b930568775568523c5090b3.1920x1080.jpg?t=1731602112', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('grim-fandango-remastered', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/316790/ss_49c7070357c2d8e6017c1a891b5d9a9d6dcd54f7.1920x1080.jpg?t=1731602112', 3);
DELETE FROM game_categories WHERE game_id = 'grim-fandango-remastered';
INSERT INTO game_categories (game_id, category_id) VALUES ('grim-fandango-remastered', 7) ON CONFLICT DO NOTHING;

-- Game: Muse Dash (muse-dash)
INSERT INTO games (id, name, price, cover_image, description, publisher, release_date, stock, is_highlight, primary_category_id)
VALUES ('muse-dash', 'Muse Dash', 109, 'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/774171/library_600x900.jpg', 'Paradise of parkour &amp; rhythm game ★★★ — Muse Dash!!', 'peropero', '19 Jun, 2019', 45, false, 4)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    cover_image = EXCLUDED.cover_image,
    description = EXCLUDED.description,
    publisher = EXCLUDED.publisher,
    release_date = EXCLUDED.release_date,
    is_highlight = EXCLUDED.is_highlight,
    primary_category_id = EXCLUDED.primary_category_id;

DELETE FROM game_screenshots WHERE game_id = 'muse-dash';
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('muse-dash', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774171/ss_08637a7ac0fb40479d0ad69c78e49805641644e3.1920x1080.jpg?t=1791528485', 0);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('muse-dash', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774171/ss_2c2433413895c9d90ce5e7e31d81e963a2238bc3.1920x1080.jpg?t=1791528485', 1);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('muse-dash', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774171/ss_ab029483f2e7341ac621540794e4d372acd02213.1920x1080.jpg?t=1791528485', 2);
INSERT INTO game_screenshots (game_id, url, display_order) VALUES ('muse-dash', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/774171/ss_a8d9434bb3ecdfd3100f3d24f4ba7b6ebe36427b.1920x1080.jpg?t=1791528485', 3);
DELETE FROM game_categories WHERE game_id = 'muse-dash';
INSERT INTO game_categories (game_id, category_id) VALUES ('muse-dash', 4) ON CONFLICT DO NOTHING;
INSERT INTO game_categories (game_id, category_id) VALUES ('muse-dash', 3) ON CONFLICT DO NOTHING;

