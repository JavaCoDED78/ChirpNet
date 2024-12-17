package com.javaded78.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("tweets_by_user")
public class Tweet {

	@PrimaryKeyColumn(name = "profile_id", type = PrimaryKeyType.PARTITIONED)
	private UUID profileId;

	@PrimaryKeyColumn(name = "tweet_id", type = PrimaryKeyType.CLUSTERED)
	@EqualsAndHashCode.Include
	private UUID tweetId;

	@Column("content")
	private String content;

	@Column("created_at")
	private Instant createdAt;

	@Column("hashtags")
	@Builder.Default
	private List<String> hashtags = new ArrayList<>();

	@Column("media_urls")
	@Builder.Default
	private Set<String> mediaUrls = new HashSet<>();

	@Column("retweet_of_id")
	private UUID retweetOfId;

	@Column("reply_to_id")
	private UUID replyToId;

	@Column("quote_of_id")
	private UUID quoteOfId;

	@Column("meta")
	private MetaInfo meta;
}