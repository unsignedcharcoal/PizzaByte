package dev.carlos.soft.pizzabyte.database;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Factory
public class DatabaseInitializer {

    @Value("${redis.uri}")
    private String redisURI;

    RedisClient getClient(){
        if (redisURI == null || redisURI.isEmpty()) {
            log.error("Redis URI is null or empty || Trying localhost...");
            return RedisClient.create("redis://localhost:6379");
        }
        return RedisClient.create(redisURI);
    }

    StatefulRedisConnection<String, String> getConnection(){
        return getClient().connect();
    }

    @Singleton
    RedisCommands<String, String> syncCommands() {
        return getConnection().sync();
    }

    @Singleton
    RedisAsyncCommands<String, String> asyncCommands() {
        return getConnection().async();
    }

}
