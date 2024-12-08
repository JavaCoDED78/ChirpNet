-- Create a keyspace
CREATE
    KEYSPACE IF NOT EXISTS tweets WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '3' };

CREATE TABLE tweet
(
    id            UUID PRIMARY KEY,
    text          TEXT,
    profile_id    TEXT,
    creation_date TIMESTAMP,
    media_urls    SET<TEXT>,
    retweet_to_id UUID,
    reply_to_id   UUID,
    quote_to_id   UUID
);

CREATE TABLE like
(
    parent_tweet_id UUID,
    profile_id      UUID,
    PRIMARY KEY (parent_tweet_id, profile_id)
);

CREATE TABLE 'view'
(
    parent_tweet_id UUID,
    user_id         UUID,
    PRIMARY KEY (parent_tweet_id, user_id)
);

CREATE TABLE tweet_by_profile
(
    profile_id    TEXT,
    id            UUID,
    text          TEXT,
    creation_date TIMESTAMP,
    PRIMARY KEY (profile_id, creation_date)
) WITH CLUSTERING ORDER BY (creation_date DESC);