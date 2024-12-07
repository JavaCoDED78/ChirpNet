package com.javaded78.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("views")
public class View {

	@PrimaryKey
	@EqualsAndHashCode.Include
	private ViewKey id;

	@Column("parent_tweet_id")
	private UUID parentTweetId;

	@Column("profile_id")
	private String profileId;

	@PrimaryKeyClass
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ViewKey implements Serializable {

		@PrimaryKeyColumn(name = "parent_tweet_id", type = PrimaryKeyType.PARTITIONED)
		private UUID parentTweetId;

		@PrimaryKeyColumn(name = "profile_id", type = PrimaryKeyType.CLUSTERED)
		private String profileId;
	}
}
