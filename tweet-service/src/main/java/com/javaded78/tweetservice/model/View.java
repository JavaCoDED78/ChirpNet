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
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "views")
@CompoundIndexes({
		@CompoundIndex(name = "parentTweetId_profileId_idx", def = "{'parentTweetId': 1, 'profileId': 1}", unique = true)
})
public class View implements BaseEntity<String> {

	@EqualsAndHashCode.Include
	@Id
	private String id;

	@Indexed
	private String parentTweetId;

	@Indexed
	private String profileId;
}
