-- V9: Create highlights table and seed initial highlights
CREATE TABLE IF NOT EXISTS highlights (
    id BIGSERIAL PRIMARY KEY,
    game_id VARCHAR(100) NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    custom_image TEXT,
    button_color VARCHAR(30) NOT NULL DEFAULT '#8b0000',
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_highlights_game_id ON highlights(game_id);

-- Seed initial highlight for Elden Ring matching mockup
INSERT INTO highlights (game_id, custom_image, button_color)
SELECT 'elden-ring', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/ss_943bf6fe62352757d9070c1d33e50b92fe8539f1.1920x1080.jpg?t=1790290043', '#8b0000'
WHERE EXISTS (SELECT 1 FROM games WHERE id = 'elden-ring')
  AND NOT EXISTS (SELECT 1 FROM highlights WHERE game_id = 'elden-ring');
