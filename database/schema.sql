-- E-Gaming Hub Complete Database Schema
-- PostgreSQL 13+

-- Create ENUM types
CREATE TYPE user_role AS ENUM ('USER', 'ORGANIZER', 'STREAMER', 'ADMIN');
CREATE TYPE event_type AS ENUM ('TOURNAMENT', 'PRACTICE', 'FRIENDLY', 'LEAGUE');
CREATE TYPE event_format AS ENUM ('SOLO', 'DUO', 'SQUAD', 'TEAM');
CREATE TYPE event_status AS ENUM ('DRAFT', 'SCHEDULED', 'ONGOING', 'COMPLETED', 'CANCELLED');
CREATE TYPE registration_status AS ENUM ('REGISTERED', 'CONFIRMED', 'CANCELLED', 'REJECTED', 'DISQUALIFIED');
CREATE TYPE match_status AS ENUM ('SCHEDULED', 'ONGOING', 'COMPLETED', 'CANCELLED', 'POSTPONED');
CREATE TYPE transaction_type AS ENUM ('DEPOSIT', 'WITHDRAWAL', 'EVENT_FEE', 'FANTASY_ENTRY', 'WINNING', 'REFUND', 'BONUS');
CREATE TYPE transaction_status AS ENUM ('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED');
CREATE TYPE withdrawal_status AS ENUM ('PENDING', 'APPROVED', 'PROCESSING', 'COMPLETED', 'REJECTED', 'CANCELLED');
CREATE TYPE notification_type AS ENUM ('EVENT_STARTING', 'EVENT_ENDED', 'NEW_FOLLOWER', 'STREAM_STARTED', 'NEW_MESSAGE', 'TOURNAMENT_RESULT', 'FANTASY_RESULT', 'FRIEND_REQUEST', 'CLAN_INVITATION', 'ACHIEVEMENT_UNLOCKED', 'REFERRAL_BONUS', 'WITHDRAWAL_STATUS', 'PAYMENT_STATUS');
CREATE TYPE contest_status AS ENUM ('ACTIVE', 'CLOSED', 'LIVE', 'COMPLETED', 'CANCELLED');
CREATE TYPE stream_status AS ENUM ('SCHEDULED', 'LIVE', 'ENDED', 'CANCELLED');
CREATE TYPE clan_status AS ENUM ('ACTIVE', 'INACTIVE', 'DISBANDED');
CREATE TYPE member_role AS ENUM ('OWNER', 'ADMIN', 'MEMBER');
CREATE TYPE report_type AS ENUM ('USER', 'POST', 'COMMENT', 'STREAM');
CREATE TYPE report_reason AS ENUM ('INAPPROPRIATE_CONTENT', 'HARASSMENT', 'HATE_SPEECH', 'SPAM', 'FRAUD', 'CHEATING', 'SELF_HARM', 'OTHER');
CREATE TYPE report_status AS ENUM ('PENDING', 'UNDER_REVIEW', 'RESOLVED', 'DISMISSED');
CREATE TYPE media_type AS ENUM ('IMAGE', 'VIDEO');
CREATE TYPE message_type AS ENUM ('TEXT', 'IMAGE', 'VIDEO', 'AUDIO');

-- Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    avatar VARCHAR(255),
    cover_image VARCHAR(255),
    bio TEXT,
    country VARCHAR(100),
    state VARCHAR(100),
    city VARCHAR(100),
    date_of_birth VARCHAR(20),
    role user_role NOT NULL DEFAULT 'USER',
    is_email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    is_phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_banned BOOLEAN NOT NULL DEFAULT FALSE,
    last_login TIMESTAMP,
    ban_reason VARCHAR(255),
    banned_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_username (username),
    INDEX idx_created_at (created_at)
);

-- User Sessions Table
CREATE TABLE user_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    access_token VARCHAR(255) NOT NULL UNIQUE,
    refresh_token VARCHAR(255),
    device_id VARCHAR(100),
    device_name VARCHAR(100),
    device_type VARCHAR(50),
    ip_address VARCHAR(45),
    user_agent TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    logged_out_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_access_token (access_token)
);

-- OTP Verifications Table
CREATE TABLE otp_verifications (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    otp VARCHAR(10) NOT NULL,
    attempts INT DEFAULT 0,
    max_attempts INT DEFAULT 5,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    verified_at TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_expires_at (expires_at)
);

