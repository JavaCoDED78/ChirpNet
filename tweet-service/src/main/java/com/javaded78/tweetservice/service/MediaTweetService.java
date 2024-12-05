package com.javaded78.tweetservice.service;

import com.javaded78.tweetservice.model.Tweet;
import org.springframework.web.multipart.MultipartFile;

public interface MediaTweetService {

	Tweet uploadMediaTweet(Tweet tweet, MultipartFile[] files);
}
