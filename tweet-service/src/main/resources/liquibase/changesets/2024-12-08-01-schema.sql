-- Create a keyspace
CREATE
    KEYSPACE IF NOT EXISTS tweets WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '3' };

CREATE TABLE tweets_by_user
(
    profile_id    UUID,
    tweet_id      TIMEUUID,
    content       TEXT,
    created_at    TIMESTAMP,
    hashtags      LIST<TEXT>,
    retweet_of_id UUID,
    reply_to_id   UUID,
    quote_of_id   UUID,
    media_urls    SET<TEXT>,
    meta          FROZEN<MAP<TEXT, INT>>,
    PRIMARY KEY ((profile_id), tweet_id)
) WITH CLUSTERING ORDER BY (tweet_id DESC);

CREATE TABLE timeline
(
    timeline_id UUID,
    tweet_id    TIMEUUID,
    profile_id  UUID,
    content     TEXT,
    created_at  TIMESTAMP,
    media_urls  SET<TEXT>,
    meta        FROZEN<MAP<TEXT, INT>>,
    PRIMARY KEY ((timeline_id), tweet_id)
) WITH CLUSTERING ORDER BY (tweet_id DESC);

CREATE TABLE tweets_by_hashtag
(
    hashtag    TEXT,
    tweet_id   TIMEUUID,
    profile_id UUID,
    content    TEXT,
    created_at TIMESTAMP,
    media_urls SET<TEXT>,
    meta       FROZEN<MAP<TEXT, INT>>,
    PRIMARY KEY ((hashtag), tweet_id)
) WITH CLUSTERING ORDER BY (tweet_id DESC);

CREATE TABLE likes_by_tweet
(
    tweet_id   TIMEUUID,
    profile_id UUID,
    created_at TIMESTAMP,
    PRIMARY KEY ((tweet_id), profile_id)
);
