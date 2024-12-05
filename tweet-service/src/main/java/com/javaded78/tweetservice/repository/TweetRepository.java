package com.javaded78.tweetservice.repository;

import com.javaded78.tweetservice.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<Tweet, String> {
}
