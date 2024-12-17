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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("timeline")
public class Timeline {

	@PrimaryKeyColumn(name = "timeline_id", type = PrimaryKeyType.PARTITIONED)
	@EqualsAndHashCode.Include
	private UUID timelineId;

	@PrimaryKeyColumn(name = "tweet_id", type = PrimaryKeyType.CLUSTERED)
	@EqualsAndHashCode.Include
	private UUID tweetId;

	@Column("profile_id")
	@EqualsAndHashCode.Include
	private UUID profileId;

	@Column("content")
	private String content;

	@Column("created_at")
	private Instant createdAt;

	@Column("hashtags")
	@Builder.Default
	private Set<String> mediaUrls = new HashSet<>();

	@Column("meta")
	private MetaInfo meta;
}
