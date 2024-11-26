package com.javaded78.profileservice.cache;

import com.javaded78.profileservice.cache.constant.CacheConstant;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheAdvice {

	private final ProfileMapper profileMapper;
	private final RedisTemplate<String, Object> redisTemplate;

	@Pointcut("execution(* com.javaded78.profileservice.service.impl.DefaultProfileService.save(..)) && args(profile)")
	public void saveProfile(Profile profile) {
	}

	@Around(value = "saveProfile(profile)", argNames = "joinPoint,profile")
	public Object cacheAfterSave(ProceedingJoinPoint joinPoint, Profile profile) throws Throwable {
		Object result = joinPoint.proceed(); // Продолжить выполнение метода save

		redisTemplate.opsForValue().set(CacheConstant.GET_PROFILE_RESPONSE_BY_ID + "::" + profile.getId(), profileMapper.toProfileResponse(profile));
		redisTemplate.opsForValue().set(CacheConstant.GET_PROFILE_RESPONSE_BY_EMAIL + "::" + profile.getEmail(), profileMapper.toProfileResponse(profile));

		return result;
	}
}
