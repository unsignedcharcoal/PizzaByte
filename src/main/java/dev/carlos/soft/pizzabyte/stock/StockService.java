package dev.carlos.soft.pizzabyte.stock;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.carlos.soft.pizzabyte.domain.StockItem;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Singleton
public class StockService {

    @Inject protected StockRepository stockRepository;

    protected final Cache<Long, StockItem> cache =
            Caffeine.newBuilder()
                    .expireAfterAccess(Duration.ofMinutes(30))
                    .build();

    public Optional<StockItem> findById(Long id) {
        StockItem cached = cache.getIfPresent(id);
        if (cached != null) {
            return Optional.of(cached);
        }

        Optional<StockItem> dbItem = stockRepository.findById(id);
        dbItem.ifPresent(item -> cache.put(item.getId(), item));
        return dbItem;
    }

    public List<StockItem> findAll() {
        return stockRepository.findAll();
    }

    public StockItem save(StockItem stockItem) {

        // 1. Save first (ID is generated)
        StockItem saved = stockRepository.save(stockItem);

        // 2. Cache only AFTER ID exists
        if (saved.getId() != null) {
            cache.put(saved.getId(), saved);
        }

        return saved;
    }

    public StockItem update(StockItem stockItem) {
        stockItem.setLastUpdate(System.currentTimeMillis());

        StockItem saved = stockRepository.save(stockItem);

        // Update cache
        if (saved.getId() != null) {
            cache.put(saved.getId(), saved);
        }

        return saved;
    }

    public boolean existsById(Long id) {
        if (cache.getIfPresent(id) != null) {
            return true;
        }
        return stockRepository.existsById(id);
    }

}

