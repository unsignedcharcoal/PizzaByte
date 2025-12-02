package dev.carlos.soft.pizzabyte.storage;

import io.lettuce.core.api.sync.RedisCommands;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;

@Singleton
public class StockService {

    @Inject DataSource dataSource;
    @Inject private RedisCommands<String, String> syncCommands;

    /**
     * Fast check for knows if there are this item in stock
     */
    //haha I don't know, but I assume that I'll use this
    public boolean containsItem(String id){
        return false;
    }

}
