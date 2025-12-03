package dev.carlos.soft.pizzabyte.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Serdeable
@MappedEntity
public class StockItem {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    private String namespace;

    private int amount;

    private long lastUpdate;

}
