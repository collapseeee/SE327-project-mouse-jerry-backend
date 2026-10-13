-- V6: Create system_requirements table

CREATE TABLE IF NOT EXISTS system_requirements (
    id BIGSERIAL PRIMARY KEY,
    game_id VARCHAR(100) NOT NULL UNIQUE REFERENCES games(id) ON DELETE CASCADE,
    os TEXT,
    processor TEXT,
    memory TEXT,
    graphics TEXT,
    storage TEXT
);

CREATE INDEX IF NOT EXISTS idx_system_requirements_game_id ON system_requirements(game_id);
