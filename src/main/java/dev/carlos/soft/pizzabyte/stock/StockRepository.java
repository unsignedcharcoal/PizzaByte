package dev.carlos.soft.pizzabyte.stock;

import dev.carlos.soft.pizzabyte.domain.StockItem;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import jakarta.transaction.Transactional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface StockRepository extends PageableRepository<StockItem, Long>{

}
