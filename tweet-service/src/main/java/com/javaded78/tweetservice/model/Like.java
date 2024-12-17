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
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("likes_by_tweet")
public class Like {

	@PrimaryKeyColumn(name = "tweet_id", type = PrimaryKeyType.PARTITIONED)
	private UUID tweetId;

	@PrimaryKeyColumn(name = "profile_id", type = PrimaryKeyType.CLUSTERED)
	private UUID profileId;

	@Column("created_at")
	private Instant createdAt;
}