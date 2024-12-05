package com.javaded78.tweetservice.service.impl;

import com.javaded78.tweetservice.model.Tweet;
import com.javaded78.tweetservice.service.MediaTweetService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DefaultMediaTweetService implements MediaTweetService {

	@Override
	public Tweet uploadMediaTweet(Tweet tweet, MultipartFile[] files) {
		return null;
	}
}
