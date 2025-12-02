package dev.carlos.soft.pizzabyte.storage;

import dev.carlos.soft.pizzabyte.domain.StockItem;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import jakarta.transaction.Transactional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface StockRepository extends PageableRepository<StockItem, Long>{

    @Transactional
    default StockItem saveWithException(StockItem stockItem) {
        save(stockItem);
        throw new DataAccessException("Stock already exists");
    }

}
