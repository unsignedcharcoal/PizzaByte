package dev.carlos.soft.pizzabyte.storage;

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
    protected final Cache<@NotNull Long, StockItem> cache = Caffeine.newBuilder().expireAfterAccess(Duration.ofMinutes(30)).build();

    public Optional<StockItem> findById(Long id) {
        var cached =  cache.getIfPresent(id);
        if (cached != null) {
            return Optional.of(cached);
        }

        return stockRepository.findById(id);
    }

    public List<StockItem> findAll() {
        return stockRepository.findAll();
    }

    public StockItem save(StockItem stockItem) {
        cache.put(stockItem.getId(), stockItem);
        return stockRepository.save(stockItem);
    }

    public StockItem update(StockItem stockItem) {
        stockItem.setLastUpdate(System.currentTimeMillis());
        return stockRepository.save(stockItem);
    }

    public boolean existsById(Long id) {
        var cached =  cache.getIfPresent(id);
        if (cached != null) {
            return true;
        }

        return stockRepository.existsById(id);
    }

    public StockItem saveWithException(StockItem stockItem) {
        cache.put(stockItem.getId(), stockItem);
        return stockRepository.saveWithException(stockItem);
    }


}