-- User Profiles Table
CREATE TABLE user_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    gaming_id VARCHAR(100),
    rank INT DEFAULT 0,
    total_matches INT DEFAULT 0,
    total_wins INT DEFAULT 0,
    win_rate DOUBLE PRECISION DEFAULT 0.0,
    total_kills INT DEFAULT 0,
    total_deaths INT DEFAULT 0,
    kdr DOUBLE PRECISION DEFAULT 0.0,
    rating INT DEFAULT 0,
    preferred_game VARCHAR(50),
    favorite_games TEXT[],
    follower_count INT DEFAULT 0,
    following_count INT DEFAULT 0,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    twitch_handle VARCHAR(100),
    youtube_channel VARCHAR(100),
    discord_handle VARCHAR(100),
    instagram_handle VARCHAR(100),
    twitter_handle VARCHAR(100),
    about_me TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_rank (rank)
);

-- Achievements Table
CREATE TABLE achievements (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon VARCHAR(255),
    rarity INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- User Achievements Table
CREATE TABLE user_achievements (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    achievement_id BIGINT NOT NULL,
    unlocked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (achievement_id) REFERENCES achievements(id),
    INDEX idx_user_id (user_id),
    UNIQUE(user_id, achievement_id)
);

-- Posts Table
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    image_url VARCHAR(255),
    tags TEXT[],
    like_count BIGINT DEFAULT 0,
    comment_count BIGINT DEFAULT 0,
    share_count BIGINT DEFAULT 0,
    is_published BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
);

-- Comments Table
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    like_count BIGINT DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_post_id (post_id),
    INDEX idx_user_id (user_id)
);

-- Likes Table
CREATE TABLE likes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT,
    comment_id BIGINT,
    stream_id BIGINT,
    likeable_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_likeable_type (likeable_type),
    UNIQUE(user_id, post_id, likeable_type)
);

-- Stories Table
CREATE TABLE stories (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    media_url VARCHAR(255) NOT NULL,
    media_type media_type,
    caption TEXT,
    view_count BIGINT DEFAULT 0,
    reaction_count BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_expires_at (expires_at)
);

-- Messages Table
CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    chat_room_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    media_url VARCHAR(255),
    message_type message_type,
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (recipient_id) REFERENCES users(id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_chat_room_id (chat_room_id),
    INDEX idx_created_at (created_at)
);

-- Followers Table
CREATE TABLE followers (
    id BIGSERIAL PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    follow_type VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    unfollowed_at TIMESTAMP,
    FOREIGN KEY (follower_id) REFERENCES users(id),
    FOREIGN KEY (following_id) REFERENCES users(id),
    INDEX idx_follower_id (follower_id),
    INDEX idx_following_id (following_id),
    UNIQUE(follower_id, following_id)
);

-- Events Table
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    organizer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    banner_image VARCHAR(255),
    game_name VARCHAR(100) NOT NULL,
    event_type event_type NOT NULL,
    event_format event_format NOT NULL,
    status event_status NOT NULL DEFAULT 'DRAFT',
    max_participants INT,
    current_participants INT DEFAULT 0,
    entry_fee NUMERIC(10, 2),
    prize_pool NUMERIC(15, 2),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    registration_deadline TIMESTAMP,
    total_matches INT DEFAULT 0,
    completed_matches INT DEFAULT 0,
    rules TEXT[],
    requires_verification BOOLEAN NOT NULL DEFAULT FALSE,
    is_featured BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organizer_id) REFERENCES users(id),
    INDEX idx_organizer_id (organizer_id),
    INDEX idx_status (status),
    INDEX idx_game_name (game_name),
    INDEX idx_start_date (start_date)
);

-- Registrations Table
CREATE TABLE registrations (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    team_id BIGINT,
    status registration_status NOT NULL,
    has_payment_verified BOOLEAN DEFAULT FALSE,
    payment_transaction_id VARCHAR(255),
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_event_id (event_id),
    INDEX idx_user_id (user_id),
    UNIQUE(event_id, user_id)
);

-- Event Teams Table
CREATE TABLE event_teams (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    team_name VARCHAR(100) NOT NULL,
    captain_id BIGINT NOT NULL,
    logo VARCHAR(255),
    member_ids BIGINT[],
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    points DOUBLE PRECISION DEFAULT 0.0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id),
    INDEX idx_event_id (event_id),
    INDEX idx_captain_id (captain_id)
);

