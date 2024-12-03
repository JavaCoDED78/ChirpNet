package com.javaded78.profileservice.cache;

import com.javaded78.profileservice.cache.constant.CacheConstant;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheAdvice {

	private final ProfileMapper profileMapper;
	private final RedisTemplate<String, Object> redisTemplate;

	@Pointcut("execution(* com.javaded78.profileservice.service.impl.DefaultProfileService.save(..)) && args(profile)")
	public void saveProfile(Profile profile) {
	}

	@Pointcut("execution(* com.javaded78.profileservice.service.impl.DefaultProfileService.saveAll(..)) && args(profiles)")
	public void saveProfiles(Profile... profiles) {
	}

	@Around(value = "saveProfile(profile)", argNames = "joinPoint,profile")
	public Object cacheAfterSave(ProceedingJoinPoint joinPoint, Profile profile) throws Throwable {
		Object result = joinPoint.proceed();
		cachingProfile(profile);
		return result;
	}

	private void cachingProfile(Profile profile) {
		redisTemplate.opsForValue().set(CacheConstant.GET_PROFILE_RESPONSE_BY_ID + "::" + profile.getId(), profileMapper.toProfileResponse(profile));
		redisTemplate.opsForValue().set(CacheConstant.GET_PROFILE_RESPONSE_BY_EMAIL + "::" + profile.getEmail(), profileMapper.toProfileResponse(profile));
		redisTemplate.opsForValue().set(CacheConstant.GET_PROFILE_AVATAR_BY_EMAIL + "::" + profile.getEmail(), profile.getAvatarUrl());
		redisTemplate.opsForValue().set(CacheConstant.GET_PROFILE_BANNER_BY_EMAIL + "::" + profile.getEmail(), profile.getBannerUrl());
	}

	@After(value = "saveProfiles(profiles)", argNames = "profiles")
	public void cacheAfterSaveAll(Profile... profiles) {
		Arrays.stream(profiles).forEach(this::cachingProfile);
	}
}
