package com.technologyzer.notificationapi.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technologyzer.notificationapi.dao.interfaces.CacheService;
import com.technologyzer.notificationapi.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.technologyzer.notificationapi.constants.ApplicationConstants.TEMPLATE_REDIS_PREFIX;
import static com.technologyzer.notificationapi.constants.ErrorConstants.CACHE_PARSING_ERROR;
import static com.technologyzer.notificationapi.constants.ErrorConstants.PUT_CACHING_ERROR;

@Service
@RequiredArgsConstructor
class CacheServiceImpl implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void putById(final String tenantId, final String id, T value) {
        put(tenantId, "BY_ID:".concat(id), value);
    }

    @Override
    public <T> void putByName(final String tenantId, final String name, T value) {
        put(tenantId, "BY_NAME:".concat(name), value);
    }

    @Override
    public void deleteById(final String tenantId, final String id) {
        delete(tenantId, "BY_ID:".concat(id));
    }

    @Override
    public void deleteByName(final String tenantId, final String name) {
        delete(tenantId, "BY_NAME:".concat(name));
    }

    @Override
    public <T> Optional<T> getById(final String tenantId, final String id, Class<T> clazz) {
        return get(tenantId, "BY_ID:".concat(id), clazz);
    }

    @Override
    public <T> Optional<T> getByName(final String tenantId, final String name, Class<T> clazz) {
        return get(tenantId, "BY_NAME:".concat(name), clazz);
    }

    private <T> Optional<T> get(final String tenantId, final String hashKey, Class<T> classz) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String jsonData = ops.get(getTenantCacheKey(tenantId), hashKey);
        try {
            return Optional.ofNullable(objectMapper.readValue(jsonData, classz));
        } catch (Exception e) {
            throw new ValidationException(CACHE_PARSING_ERROR, HttpStatus.BAD_REQUEST.value());
        }
    }

    private void delete(final String tenantId, final String hashKey) {
        redisTemplate.opsForHash().delete(getTenantCacheKey(tenantId), hashKey);
    }

    private <T> void put(final String tenantId, final String hashKey, final T data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            redisTemplate.opsForHash().put(getTenantCacheKey(tenantId), hashKey, json);
        } catch (JsonProcessingException e) {
            throw new ValidationException(PUT_CACHING_ERROR, HttpStatus.BAD_REQUEST.value());
        }
    }

    private String getTenantCacheKey(final String tenantId) {
        return TEMPLATE_REDIS_PREFIX.concat(tenantId);
    }
}