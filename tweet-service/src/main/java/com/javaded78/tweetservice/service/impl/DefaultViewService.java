package com.javaded78.tweetservice.service.impl;

import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.model.Tweet;
import com.javaded78.tweetservice.service.ViewService;
import org.springframework.stereotype.Service;

@Service
public class DefaultViewService implements ViewService {
	@Override
	public View createView(Tweet tweet, String loggedInUser, ProfileServiceClient profileServiceClient) {
		return null;
	}
}
