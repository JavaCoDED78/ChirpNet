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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("tweets_by_hashtag")
public class Hashtag {

	@PrimaryKeyColumn(name = "hashtag", type = PrimaryKeyType.PARTITIONED)
	@EqualsAndHashCode.Include
	private String hashtag;

	@PrimaryKeyColumn(name = "tweet_id", type = PrimaryKeyType.CLUSTERED)
	@EqualsAndHashCode.Include
	private UUID tweetId;

	@Column("profile_id")
	private UUID profileId;

	@Column("content")
	private String content;

	@Column("created_at")
	private Instant createdAt;

	@Column("meta")
	private MetaInfo meta;

	@Column("media_urls")
	@Builder.Default
	private Set<String> mediaUrls = new HashSet<>();
}
