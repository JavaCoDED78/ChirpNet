package com.javaded78.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "tweets")
@CompoundIndexes({
		@CompoundIndex(name = "profileId_creationDate_idx", def = "{'profileId': 1, 'creationDate': -1}", unique = true),
		@CompoundIndex(name = "retweet_reply_idx", def = "{'retweetTo': 1, 'replyTo': 1, 'creationDate': -1}", unique = true)})
public class Tweet implements BaseEntity<String> {

	@Id
	@EqualsAndHashCode.Include
	private String id;

	@TextIndexed
	private String text;

	@Indexed
	private String profileId;

	@Indexed
	private LocalDateTime createdDateTime;

	private Set<String> mediaUrls = new HashSet<>();

	@DBRef(lazy = true)
	private Set<Tweet> retweets = new HashSet<>();

	@DBRef(lazy = true)
	private Set<Tweet> replies = new HashSet<>();

	@DBRef(lazy = true)
	private Set<Like> likes = new HashSet<>();

	@DBRef(lazy = true)
	private Set<View> views = new HashSet<>();

	@DBRef
	private Tweet retweetTo;

	@DBRef
	private Tweet replyTo;

	@DBRef
	private Tweet quoteTo;
}
