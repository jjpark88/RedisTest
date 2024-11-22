package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Client;
import org.example.domain.entity.RedisHashUser;
import org.example.domain.repository.RedisHashUserRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static org.example.config.CacheConfig.CACHE1;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisHashUserRepository redisHashUserRepository;

    private final RedisTemplate<String, Client> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    //단순하게 디비 조회 해서 select
//    public Client getUser(final Long id){
//        return userRepository.findById(id).orElseThrow();
//    }

    // 객체 1개만 담기는 레디스 템플릿
//    public Client getUser(final Long id){
//        var key = "users:%d".formatted(id);
//        var cachedUser = userRedisTemplate.opsForValue().get(key);
//        if (cachedUser != null) {
//            return (Client) cachedUser;
//        }
//        Client client = userRepository.findById(id).orElseThrow();
//        userRedisTemplate.opsForValue().set(key, client, Duration.ofSeconds(30));
//
//        return client;
//    }

    public Client getUser(final Long id) {
        var key = "users:%d".formatted(id);
        var cachedUser = objectRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return (Client) cachedUser;
        }
        Client user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;
    }

    //redis hash기반
    public RedisHashUser getUser2(final Long id) {
        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            Client user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });
        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public Client getUser3(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
