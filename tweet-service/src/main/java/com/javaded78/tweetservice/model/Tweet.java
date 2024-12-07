package com.javaded78.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("tweets")
public class Tweet implements BaseEntity<UUID> {

	@PrimaryKey
	private UUID id;

	@Column("text")
	private String text;

	@Column("profile_id")
	private String profileId;

	@Column("creation_date")
	private LocalDateTime creationDate;

	@Column("media_urls")
	private Set<String> mediaUrls = new HashSet<>();

	@Column("retweet_to_id")
	private UUID retweetToId;

	@Column("reply_to_id")
	private UUID replyToId;

	@Column("quote_to_id")
	private UUID quoteToId;
}
