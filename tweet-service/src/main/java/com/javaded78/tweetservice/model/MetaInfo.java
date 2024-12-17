package com.javaded78.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaInfo {

	private int likes;
	private int replies;
	private int retweets;
	private int views;

	public void like() {
		this.likes++;
	}

	public void unlike() {
		this.likes--;
	}

	public void retweet() {
		this.retweets++;
	}

	public void reply() {
		this.replies++;
	}

	public void view() {
		this.views++;
	}
}
