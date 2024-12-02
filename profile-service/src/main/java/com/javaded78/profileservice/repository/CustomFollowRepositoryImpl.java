package com.javaded78.profileservice.repository;

import com.javaded78.profileservice.model.Profile;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

@RequiredArgsConstructor
public class CustomFollowRepositoryImpl implements CustomFollowRepository {

	private final MongoTemplate mongoTemplate;

	@Override
	public Page<Profile> findFollowingsWithHighFollowers(String profileId, long minFollowers, Pageable pageable) {
		MatchOperation matchFolloweeProfile = Aggregation.match(
				Criteria.where("followeeProfile.$id").is(new ObjectId(profileId))
		);
		LookupOperation lookupProfiles = Aggregation.lookup(
				"profiles",
				"followerProfile.$id",
				"_id",
				"profile"
		);
		UnwindOperation unwindProfiles = Aggregation.unwind("profile");
		MatchOperation matchPositiveFollowerCount = Aggregation.match(
				Criteria.where("profile.followerCount").gte(minFollowers)
		);
		ReplaceRootOperation replaceRootWithProfile = Aggregation.replaceRoot("profile");
		SkipOperation skip = Aggregation.skip(pageable.getOffset());
		LimitOperation limit = Aggregation.limit(pageable.getPageSize());

		Aggregation aggregation = Aggregation.newAggregation(
				matchFolloweeProfile,
				lookupProfiles,
				unwindProfiles,
				matchPositiveFollowerCount,
				replaceRootWithProfile,
				skip,
				limit
		);
		AggregationResults<Profile> follows = mongoTemplate.aggregate(aggregation, "follows", Profile.class);
		List<Profile> results = follows.getMappedResults();

		Aggregation countAggregation = Aggregation.newAggregation(
				matchFolloweeProfile,
				lookupProfiles,
				unwindProfiles,
				matchPositiveFollowerCount,
				Aggregation.group().count().as("total")
		);
		AggregationResults<Document> countResults = mongoTemplate.aggregate(countAggregation, "follows", Document.class);
		long total = countResults.getMappedResults().isEmpty()
				? 0
				: countResults.getMappedResults().getFirst().getInteger("total");

		return new PageImpl<>(results, pageable, total);
	}
}
