package com.javaded78.tweetservice.service;

import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.model.Tweet;

public interface ViewService {

	View createView(Tweet tweet, String loggedInUser, ProfileServiceClient profileServiceClient);
}