-- Event Matches Table
CREATE TABLE event_matches (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    round_number INT NOT NULL,
    match_number INT NOT NULL,
    team1_id BIGINT,
    team2_id BIGINT,
    player1_id BIGINT,
    player2_id BIGINT,
    status match_status,
    scheduled_time TIMESTAMP,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    map_name VARCHAR(100),
    winner_id BIGINT,
    team1_score INT DEFAULT 0,
    team2_score INT DEFAULT 0,
    match_stats TEXT,
    match_result_json JSONB,
    live_stream_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id),
    INDEX idx_event_id (event_id),
    INDEX idx_status (status)
);

-- Rankings Table
CREATE TABLE rankings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    game_name VARCHAR(50) NOT NULL,
    country VARCHAR(100) NOT NULL,
    region VARCHAR(100),
    global_rank INT NOT NULL,
    regional_rank INT NOT NULL,
    rating DOUBLE PRECISION DEFAULT 0.0,
    total_matches INT DEFAULT 0,
    total_wins INT DEFAULT 0,
    total_kills INT DEFAULT 0,
    total_deaths INT DEFAULT 0,
    win_rate DOUBLE PRECISION DEFAULT 0.0,
    kdr DOUBLE PRECISION DEFAULT 0.0,
    points INT DEFAULT 0,
    streak_wins INT DEFAULT 0,
    streak_losses INT DEFAULT 0,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_global_rank (global_rank),
    INDEX idx_game_name (game_name)
);

-- Fantasy Contests Table
CREATE TABLE fantasy_contests (
    id BIGSERIAL PRIMARY KEY,
    creator_id BIGINT NOT NULL,
    contest_name VARCHAR(255) NOT NULL,
    description TEXT,
    match_type VARCHAR(100) NOT NULL,
    status contest_status NOT NULL,
    entry_fee NUMERIC(10, 2) NOT NULL,
    prize_pool NUMERIC(15, 2) NOT NULL,
    max_participants INT,
    current_participants INT DEFAULT 0,
    payout_percentage INT DEFAULT 50,
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES users(id),
    INDEX idx_status (status),
    INDEX idx_starts_at (starts_at)
);

-- Fantasy Teams Table
CREATE TABLE fantasy_teams (
    id BIGSERIAL PRIMARY KEY,
    contest_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    team_name VARCHAR(100) NOT NULL,
    player_ids BIGINT[],
    captain_id BIGINT,
    vice_captain_id BIGINT,
    current_points DOUBLE PRECISION DEFAULT 0.0,
    current_rank INT DEFAULT 0,
    is_confirmed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (contest_id) REFERENCES fantasy_contests(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_contest_id (contest_id),
    INDEX idx_user_id (user_id)
);

-- Fantasy Points Table
CREATE TABLE fantasy_points (
    id BIGSERIAL PRIMARY KEY,
    team_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    points_earned DOUBLE PRECISION DEFAULT 0.0,
    kills INT DEFAULT 0,
    deaths INT DEFAULT 0,
    assists INT DEFAULT 0,
    participated BOOLEAN DEFAULT FALSE,
    calculated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES fantasy_teams(id),
    INDEX idx_team_id (team_id),
    INDEX idx_player_id (player_id)
);

-- Live Streams Table
CREATE TABLE live_streams (
    id BIGSERIAL PRIMARY KEY,
    streamer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    thumbnail VARCHAR(255),
    game_name VARCHAR(100) NOT NULL,
    status stream_status NOT NULL,
    stream_url VARCHAR(255) NOT NULL,
    rtmp_url VARCHAR(255),
    stream_key VARCHAR(255),
    viewer_count BIGINT DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    comment_count BIGINT DEFAULT 0,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    total_viewer_minutes BIGINT DEFAULT 0,
    avg_concurrent_viewers DOUBLE PRECISION DEFAULT 0.0,
    allow_chat BOOLEAN DEFAULT TRUE,
    allow_gifts BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (streamer_id) REFERENCES users(id),
    INDEX idx_streamer_id (streamer_id),
    INDEX idx_status (status)
);

-- Stream Comments Table
CREATE TABLE stream_comments (
    id BIGSERIAL PRIMARY KEY,
    stream_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    comment TEXT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stream_id) REFERENCES live_streams(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_stream_id (stream_id)
);

-- Highlights Table
CREATE TABLE highlights (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    video_url VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(255),
    game_name VARCHAR(100) NOT NULL,
    event_id BIGINT,
    match_id BIGINT,
    view_count BIGINT DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    comment_count BIGINT DEFAULT 0,
    share_count BIGINT DEFAULT 0,
    tags TEXT[],
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_game_name (game_name)
);

-- Clans Table
CREATE TABLE clans (
    id BIGSERIAL PRIMARY KEY,
    clan_name VARCHAR(100) NOT NULL UNIQUE,
    clan_tag VARCHAR(20),
    owner_id BIGINT NOT NULL,
    description TEXT,
    logo VARCHAR(255),
    banner_image VARCHAR(255),
    member_count INT DEFAULT 1,
    max_members INT DEFAULT 50,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    clan_rating INT DEFAULT 0,
    is_public BOOLEAN DEFAULT TRUE,
    requires_approval BOOLEAN DEFAULT FALSE,
    status clan_status DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(id),
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status)
);

