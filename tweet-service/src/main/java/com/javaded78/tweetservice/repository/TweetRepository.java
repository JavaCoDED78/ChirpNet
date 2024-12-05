package com.javaded78.tweetservice.repository;

import com.javaded78.tweetservice.model.Tweet;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TweetRepository extends MongoRepository<Tweet, String> {

	@Query(value = "{ 'retweetTo._id': ?0 }", count = true)
	Integer countRetweets(String retweetToId);

	@Aggregation(pipeline = {
			"{ $match: { _id: ?0 } }",
			"{ $project: { likeCount: { $size: '$likes' } } }"
	})
	Integer countLikes(String tweetId);
}
