package com.javaded78.tweetservice.repository;

import com.javaded78.tweetservice.model.Tweet;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface TweetRepository extends CassandraRepository<Tweet, UUID> {

	@Query(value = "{ 'retweetTo._id': ?0 }", count = true)
	Integer countRetweets(String retweetToId);

	@Aggregation(pipeline = {
			"{ $match: { _id: ?0 } }",
			"{ $project: { likeCount: { $size: '$likes' } } }"
	})
	Integer countLikes(String tweetId);
}
