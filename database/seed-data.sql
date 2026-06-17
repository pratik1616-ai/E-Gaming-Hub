-- Seed data for testing

-- Insert sample achievements
INSERT INTO achievements (code, name, description, icon, rarity) VALUES
('FIRST_WIN', 'First Victory', 'Win your first match', '/assets/achievements/first_win.png', 1),
('10_WINS', '10 Victories', 'Win 10 matches', '/assets/achievements/10_wins.png', 2),
('100_KILLS', 'Sharpshooter', 'Get 100 kills', '/assets/achievements/100_kills.png', 3),
('STREAMER', 'Going Live', 'Start your first stream', '/assets/achievements/streamer.png', 2),
('SOCIAL', 'Popular', 'Get 1000 followers', '/assets/achievements/social.png', 3);

-- Insert sample games
INSERT INTO events (organizer_id, title, description, game_name, event_type, event_format, status, max_participants, entry_fee, prize_pool, start_date, registration_deadline, requires_verification) VALUES
(1, 'BGMI Weekly Tournament', 'Weekly BGMI tournament', 'BGMI', 'TOURNAMENT', 'SQUAD', 'SCHEDULED', 32, 100.00, 10000.00, NOW() + INTERVAL '7 days', NOW() + INTERVAL '3 days', FALSE),
(1, 'Valorant Pro League', 'Professional Valorant League', 'VALORANT', 'LEAGUE', 'TEAM', 'SCHEDULED', 16, 500.00, 50000.00, NOW() + INTERVAL '14 days', NOW() + INTERVAL '7 days', TRUE);

-- Insert sample clans
INSERT INTO clans (clan_name, clan_tag, owner_id, description, member_count, max_members, is_public, status) VALUES
('Elite Gamers', 'EG', 1, 'Top-tier gaming community', 15, 50, TRUE, 'ACTIVE'),
('Phoenix Rising', 'PR', 2, 'Rising esports team', 10, 50, TRUE, 'ACTIVE');