-- Clan Members Table
CREATE TABLE clan_members (
    id BIGSERIAL PRIMARY KEY,
    clan_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role member_role NOT NULL,
    join_status VARCHAR(50),
    contribution_points INT DEFAULT 0,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    left_at TIMESTAMP,
    FOREIGN KEY (clan_id) REFERENCES clans(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_clan_id (clan_id),
    INDEX idx_user_id (user_id),
    UNIQUE(clan_id, user_id)
);

-- Wallets Table
CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    balance NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    total_earned NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    total_spent NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    total_withdrawn NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    loyalty_points NUMERIC(10, 2) DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id)
);

-- Transactions Table
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(100) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    type transaction_type NOT NULL,
    status transaction_status NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    payment_gateway VARCHAR(50),
    gateway_transaction_id VARCHAR(255),
    description TEXT,
    related_event_id BIGINT,
    related_contest_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- Withdrawals Table
CREATE TABLE withdrawals (
    id BIGSERIAL PRIMARY KEY,
    withdrawal_id VARCHAR(100) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    status withdrawal_status NOT NULL,
    bank_name VARCHAR(100),
    account_number VARCHAR(50),
    ifsc_code VARCHAR(20),
    upi_id VARCHAR(100),
    withdrawal_method VARCHAR(50),
    requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP,
    completed_at TIMESTAMP,
    rejected_at TIMESTAMP,
    rejection_reason TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

-- Notifications Table
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT,
    type notification_type NOT NULL,
    related_entity_id BIGINT,
    related_entity_type VARCHAR(50),
    action_url VARCHAR(255),
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at)
);

-- Referrals Table
CREATE TABLE referrals (
    id BIGSERIAL PRIMARY KEY,
    referrer_id BIGINT NOT NULL,
    referred_user_id BIGINT NOT NULL,
    referral_code VARCHAR(50) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    has_earned BOOLEAN NOT NULL DEFAULT FALSE,
    bonus_amount NUMERIC(10, 2) DEFAULT 0.00,
    status VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    expires_at TIMESTAMP,
    FOREIGN KEY (referrer_id) REFERENCES users(id),
    FOREIGN KEY (referred_user_id) REFERENCES users(id),
    INDEX idx_referrer_id (referrer_id),
    INDEX idx_referral_code (referral_code)
);

-- Admin Logs Table
CREATE TABLE admin_logs (
    id BIGSERIAL PRIMARY KEY,
    admin_id BIGINT NOT NULL,
    action VARCHAR(255) NOT NULL,
    details TEXT,
    affected_entity_type VARCHAR(50),
    affected_entity_id BIGINT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES users(id),
    INDEX idx_admin_id (admin_id),
    INDEX idx_created_at (created_at)
);

-- Reports Table
CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    reporter_id BIGINT NOT NULL,
    reported_user_id BIGINT,
    reported_post_id BIGINT,
    reported_comment_id BIGINT,
    type report_type NOT NULL,
    reason report_reason NOT NULL,
    description TEXT,
    status report_status NOT NULL,
    resolver_id BIGINT,
    resolution_note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users(id),
    FOREIGN KEY (resolver_id) REFERENCES users(id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- Create indexes for performance
CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_followers_follower ON followers(follower_id);
CREATE INDEX idx_registrations_event_user ON registrations(event_id, user_id);
CREATE INDEX idx_rankings_game_rank ON rankings(game_name, global_rank);
CREATE INDEX idx_transactions_user_date ON transactions(user_id, created_at);
CREATE INDEX idx_notifications_user_date ON notifications(user_id, created_at DESC);
